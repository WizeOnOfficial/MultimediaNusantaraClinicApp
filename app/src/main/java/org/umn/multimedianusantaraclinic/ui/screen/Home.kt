package org.umn.multimedianusantaraclinic.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.LocalPharmacy
import androidx.compose.material.icons.filled.MedicalInformation
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import okhttp3.Route
import org.umn.multimedianusantaraclinic.Screen
import org.umn.multimedianusantaraclinic.ui.component.HomeMenuItem

data class MenuItemData(
    val id: String,
    val title: String,
    val icon: ImageVector,
    val route: String? = null
)

val homeMenuItems = listOf(
    MenuItemData("doctor", "Doctor", Icons.Default.MedicalServices, Screen.Hotline.route),
    MenuItemData("pharmacy", "Pharmacy", Icons.Default.LocalPharmacy, Screen.ActivityList.route),
    MenuItemData("ambulance", "Ambulance", Icons.Default.MedicalInformation, Screen.Hotline.route),
    MenuItemData("schedule", "Schedule", Icons.Default.Event, Screen.ActivityList.route),
    MenuItemData("records", "Records", Icons.Default.ReceiptLong, Screen.ActivityList.route),
    MenuItemData("lab", "Lab Test", Icons.Default.Science, Screen.ActivityList.route),
    MenuItemData("counseling", "Counseling", Icons.Default.Psychology, Screen.Hotline.route),
    MenuItemData("facility", "Facility", Icons.Default.LocalHospital, Screen.ActivityList.route)
)

@Composable
fun HomeScreen(
    userName: String = "Barbara",
    onNavigateToLogin: () -> Unit = {},
    onNavigateToRoute: (String) -> Unit = {}
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        // Single root Box container
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Background Decorative Accent
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
                // Header Profile
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

                        // Profile Avatar
                        Box(
                            modifier = Modifier
                                .size(75.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFCFD8DC))
                                .clickable { onNavigateToLogin() },
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

                // Grid Menu Items
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        homeMenuItems.chunked(4).forEach { rowItems ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                rowItems.forEach { item ->
                                    HomeMenuItem(
                                        data = item,
                                        onClick = {
                                            item.route?.let { targetRoute ->
                                                onNavigateToRoute(targetRoute)
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                // Highlight Card
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
                            .height(140.dp)
                            .clickable { onNavigateToRoute(Screen.ActivityList.route) },
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

