package org.umn.multimedianusantaraclinic.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Base64
import java.security.MessageDigest
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

class UserDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context.applicationContext, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "MncClinic.db"
        // Naik dari 1 ke 2 karena format password berubah (plaintext -> hash).
        // onUpgrade akan membuat ulang tabel pada HP/emulator yang masih punya DB lama.
        private const val DATABASE_VERSION = 2
        private const val TABLE_USERS = "users"
        private const val USER_ID = "id"
        private const val USER_EMAIL = "email"
        private const val USER_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE $TABLE_USERS (
                $USER_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $USER_EMAIL TEXT UNIQUE NOT NULL,
                $USER_PASSWORD TEXT NOT NULL
            )
            """.trimIndent()
        )
        // Akun default untuk development. Hapus blok ini sebelum aplikasi dirilis.
        val defaultValues = ContentValues().apply {
            put(USER_EMAIL, "admin@gmail.com")
            put(USER_PASSWORD, PasswordHasher.hash("123456"))
        }
        db.insert(TABLE_USERS, null, defaultValues)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }
    fun registerUser(email: String, password: String): Boolean {
        val values = ContentValues().apply {
            put(USER_EMAIL, email.lowercase().trim())
            put(USER_PASSWORD, PasswordHasher.hash(password))
        }
        return writableDatabase.insert(TABLE_USERS, null, values) != -1L
    }
    fun checkUser(email: String, password: String): Boolean {
        readableDatabase.query(
            TABLE_USERS,
            arrayOf(USER_PASSWORD),
            "$USER_EMAIL = ?",
            arrayOf(email.lowercase().trim()),
            null, null, null
        ).use { cursor ->
            if (!cursor.moveToFirst()) return false
            return PasswordHasher.verify(password, cursor.getString(0))
        }
    }

    fun isEmailExists(email: String): Boolean {
        readableDatabase.query(
            TABLE_USERS,
            arrayOf(USER_ID),
            "$USER_EMAIL = ?",
            arrayOf(email.lowercase().trim()),
            null, null, null
        ).use { cursor ->
            return cursor.count > 0
        }
    }
}

private object PasswordHasher {
    private const val ALGORITHM = "PBKDF2WithHmacSHA1"
    private const val ITERATIONS = 100_000
    private const val KEY_LENGTH = 256
    private const val SALT_SIZE = 16

    fun hash(password: String): String {
        val salt = ByteArray(SALT_SIZE).also { SecureRandom().nextBytes(it) }
        val hash = derive(password, salt)
        return Base64.encodeToString(salt, Base64.NO_WRAP) + ":" +
                Base64.encodeToString(hash, Base64.NO_WRAP)
    }

    fun verify(password: String, stored: String): Boolean {
        val parts = stored.split(":")
        if (parts.size != 2) return false
        val salt = Base64.decode(parts[0], Base64.NO_WRAP)
        val expected = Base64.decode(parts[1], Base64.NO_WRAP)
        return MessageDigest.isEqual(expected, derive(password, salt))
    }

    private fun derive(password: String, salt: ByteArray): ByteArray {
        val spec = PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH)
        return SecretKeyFactory.getInstance(ALGORITHM).generateSecret(spec).encoded
    }
}