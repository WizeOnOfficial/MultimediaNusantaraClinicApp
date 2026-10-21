package org.umn.multimedianusantaraclinic.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.umn.multimedianusantaraclinic.ui.component.CustomBottomNavbar

@Composable
fun DashboardScreen(
    userName: String = "Barbara",
    onNavigateToLogin: () -> Unit = {}
) {
    var activeTab by remember { mutableStateOf("Home") }

    Scaffold(
        bottomBar = {
            CustomBottomNavbar(
                selectedTab = activeTab,
                onTabSelected = { activeTab = it },
                emergencyNumber = "02154220808" // Ubah sesuai hotline klinik kampus
            )
        },
        containerColor = Color(0xFFFAFAFA)
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Aksen Dekoratif Lingkaran Hijau di Pojok Kanan Atas
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
                // Header Profil
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Hello",
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1B5E20)
                            )
                            Text(
                                text = userName,
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1B5E20)
                            )
                        }

                        // Avatar Profil Bulat
                        Box(
                            modifier = Modifier
                                .size(75.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFCFD8DC)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = userName.take(1).uppercase(),
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }

                // Grid 8 Menu Navigasi (2 Baris x 4 Kolom)
                item {
                    val menuList = listOf(
                        "Doctor", "Pharmacy", "Ambulance", "Schedule",
                        "Records", "Lab Test", "Counseling", "Facility"
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            menuList.take(4).forEach { name ->
                                ClinicMenuItem(name = name)
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            menuList.drop(4).take(4).forEach { name ->
                                ClinicMenuItem(name = name)
                            }
                        }
                    }
                }

                // Section Highlight
                item {
                    Text(
                        text = "Highlight",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0E0E0))
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "HEY WHATSUP!",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.Black
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

@Composable
fun ClinicMenuItem(name: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(62.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFD9D9D9))
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = name,
            fontSize = 12.sp,
            color = Color.Black
        )
    }
}