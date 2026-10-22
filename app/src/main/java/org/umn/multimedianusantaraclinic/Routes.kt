package org.umn.multimedianusantaraclinic

sealed class Screen(val route: String, val title: String) {
    object SplashScreen : Screen("splashScreen", "Splash Screen")
    object Home : Screen("home", "Home")
    object Login : Screen("login", "Login")
    object Hotline : Screen("hotline", "Hotline")

    object ActivityList : Screen("activity", "Activity")

    object ActivityDetail : Screen("activity_detail/{activityId}", "Activity Detail") {
        fun createRoute(activityId: String) = "activity_detail/$activityId"
    }
}