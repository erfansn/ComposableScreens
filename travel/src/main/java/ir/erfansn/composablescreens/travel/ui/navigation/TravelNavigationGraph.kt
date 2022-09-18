package ir.erfansn.composablescreens.travel.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import ir.erfansn.composablescreens.travel.details.TravelDetailsRoute
import ir.erfansn.composablescreens.travel.home.TravelHomeRoute

fun NavGraphBuilder.travelNavigationGraph(navController: NavController) {
    navigation(
        route = "travel",
        startDestination = "home"
    ) {
        composable("home") {
            TravelHomeRoute(
                onTravelGroupItemClick = {
                    navController.navigate("details")
                }
            )
        }
        composable("details") {
            TravelDetailsRoute()
        }
    }
}