package ir.erfansn.composablescreens.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ir.erfansn.composablescreens.travel.ui.navigation.travelNavigationGraph
import ir.erfansn.composablescreens.ui.ComposableScreensApp

val routes = listOf(
    "travel"
)

@Composable
fun ComposableScreensNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "app"
    ) {
        composable("app") {
            ComposableScreensApp(
                onRouteClick = navController::navigate
            )
        }
        travelNavigationGraph(navController)
    }
}