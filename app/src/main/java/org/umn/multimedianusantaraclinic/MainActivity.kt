package org.umn.multimedianusantaraclinic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.umn.multimedianusantaraclinic.data.defaultActivityDetail
import org.umn.multimedianusantaraclinic.data.mockActivityDetailMap
import org.umn.multimedianusantaraclinic.ui.screen.ActivityDetailsScreen
import org.umn.multimedianusantaraclinic.ui.screen.ActivityListScreen
import org.umn.multimedianusantaraclinic.ui.screen.HomeScreen
import org.umn.multimedianusantaraclinic.ui.screen.HotlineScreen
import org.umn.multimedianusantaraclinic.ui.screen.IntroductionScreen
import org.umn.multimedianusantaraclinic.ui.screen.SplashScreen
import org.umn.multimedianusantaraclinic.ui.theme.MultimediaNusantaraClinicTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MultimediaNusantaraClinicTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainAppContent(
                        modifier = Modifier.padding(innerPadding)
                    )
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

    // Make sures that the NavBar will not be shown when SplashScreen & Introduction Slides are ongoing
    val showBottomBar = currentRoute != Screen.SplashScreen.route && currentRoute != Screen.Introduction.route

    Scaffold(
        bottomBar = {
            // Added the condition for when NavBar should appear
            if (showBottomBar) {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentRoute == Screen.Home.route,
                        onClick = { 
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Home.route) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            } 
                        },
                        icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                        label = { Text("Home") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == Screen.Hotline.route,
                        onClick = { 
                            navController.navigate(Screen.Hotline.route) {
                                popUpTo(Screen.Home.route) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            } 
                        },
                        icon = { Icon(Icons.Filled.Phone, contentDescription = "Hotline") },
                        label = { Text("Hotline") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == Screen.ActivityList.route,
                        onClick = { 
                            navController.navigate(Screen.ActivityList.route) {
                                popUpTo(Screen.Home.route) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            } 
                        },
                        icon = { Icon(Icons.Filled.History, contentDescription = "Activity") },
                        label = { Text("Activity") }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.SplashScreen.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Intro of the App
            composable( Screen.SplashScreen.route ) { SplashScreen(
                onTimeout = {
                    navController.navigate(Screen.Introduction.route) {
                        popUpTo(Screen.SplashScreen.route) { inclusive = true }
                    }
                }
            ) }
            // Proceeds to Introduction.kt
            composable( Screen.Introduction.route ) {
                IntroductionScreen(
                    onFinished = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Introduction.route) { inclusive = true }
                        }
                    }
                )
            }
            composable( Screen.Home.route ) { HomeScreen() }
            composable( Screen.ActivityList.route ) { 
                ActivityListScreen(
                    onItemClick = { activityId -> 
                        navController.navigate(Screen.ActivityDetail.createRoute(activityId))
                    }
                ) 
            }
            composable( Screen.Hotline.route ) { HotlineScreen() }
            composable(
                route = Screen.ActivityDetail.route,
                arguments = listOf(navArgument("activityId") { type = NavType.StringType })
            ) { backStackEntry ->
                val activityId = backStackEntry.arguments?.getString("activityId") ?: "ID-1234-5678"
                val detailData = mockActivityDetailMap[activityId] ?: defaultActivityDetail
                ActivityDetailsScreen(
                    data = detailData,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MultimediaNusantaraClinicTheme {
        MainAppContent()
    }
}