package org.umn.multimedianusantaraclinic.ui.screen.graph

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import okhttp3.Route
import org.umn.multimedianusantaraclinic.Graph
import org.umn.multimedianusantaraclinic.Screen
import org.umn.multimedianusantaraclinic.data.defaultActivityDetail
import org.umn.multimedianusantaraclinic.data.getHighlightById
import org.umn.multimedianusantaraclinic.data.mockActivityDetailMap
import org.umn.multimedianusantaraclinic.ui.screen.DashboardScreen
import org.umn.multimedianusantaraclinic.ui.screen.HighlightDetailScreen
import org.umn.multimedianusantaraclinic.ui.screen.HotlineScreen
import org.umn.multimedianusantaraclinic.ui.screen.ProfileScreen
import org.umn.multimedianusantaraclinic.ui.screen.SettingsScreen
import org.umn.multimedianusantaraclinic.ui.screen.activity.ActivityDetailsScreen
import org.umn.multimedianusantaraclinic.ui.screen.activity.ActivityListScreen

@Composable
fun HomeNavigationGraph(
    navController: NavHostController,
    onLogoutToAuth: () -> Unit = {}
) {
    val context = LocalContext.current

    var profileImageUri by remember {
        val savedFile = context.filesDir.listFiles()
            ?.firstOrNull { it.name.startsWith("profile_picture_") }
        mutableStateOf(savedFile?.let { Uri.fromFile(it) })
    }

    NavHost(
        navController = navController,
        route = Graph.Home.route,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            DashboardScreen(
                profileImageUri = profileImageUri,
                onNavigateToLogin = onLogoutToAuth,
                onNavigateToRoute = { targetRoute ->
                    navController.navigate(targetRoute) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }

        composable(route = Screen.ActivityList.route) {
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

        composable(
            route = Screen.HighlightDetail.route,
            arguments = listOf(navArgument("highlightId") { type = NavType.StringType })
        ) { backStackEntry ->
            val highlightId = backStackEntry.arguments?.getString("highlightId")
            val highlightItem = getHighlightById(highlightId)

            HighlightDetailScreen(
                item = highlightItem,
                onBackClick = { navController.popBackStack() },
                onContactClinicClick = {
                    navController.navigate(Screen.Hotline.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }

        composable(route = Screen.Hotline.route) {
            HotlineScreen()
        }

        composable(route = Screen.Profile.route) {
            ProfileScreen(
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
                onLogout = onLogoutToAuth
            )
        }

        composable(route = Screen.Settings.route) {
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
                onLogout = onLogoutToAuth
            )
        }
    }
}