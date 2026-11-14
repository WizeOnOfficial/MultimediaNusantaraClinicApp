package org.umn.multimedianusantaraclinic.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import org.umn.multimedianusantaraclinic.ui.component.ProfileCard
import org.umn.multimedianusantaraclinic.ui.theme.Green40
import org.umn.multimedianusantaraclinic.ui.theme.Red

@Composable
fun ProfileScreen(
    userName: String = "Barbara",
    userId: String = "NIM: 00000012345",
    email: String = "barbara@student.umn.ac.id",
    bloodType: String = "O+",
    allergies: String = "None",
    emergencyContact: String = "+62 812-3456-7890",
    onNavigateToSettings: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var showPhotoDialog by remember { mutableStateOf(false) }

    // Launcher untuk membuka galeri HP
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            selectedImageUri = uri
        }
    }

    // Gunakan gambar dari galeri jika sudah dipilih, jika belum pakai ikon default
    val profilePainter = if (selectedImageUri != null) {
        rememberAsyncImagePainter(model = selectedImageUri)
    } else {
        rememberVectorPainter(Icons.Default.AccountCircle)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Dekorasi lingkaran hijau (konsisten dengan Home.kt)
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .offset(x = 60.dp, y = (-40).dp)
                    .background(Color(0xFF2E7D32), CircleShape)
                    .align(Alignment.TopEnd)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Header Title
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "My Profile",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1B5E20)
                    )
                }

                // Kartu Profil dengan klik untuk lihat/ganti foto
                item {
                    ProfileCard(
                        title = "UMN Clinic Patient Card",
                        name = userName,
                        userId = userId,
                        imagePainter = profilePainter,
                        onImageClick = { showPhotoDialog = true }
                    )
                }

                // Informasi Medis (Layout Atas-Bawah)
                item {
                    Text(
                        text = "Medical Information",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            ProfileInfoRow(
                                icon = Icons.Default.Email,
                                label = "Campus Email",
                                value = email
                            )
                            HorizontalDivider()
                            ProfileInfoRow(
                                icon = Icons.Default.Bloodtype,
                                label = "Blood Type",
                                value = bloodType
                            )
                            HorizontalDivider()
                            ProfileInfoRow(
                                icon = Icons.Default.WarningAmber,
                                label = "Allergies",
                                value = allergies
                            )
                            HorizontalDivider()
                            ProfileInfoRow(
                                icon = Icons.Default.ContactPhone,
                                label = "Emergency Contact",
                                value = emergencyContact
                            )
                        }
                    }
                }

                // Tombol Aksi
                item {
                    OutlinedButton(
                        onClick = onNavigateToSettings,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Account Settings")
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = onLogout,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Red,
                            contentColor = Color.White
                        )
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "Log Out",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Log Out")
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }

        // Dialog Pratinjau & Ganti Foto Profil
        if (showPhotoDialog) {
            AlertDialog(
                onDismissRequest = { showPhotoDialog = false },
                title = {
                    Text(
                        text = "Profile Picture",
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Image(
                            painter = profilePainter,
                            contentDescription = "Full Profile Picture",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(180.dp)
                                .clip(CircleShape)
                        )
                        Text(
                            text = userName,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Green40,
                            contentColor = Color.White
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.PhotoLibrary,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Change Photo")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showPhotoDialog = false }) {
                        Text("Close")
                    }
                }
            )
        }
    }
}

@Composable
private fun ProfileInfoRow(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Green40,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}