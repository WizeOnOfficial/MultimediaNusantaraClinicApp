package org.umn.multimedianusantaraclinic.ui.screen.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import okhttp3.Route
import org.umn.multimedianusantaraclinic.Graph
import org.umn.multimedianusantaraclinic.Screen
import org.umn.multimedianusantaraclinic.data.defaultActivityDetail
import org.umn.multimedianusantaraclinic.data.mockActivityDetailMap
import org.umn.multimedianusantaraclinic.ui.screen.DashboardScreen
import org.umn.multimedianusantaraclinic.ui.screen.HotlineScreen
import org.umn.multimedianusantaraclinic.ui.screen.activity.ActivityDetailsScreen
import org.umn.multimedianusantaraclinic.ui.screen.activity.ActivityListScreen

@Composable
fun HomeNavigationGraph(
    navController: NavHostController
) {
    NavHost (
        navController = navController,
        route = Graph.Home.route,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            DashboardScreen()
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

        composable(route = Screen.Hotline.route) {
            HotlineScreen()
        }
    }
}