package org.umn.multimedianusantaraclinic.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.keepScreenOn
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.umn.multimedianusantaraclinic.ui.component.BottomNavbar
import org.umn.multimedianusantaraclinic.ui.screen.graph.HomeNavigationGraph
import org.umn.multimedianusantaraclinic.ui.theme.MultimediaNusantaraClinicTheme

@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()) {
    Scaffold(
        bottomBar = { BottomNavbar(navController = navController) },
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding())
        ) {
            HomeNavigationGraph(navController = navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MultimediaNusantaraClinicTheme {
        HomeScreen()
    }
}