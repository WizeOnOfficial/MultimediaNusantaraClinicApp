package org.umn.multimedianusantaraclinic

sealed class Screen(val route: String, val title: String) {
    object Introduction : Screen("introduction", "Introduction")
    object Home : Screen("home", "Home")
    object Login : Screen("login", "Login")
    object Hotline : Screen("hotline", "Hotline")

    object ActivityList : Screen("activity", "Activity")
    object SignUp : Screen("signup", "Sign Up")
    object Profile : Screen("profile", "Profile")
    object Settings : Screen("settings", "Settings")

    object HighlightDetail : Screen("highlight_detail/{highlightId}", "Highlight Detail") {
        fun createRoute(highlightId: String) = "highlight_detail/$highlightId"
    }

    object ActivityDetail : Screen("activity_detail/{activityId}", "Activity Detail") {
        fun createRoute(activityId: String) = "activity_detail/$activityId"
    }
}

sealed class Graph(val route: String) {
    object Root : Graph("root_graph")
    object Auth : Graph("auth_graph")
    object Home : Graph("home_graph")
    object Activity : Graph("auth_activity")
}