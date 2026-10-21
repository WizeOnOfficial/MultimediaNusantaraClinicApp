package org.umn.multimedianusantaraclinic.ui.component

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Native Feature: Membuka dialer telepon darurat langsung dari sistem Android
fun triggerEmergencyCall(context: Context, phoneNumber: String = "112") {
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:$phoneNumber")
    }
    context.startActivity(intent)
}

@Composable
fun CustomBottomNavbar(
    selectedTab: String = "Home",
    onTabSelected: (String) -> Unit = {},
    emergencyNumber: String = "02154220808" // Hotline Klinik UMN / Darurat
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(95.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        // Kontainer putih navbar
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp)
                .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)),
            color = Color.White,
            shadowElevation = 12.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Sisi Kiri
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    NavItem(
                        icon = Icons.Default.Home,
                        label = "Home",
                        isSelected = selectedTab == "Home",
                        onClick = { onTabSelected("Home") }
                    )
                    NavItem(
                        icon = Icons.Default.DateRange,
                        label = "Activity",
                        isSelected = selectedTab == "Activity",
                        onClick = { onTabSelected("Activity") }
                    )
                }

                // Spacer untuk tombol Emergency di tengah
                Spacer(modifier = Modifier.width(68.dp))

                // Sisi Kanan
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    NavItem(
                        icon = Icons.Default.Person,
                        label = "Profile",
                        isSelected = selectedTab == "Profile",
                        onClick = { onTabSelected("Profile") }
                    )
                    NavItem(
                        icon = Icons.Default.Settings,
                        label = "Settings",
                        isSelected = selectedTab == "Settings",
                        onClick = { onTabSelected("Settings") }
                    )
                }
            }
        }

        // Native Emergency Floating Action Button
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = 6.dp)
                .size(62.dp)
                .shadow(8.dp, CircleShape)
                .background(Color(0xFFE53935), CircleShape)
                .clip(CircleShape)
                .clickable {
                    triggerEmergencyCall(context, emergencyNumber)
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Phone,
                contentDescription = "Emergency Call",
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
private fun NavItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val activeColor = Color(0xFF2E7D32)
    val inactiveColor = Color.Gray

    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) activeColor else inactiveColor,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = label,
            fontSize = 11.sp,
            color = if (isSelected) activeColor else inactiveColor
        )
    }
}