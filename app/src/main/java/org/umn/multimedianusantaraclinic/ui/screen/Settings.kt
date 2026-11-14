package org.umn.multimedianusantaraclinic.ui.screen

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import org.umn.multimedianusantaraclinic.data.sampleActivities
import org.umn.multimedianusantaraclinic.notification.ClinicNotificationHelper
import org.umn.multimedianusantaraclinic.ui.theme.Green40
import org.umn.multimedianusantaraclinic.ui.theme.Red

@Composable
fun SettingsScreen(
    onNavigateToProfile: () -> Unit = {},
    onNavigateToHotline: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    val context = LocalContext.current

    var appointmentReminder by remember { mutableStateOf(false) }
    var medicineReminder by remember { mutableStateOf(false) }
    var showAboutDialog by remember { mutableStateOf(false) }
    var pendingAction by remember { mutableStateOf<(() -> Unit)?>(null) }

    // Mengambil data nyata dari ActivityListData.kt
    val ongoingActivity = remember {
        sampleActivities.firstOrNull { it.status == ActivityStatus.ONGOING }
    }
    val medicineActivity = remember {
        sampleActivities.firstOrNull { it.subCategory == "Medicine Request" }
    }

    // Launcher untuk meminta izin notifikasi Android 13+
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            pendingAction?.invoke()
        } else {
            Toast.makeText(
                context,
                "Izin notifikasi ditolak. Aktifkan di pengaturan HP Anda.",
                Toast.LENGTH_SHORT
            ).show()
        }
        pendingAction = null
    }

    fun executeWithNotificationPermission(action: () -> Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val hasPermission = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if (hasPermission) {
                action()
            } else {
                pendingAction = action
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        } else {
            action()
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Header (Diselaraskan dengan Home.kt)
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Settings",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1B5E20)
                )
            }

            // Bagian Akun
            item {
                SettingsSectionTitle("Account")
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column {
                        SettingsNavigationItem(
                            icon = Icons.Default.Person,
                            title = "Personal & Medical Details",
                            subtitle = "View NIM, blood type, and emergency contact",
                            onClick = onNavigateToProfile
                        )
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                        SettingsNavigationItem(
                            icon = Icons.Default.Lock,
                            title = "Privacy & Security",
                            subtitle = "UMN SSO authentication active",
                            onClick = { }
                        )
                    }
                }
            }

            // Bagian Notifikasi
            item {
                SettingsSectionTitle("Notifications")
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column {
                        SettingsSwitchItem(
                            icon = Icons.Default.NotificationsActive,
                            title = "Service & Appointment Alerts",
                            subtitle = "Instant updates for ongoing clinic requests",
                            checked = appointmentReminder,
                            onCheckedChange = { isChecked ->
                                if (isChecked) {
                                    executeWithNotificationPermission {
                                        appointmentReminder = true
                                        val title = ongoingActivity?.title ?: "Clinic Service Update"
                                        val desc = ongoingActivity?.description
                                            ?: "Your clinic appointment reminder is now active."
                                        ClinicNotificationHelper.showInstantNotification(
                                            context = context,
                                            notificationId = 1001,
                                            title = "UMN Clinic: $title",
                                            message = desc
                                        )
                                    }
                                } else {
                                    appointmentReminder = false
                                    ClinicNotificationHelper.cancelScheduledNotification(context, 1001)
                                }
                            }
                        )
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                        SettingsSwitchItem(
                            icon = Icons.Default.Medication,
                            title = "Prescription Alerts (5s Demo)",
                            subtitle = "Scheduled reminder for prescribed medicine",
                            checked = medicineReminder,
                            onCheckedChange = { isChecked ->
                                if (isChecked) {
                                    executeWithNotificationPermission {
                                        medicineReminder = true
                                        val medDesc = medicineActivity?.description
                                            ?: "Time to take your prescribed medicine."
                                        ClinicNotificationHelper.scheduleNotification(
                                            context = context,
                                            notificationId = 1002,
                                            title = "Prescription Reminder",
                                            message = medDesc,
                                            delayMillis = 5000L
                                        )
                                        Toast.makeText(
                                            context,
                                            "Pengingat obat dijadwalkan muncul dalam 5 detik",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } else {
                                    medicineReminder = false
                                    ClinicNotificationHelper.cancelScheduledNotification(context, 1002)
                                }
                            }
                        )
                    }
                }
            }

            // Bagian Bantuan & Informasi
            item {
                SettingsSectionTitle("Support & Info")
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column {
                        SettingsNavigationItem(
                            icon = Icons.AutoMirrored.Filled.HelpOutline,
                            title = "Clinic Emergency & Help",
                            subtitle = "Contact Multimedia Nusantara Clinic",
                            onClick = onNavigateToHotline
                        )
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                        SettingsNavigationItem(
                            icon = Icons.Default.Info,
                            title = "About App",
                            subtitle = "Version 1.0.0",
                            onClick = { showAboutDialog = true }
                        )
                    }
                }
            }

            // Tombol Log Out
            item {
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
                Spacer(modifier = Modifier.height(20.dp))
            }
        }

        if (showAboutDialog) {
            AlertDialog(
                onDismissRequest = { showAboutDialog = false },
                title = { Text("Multimedia Nusantara Clinic") },
                text = {
                    Text("Aplikasi layanan kesehatan kampus Universitas Multimedia Nusantara untuk pendaftaran konsultasi, riwayat medis, dan panggilan darurat.")
                },
                confirmButton = {
                    TextButton(onClick = { showAboutDialog = false }) {
                        Text("Close", color = Green40)
                    }
                }
            )
        }
    }
}

@Composable
private fun SettingsSectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(bottom = 10.dp)
    )
}

@Composable
private fun SettingsNavigationItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Green40,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            modifier = Modifier.size(16.dp)
        )
    }
}

@Composable
private fun SettingsSwitchItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Green40,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Green40
            )
        )
    }
}