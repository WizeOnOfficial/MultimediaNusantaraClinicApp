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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.umn.multimedianusantaraclinic.Screen
import org.umn.multimedianusantaraclinic.ui.theme.Black
import org.umn.multimedianusantaraclinic.ui.theme.MultimediaNusantaraClinicTheme
import org.umn.multimedianusantaraclinic.ui.theme.White

sealed class BottomNavItem(
    val route: String?,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomNavItem(Screen.Home.route, "Home", Icons.Default.Home)
    object Activity : BottomNavItem(Screen.ActivityList.route, "Activity", Icons.Default.DateRange)
    object Hotline : BottomNavItem(Screen.Hotline.route, "Hotline", Icons.Default.Phone)
    object Profile : BottomNavItem(Screen.Profile.route, "Profile", Icons.Default.Person)
    object Settings : BottomNavItem(Screen.Settings.route, "Settings", Icons.Default.Settings)
}

// Native Feature - Opens the phone app on user's phone. Not yet apply - UNUSED
//fun triggerEmergencyCall(context: Context, phoneNumber: String = "112") {
//    val intent = Intent(Intent.ACTION_DIAL).apply {
//        data = Uri.parse("tel:$phoneNumber")
//    }
//    context.startActivity(intent)
//}

@Composable
fun BottomNavbar(navController: NavHostController) {
    val screens = listOf(
        BottomNavItem.Home,
        BottomNavItem.Activity,
        BottomNavItem.Profile,
        BottomNavItem.Settings,
        BottomNavItem.Hotline
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination

    val screensWithBottomNavbar = screens.any { it.route == currentRoute?.route }
    if(screensWithBottomNavbar) {
        CustomBottomNavbar(navController, currentRoute)
    }
}

@Composable
fun CustomBottomNavbar(
    navController: NavHostController,
    currentDestination: NavDestination?
) {
    val raisedTonalColor = MaterialTheme.colorScheme.surfaceColorAtElevation(6.dp)
    val leftItems = listOf(BottomNavItem.Home, BottomNavItem.Activity)
    val rightItems = listOf(BottomNavItem.Profile, BottomNavItem.Settings)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .height(96.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
            color = raisedTonalColor,
            shadowElevation = 12.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Left Tabs
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    leftItems.forEach { item ->
//                        OLD NAVITEM IMPLEMENTATION
//                        NavItem(
//                            icon = item.icon,
//                            label = item.title,
//                            isSelected = currentRoute == item.route,
//                            onClick = {
//                                item.route?.let { targetRoute ->
//                                    if (currentRoute != targetRoute) {
//                                        onNavigateToRoute(targetRoute)
//                                    }
//                                }
//                            }
//                        )

                        NavItem(
                            screen = item,
                            icon = item.icon,
                            label = item.title,
                            currentDestination = currentDestination,
                            navController = navController,
                        )
                    }
                }

                // Center FAB Gap
                Spacer(modifier = Modifier.width(64.dp))

                // Right Tabs
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    rightItems.forEach { item ->
//                        OLD NAVITEM IMPLEMENTATION
//                        NavItem(
//                            icon = item.icon,
//                            label = item.title,
//                            isSelected = currentRoute == item.route,
//                            onClick = {
//                                item.route?.let { targetRoute ->
//                                    if (currentRoute != targetRoute) {
//                                        onNavigateToRoute(targetRoute)
//                                    }
//                                }
//                            }
//                        )

                        NavItem(
                            screen = item,
                            icon = item.icon,
                            label = item.title,
                            currentDestination = currentDestination,
                            navController = navController,
                        )
                    }
                }
            }
        }

        // Floating Emergency Dial Button
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = 8.dp)
                .size(64.dp)
                .shadow(8.dp, CircleShape)
                .background(Color(0xFFE53935), CircleShape)
                .clip(CircleShape)
                .clickable {
                    BottomNavItem.Hotline.route?.let { navController.navigate(it) }
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = BottomNavItem.Hotline.icon,
                contentDescription = BottomNavItem.Hotline.title,
                tint = White,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
private fun NavItem(
    screen: BottomNavItem,
    icon: ImageVector,
    label: String,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val isSelected = currentDestination?.hierarchy?.any {
        it.route == screen.route
    } == true
    val activeColor = MaterialTheme.colorScheme.primary
    val inactiveColor = MaterialTheme.colorScheme.onSurface

    Column(
        modifier = Modifier
            .clickable(onClick = {
                screen.route?.let {
                    navController.navigate(it) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                }
            })
            .widthIn(min = 54.dp)
            .clip(shape = RoundedCornerShape(32.dp))
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
            style = MaterialTheme.typography.labelSmall,
            color = if (isSelected) activeColor else inactiveColor
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavbarPreview() {
    MultimediaNusantaraClinicTheme {
        BottomNavbar(
            navController = rememberNavController()
        )
    }
}