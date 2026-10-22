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
import org.umn.multimedianusantaraclinic.Screen
import org.umn.multimedianusantaraclinic.ui.theme.Black
import org.umn.multimedianusantaraclinic.ui.theme.White

sealed class BottomNavItem(
    val route: String?,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomNavItem(Screen.Home.route, "Home", Icons.Default.Home)
    object Activity : BottomNavItem(Screen.ActivityList.route, "Activity", Icons.Default.DateRange)
    object Profile : BottomNavItem(null, "Profile", Icons.Default.Person)
    object Settings : BottomNavItem(null, "Settings", Icons.Default.Settings)
}

// Native Feature - Opens the phone app on user's phone. Not yet apply
//fun triggerEmergencyCall(context: Context, phoneNumber: String = "112") {
//    val intent = Intent(Intent.ACTION_DIAL).apply {
//        data = Uri.parse("tel:$phoneNumber")
//    }
//    context.startActivity(intent)
//}


@Composable
fun CustomBottomNavbar(
    currentRoute: String?,
    onNavigateToRoute: (String) -> Unit,
    emergencyNumber: String = "02154220808" // Hotline Klinik UMN / Darurat
) {
    val context = LocalContext.current
    val raisedTonalColor = MaterialTheme.colorScheme.surfaceColorAtElevation(6.dp)

    val leftItems = listOf(BottomNavItem.Home, BottomNavItem.Activity)
    val rightItems = listOf(BottomNavItem.Profile, BottomNavItem.Settings)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(96.dp)
            .background(Black),
        contentAlignment = Alignment.BottomCenter
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp)
                .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)),
            color = raisedTonalColor,
            shadowElevation = 12.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Left Tabs
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    leftItems.forEach { item ->
                        NavItem(
                            icon = item.icon,
                            label = item.title,
                            isSelected = currentRoute == item.route,
                            onClick = {
                                item.route?.let { targetRoute ->
                                    if (currentRoute != targetRoute) {
                                        onNavigateToRoute(targetRoute)
                                    }
                                }
                            }
                        )
                    }
                }

                // Center FAB Gap
                Spacer(modifier = Modifier.width(68.dp))

                // Right Tabs
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    rightItems.forEach { item ->
                        NavItem(
                            icon = item.icon,
                            label = item.title,
                            isSelected = currentRoute == item.route,
                            onClick = {
                                item.route?.let { targetRoute ->
                                    if (currentRoute != targetRoute) {
                                        onNavigateToRoute(targetRoute)
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }

        // Floating Emergency Dial Button
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = 6.dp)
                .size(62.dp)
                .shadow(8.dp, CircleShape)
                .background(Color(0xFFE53935), CircleShape)
                .clip(CircleShape)
                .clickable {
                    onNavigateToRoute(Screen.Hotline.route)
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Phone,
                contentDescription = "Emergency Call",
                tint = White,
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
    val activeColor = MaterialTheme.colorScheme.primary
    val inactiveColor = MaterialTheme.colorScheme.onPrimary

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