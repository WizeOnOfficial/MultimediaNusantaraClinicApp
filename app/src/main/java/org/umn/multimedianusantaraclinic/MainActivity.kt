package org.umn.multimedianusantaraclinic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.umn.multimedianusantaraclinic.data.defaultActivityDetail
import org.umn.multimedianusantaraclinic.data.mockActivityDetailMap
import org.umn.multimedianusantaraclinic.ui.screen.activity.ActivityDetailsScreen
import org.umn.multimedianusantaraclinic.ui.screen.activity.ActivityListScreen
import org.umn.multimedianusantaraclinic.ui.screen.HomeScreen
import org.umn.multimedianusantaraclinic.ui.screen.HotlineScreen
import org.umn.multimedianusantaraclinic.ui.screen.auth.IntroductionScreen
import org.umn.multimedianusantaraclinic.ui.screen.graph.RootNavigationGraph
import org.umn.multimedianusantaraclinic.ui.theme.MultimediaNusantaraClinicTheme
import org.umn.multimedianusantaraclinic.ui.screen.Login
import org.umn.multimedianusantaraclinic.ui.screen.SignUpScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MultimediaNusantaraClinicTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    MainAppContent(
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
                RootNavigationGraph(navController = rememberNavController())
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

    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Introduction.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Proceeds to Introduction.kt
            composable( Screen.Introduction.route ) {
                IntroductionScreen(
                    onFinished = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Introduction.route) { inclusive = true }
                        }
                    },
                    onSignUpClick = { navController.navigate(Screen.SignUp.route) }
                )
            }
            composable(
                Screen.SignUp.route, enterTransition = {
                    fadeIn(animationSpec = tween(600))
                }) {
                SignUpScreen(
                    onSignUpSuccess = { navController.popBackStack() },
                    onBackToLoginClick = { navController.popBackStack() }
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
fun MainAppPreview() {
    MultimediaNusantaraClinicTheme {
        RootNavigationGraph(navController = rememberNavController())
    }
}