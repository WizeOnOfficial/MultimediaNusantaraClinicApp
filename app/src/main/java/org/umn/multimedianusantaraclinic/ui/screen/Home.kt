package org.umn.multimedianusantaraclinic.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.umn.multimedianusantaraclinic.ui.component.BottomNavbar
import org.umn.multimedianusantaraclinic.ui.screen.graph.HomeNavigationGraph

@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController(),
    onLogout: () -> Unit = {}
) {
    Scaffold(
        bottomBar = {
            BottomNavbar(navController = navController)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            HomeNavigationGraph(
                navController = navController,
                onLogoutToAuth = onLogout
            )
        }
    }
}