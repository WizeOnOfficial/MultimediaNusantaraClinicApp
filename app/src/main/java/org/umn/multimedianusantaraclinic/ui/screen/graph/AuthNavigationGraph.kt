package org.umn.multimedianusantaraclinic.ui.screen.graph

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import org.umn.multimedianusantaraclinic.Graph
import org.umn.multimedianusantaraclinic.Screen
import org.umn.multimedianusantaraclinic.ui.screen.auth.IntroductionScreen
import org.umn.multimedianusantaraclinic.ui.screen.auth.LoginScreen
import org.umn.multimedianusantaraclinic.ui.screen.auth.SignUpScreen

fun NavGraphBuilder.authNavigationGraph(navController: NavHostController) {
    navigation(
        route = Graph.Auth.route,
        startDestination = Screen.Introduction.route
    ) {
        composable(route = Screen.Introduction.route) {
            IntroductionScreen(
                onFinished = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Introduction.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Graph.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onSignUpClick = {
                    navController.navigate(Screen.SignUp.route)
                }
            )
        }

        composable(route = Screen.SignUp.route) {
            SignUpScreen(
                onSignUpSuccess = { navController.popBackStack() },
                onBackToLoginClick = { navController.popBackStack() }
            )
        }
    }
}