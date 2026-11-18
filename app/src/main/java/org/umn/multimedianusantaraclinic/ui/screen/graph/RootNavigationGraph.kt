package org.umn.multimedianusantaraclinic.ui.screen.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.umn.multimedianusantaraclinic.Graph
import org.umn.multimedianusantaraclinic.Screen
import org.umn.multimedianusantaraclinic.ui.screen.HomeScreen

@Composable
fun RootNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Graph.Auth.route,
        route = Graph.Root.route
    ) {
        authNavigationGraph(navController = navController)
        composable(route = Graph.Home.route) {
            HomeScreen(
                onLogout = {
                    navController.navigate(Graph.Auth.route) {
                        popUpTo(Graph.Root.route) { inclusive = true }
                    }
                }
            )
        }
    }
}