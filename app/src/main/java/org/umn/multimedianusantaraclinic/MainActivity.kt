package org.umn.multimedianusantaraclinic

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.umn.multimedianusantaraclinic.data.defaultActivityDetail
import org.umn.multimedianusantaraclinic.data.mockActivityDetailMap
import org.umn.multimedianusantaraclinic.notification.ClinicNotificationHelper
import org.umn.multimedianusantaraclinic.ui.component.CustomBottomNavbar
import org.umn.multimedianusantaraclinic.ui.screen.ActivityDetailsScreen
import org.umn.multimedianusantaraclinic.ui.screen.ActivityListScreen
import org.umn.multimedianusantaraclinic.ui.screen.HomeScreen
import org.umn.multimedianusantaraclinic.ui.screen.HotlineScreen
import org.umn.multimedianusantaraclinic.ui.screen.Login
import org.umn.multimedianusantaraclinic.ui.screen.ProfileScreen
import org.umn.multimedianusantaraclinic.ui.screen.SettingsScreen
import org.umn.multimedianusantaraclinic.ui.screen.SplashScreen
import org.umn.multimedianusantaraclinic.ui.theme.MultimediaNusantaraClinicTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        ClinicNotificationHelper.createNotificationChannel(this)
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

    val context = LocalContext.current
    var profileImageUri by remember {
        mutableStateOf<Uri?>(
            context.filesDir.listFiles()
                ?.firstOrNull { it.name.startsWith("profile_picture_") }
                ?.let { Uri.fromFile(it) }
        )
    }

    val topLevelRoutes = listOf(
        Screen.Home.route,
        Screen.ActivityList.route,
        Screen.Hotline.route,
        Screen.Profile.route,
        Screen.Settings.route
    )
    val shouldShowBottomBar = currentRoute in topLevelRoutes

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding(),
        bottomBar = {
            if (shouldShowBottomBar) {
                CustomBottomNavbar(
                    currentRoute = currentRoute,
                    onNavigateToRoute = { targetRoute ->
                        if (currentRoute != targetRoute) {
                            navController.navigate(targetRoute) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    emergencyNumber = "02154220808"
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.SplashScreen.route,
            modifier = Modifier.padding(innerPadding),
            // Global transitions for all destinations
            enterTransition = { fadeIn(animationSpec = tween(400)) },
            exitTransition = { fadeOut(animationSpec = tween(400)) },
            popEnterTransition = { fadeIn(animationSpec = tween(400)) },
            popExitTransition = { fadeOut(animationSpec = tween(400)) }
        ) {
            composable(
                Screen.SplashScreen.route,
                exitTransition = {
                    fadeOut(animationSpec = tween(600))
                }
            ) {
                SplashScreen(
                    onTimeout = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.SplashScreen.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(
                Screen.Login.route,
                enterTransition = {
                    fadeIn(animationSpec = tween(600))
                }
            ) {
                Login(
                    onLoginSuccess = { userEmail ->
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(Screen.Home.route) {
                HomeScreen(
                    userName = "Barbara",
                    profileImageUri = profileImageUri,
                    onNavigateToLogin = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Home.route) { inclusive = true }
                        }
                    },
                    onNavigateToRoute = { targetRoute ->
                        if (currentRoute != targetRoute) {
                            navController.navigate(targetRoute) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }

            composable(Screen.ActivityList.route) {
                ActivityListScreen(
                    onItemClick = { activityId ->
                        navController.navigate(Screen.ActivityDetail.createRoute(activityId))
                    }
                )
            }

            composable(
                route = Screen.ActivityDetail.route,
                arguments = listOf(navArgument("activityId") { type = NavType.StringType })
            ) { backStackEntry ->
                val activityId = backStackEntry.arguments?.getString("activityId")
                val detailData = mockActivityDetailMap[activityId] ?: defaultActivityDetail

                ActivityDetailsScreen(
                    data = detailData,
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable(Screen.Hotline.route) {
                HotlineScreen()
            }

            composable(Screen.Profile.route) {
                ProfileScreen(
                    userName = "Barbara",
                    profileImageUri = profileImageUri,
                    onProfileImageChange = { newUri ->
                        profileImageUri = newUri
                    },
                    onNavigateToSettings = {
                        navController.navigate(Screen.Settings.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onLogout = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }

            composable(Screen.Settings.route) {
                SettingsScreen(
                    onNavigateToProfile = {
                        navController.navigate(Screen.Profile.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onNavigateToHotline = {
                        navController.navigate(Screen.Hotline.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onLogout = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
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