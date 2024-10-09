package ir.erfansn.composablescreens.travel.qclay_trip.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import ir.erfansn.composablescreens.travel.qclay_trip.details.TravelDetailsRoute
import ir.erfansn.composablescreens.travel.qclay_trip.home.TravelHomeRoute

private const val TRAVEL_ROUTE = "travel"
private const val TRAVEL_HOME_ROUTE = "home"
private const val TRAVEL_DETAILS_ROUTE = "details"

fun NavGraphBuilder.travelNavigationGraph(navController: NavController) {
    navigation(
        route = TRAVEL_ROUTE,
        startDestination = TRAVEL_HOME_ROUTE
    ) {
        composable(TRAVEL_HOME_ROUTE) {
            TravelHomeRoute(
                onTravelGroupItemClick = {
                    navController.navigate(TRAVEL_DETAILS_ROUTE)
                }
            )
        }
        composable(TRAVEL_DETAILS_ROUTE) {
            TravelDetailsRoute()
        }
    }
}
