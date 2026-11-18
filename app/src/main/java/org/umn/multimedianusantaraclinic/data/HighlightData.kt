package org.umn.multimedianusantaraclinic.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bloodtype
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import org.umn.multimedianusantaraclinic.ui.theme.Cyan40
import org.umn.multimedianusantaraclinic.ui.theme.Green40

data class HighlightItem(
    val id: String,
    val badge: String,
    val title: String,
    val subtitle: String,
    val date: String,
    val time: String,
    val location: String,
    val description: String,
    val requirements: List<String>,
    val primaryColor: Color,
    val secondaryColor: Color,
    val icon: ImageVector
)

val sampleHighlights = listOf(
    HighlightItem(
        id = "hl-1",
        badge = "CAMPUS EVENT",
        title = "UMN Blood Donation 2026",
        subtitle = "Lobby Gedung B • Free Health Check",
        date = "20 November 2026",
        time = "09:00 - 15:00 WIB",
        location = "Lobby Gedung B, UMN",
        description = "Kegiatan donor darah rutin bekerja sama dengan PMI Kabupaten Tangerang. Setiap pendonor akan mendapatkan pemeriksaan tekanan darah, kadar hemoglobin gratis, serta paket konsumsi pemulihan.",
        requirements = listOf(
            "Membawa Kartu Tanda Mahasiswa (KTM) / KTP",
            "Usia minimal 17 tahun dengan berat badan min. 45 kg",
            "Tidur minimal 5 jam dan sudah sarapan sebelum donor",
            "Tidak sedang mengonsumsi obat-obatan dalam 3 hari terakhir"
        ),
        primaryColor = Color(0xFF1B5E20),
        secondaryColor = Green40,
        icon = Icons.Default.Bloodtype
    ),
    HighlightItem(
        id = "hl-2",
        badge = "CLINIC INFO",
        title = "Free Medical Check-Up",
        subtitle = "Khusus Mahasiswa & Civitas UMN",
        date = "Senin - Jumat",
        time = "08:00 - 17:00 WIB",
        location = "Multimedia Nusantara Clinic, Gedung A Lt. 1",
        description = "Klinik UMN menyediakan layanan pemeriksaan kesehatan dasar gratis meliputi cek tensi, suhu tubuh, konsultasi dokter umum, serta pemberian obat P3K dasar bagi seluruh mahasiswa aktif dan staf UMN.",
        requirements = listOf(
            "Menunjukkan KTM atau ID Card staf UMN yang masih berlaku",
            "Melakukan registrasi singkat di meja resepsionis klinik"
        ),
        primaryColor = Color(0xFF006064),
        secondaryColor = Cyan40,
        icon = Icons.Default.LocalHospital
    ),
    HighlightItem(
        id = "hl-3",
        badge = "HEALTH TIPS",
        title = "Cegah Flu & Kelelahan Kuliah",
        subtitle = "Panduan menjaga imun saat musim hujan",
        date = "Artikel Kesehatan",
        time = "3 Menit Baca",
        location = "Tim Medis Klinik UMN",
        description = "Perubahan cuaca dan jadwal perkuliahan yang padat dapat menurunkan daya tahan tubuh. Pastikan asupan cairan minimal 2 liter per hari, istirahat cukup, dan segera kunjungi klinik jika mengalami demam lebih dari 2 hari.",
        requirements = listOf(
            "Konsumsi air putih yang cukup selama berada di ruang ber-AC",
            "Gunakan masker apabila mulai mengalami gejala batuk atau pilek",
            "Hubungi Hotline Klinik jika membutuhkan penanganan darurat di kampus"
        ),
        primaryColor = Color(0xFF2E7D32),
        secondaryColor = Color(0xFF66BB6A),
        icon = Icons.Default.HealthAndSafety
    )
)

fun getHighlightById(id: String?): HighlightItem {
    return sampleHighlights.firstOrNull { it.id == id } ?: sampleHighlights.first()
}