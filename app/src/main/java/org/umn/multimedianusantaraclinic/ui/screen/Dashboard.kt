package org.umn.multimedianusantaraclinic.ui.screen

import kotlin.collections.chunked
import kotlin.collections.forEach
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.LocalPharmacy
import androidx.compose.material.icons.filled.MedicalInformation
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Science
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.umn.multimedianusantaraclinic.Screen
import org.umn.multimedianusantaraclinic.ui.component.dashboard.DashboardMenuItem
import org.umn.multimedianusantaraclinic.ui.theme.Gray
import org.umn.multimedianusantaraclinic.ui.theme.MultimediaNusantaraClinicTheme
import org.umn.multimedianusantaraclinic.ui.theme.White

data class DashboardItemData(
    val id: String,
    val title: String,
    val icon: ImageVector,
    val route: String? = null
)

val dashboardItems = listOf(
    DashboardItemData("doctor", "Doctor", Icons.Default.MedicalServices, Screen.Hotline.route),
    DashboardItemData("pharmacy", "Pharmacy", Icons.Default.LocalPharmacy, Screen.ActivityList.route),
    DashboardItemData("ambulance", "Ambulance", Icons.Default.MedicalInformation, Screen.Hotline.route),
    DashboardItemData("schedule", "Schedule", Icons.Default.Event, Screen.ActivityList.route),
    DashboardItemData("records", "Records", Icons.Default.ReceiptLong, Screen.ActivityList.route),
    DashboardItemData("lab", "Lab Test", Icons.Default.Science, Screen.ActivityList.route),
    DashboardItemData("counseling", "Counseling", Icons.Default.Psychology, Screen.Hotline.route),
    DashboardItemData("facility", "Facility", Icons.Default.LocalHospital, Screen.ActivityList.route)
)

@Composable
fun DashboardScreen(
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
                .padding(16.dp)
        ) {
            // Background Decorative Accent
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .offset(x = 60.dp, y = (-40).dp)
                    .background(MaterialTheme.colorScheme.primary, CircleShape)
                    .align(Alignment.TopEnd)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding(),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Header Profile
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Hello",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = userName,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        // Profile Avatar
                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .clip(CircleShape)
                                .background(Gray)
                                .clickable { onNavigateToLogin() },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = userName.take(1).uppercase(),
                                style = MaterialTheme.typography.displaySmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }

                // Grid Menu Items
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        dashboardItems.chunked(4).forEach { rowItems ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                rowItems.forEach { item ->
                                    DashboardMenuItem(
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
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(144.dp)
                            .clickable { onNavigateToRoute(Screen.ActivityList.route) },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Gray)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "HEY WHATSUP!",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    MultimediaNusantaraClinicTheme {
        DashboardScreen(
            onNavigateToRoute = {},
            onNavigateToLogin = {}
        )
    }
}
