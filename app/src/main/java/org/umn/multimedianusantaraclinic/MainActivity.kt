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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.umn.multimedianusantaraclinic.ui.screen.ActivityDetailScreen
import org.umn.multimedianusantaraclinic.ui.screen.ActivityScreen
import androidx.navigation.navArgument
import org.umn.multimedianusantaraclinic.data.mockActivityDetailMap
import org.umn.multimedianusantaraclinic.data.wheelchairRequestMock
import org.umn.multimedianusantaraclinic.ui.screen.ActivityDetailsScreen
import org.umn.multimedianusantaraclinic.ui.screen.HomeScreen
import org.umn.multimedianusantaraclinic.ui.screen.HotlineScreen
import org.umn.multimedianusantaraclinic.ui.screen.SplashScreen
import org.umn.multimedianusantaraclinic.ui.theme.MultimediaNusantaraClinicTheme

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
                    onClick = { navController.navigate(Screen.Activity.route) },
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
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.SplashScreen.route) { inclusive = true }
                    }
                }
            ) }
            composable( Screen.Home.route ) { HomeScreen() }
            composable( Screen.Activity.route ) {
                ActivityScreen(
                    onItemClick = { navController.navigate(Screen.ActivityDetail.route) }
                )
            }
            composable( Screen.Hotline.route ) { HotlineScreen() }
            composable(
                route = Screen.ActivityDetail.route,
                arguments = listOf(navArgument("argumentId") { type = NavType.StringType })
            ) { backStackEntry ->
                val activityId = backStackEntry.arguments?.getString("activityId")
                val detailData = mockActivityDetailMap[activityId] ?: wheelchairRequestMock

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
fun MainAppPreview() {
    MultimediaNusantaraClinicTheme {
         MainAppContent()
    }
}