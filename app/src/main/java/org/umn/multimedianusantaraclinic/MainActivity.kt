package org.umn.multimedianusantaraclinic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.umn.multimedianusantaraclinic.ui.screen.ActivityListScreen
import org.umn.multimedianusantaraclinic.ui.screen.HotlineScreen
import org.umn.multimedianusantaraclinic.ui.screen.SplashScreen
import org.umn.multimedianusantaraclinic.ui.theme.MultimediaNusantaraClinicTheme
import org.umn.multimedianusantaraclinic.ui.screen.DashboardScreen
import androidx.navigation.compose.currentBackStackEntryAsState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MultimediaNusantaraClinicTheme {
                Surface {
                     MainAppContent()
                }
            }
        }
    }
}

@Composable
fun MainAppContent(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(Screen.Home.route) },
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(Screen.Hotline.route) },
                    icon = { Icon(Icons.Filled.Phone, contentDescription = "Hotline") },
                    label = { Text("Hotline") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(Screen.ActivityList.route) },
                    icon = { Icon(Icons.Filled.History, contentDescription = "Activity") },
                    label = { Text("Activity") }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.SplashScreen.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable( Screen.SplashScreen.route ) { SplashScreen(
                onTimeout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.SplashScreen.route) { inclusive = true }
                    }
                }
            ) }
            composable( Screen.ActivityList.route ) {
                ActivityListScreen(
                    onItemClick = { activityId ->
                        navController.navigate(Screen.ActivityDetail.createRoute(activityId))
                    }
                )
            }
            composable(Screen.Home.route) {
                DashboardScreen(
                    userName = "Barbara",
                    onNavigateToLogin = {
                        navController.navigate(Screen.Login.route)
                    }
                )
            }
            composable( Screen.Hotline.route ) { HotlineScreen() }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainAppPreview() {
    MultimediaNusantaraClinicTheme {
         MainAppContent()
    }
}