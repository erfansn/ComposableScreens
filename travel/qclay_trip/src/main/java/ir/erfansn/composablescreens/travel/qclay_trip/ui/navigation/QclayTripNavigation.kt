package ir.erfansn.composablescreens.travel.qclay_trip.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import ir.erfansn.composablescreens.travel.qclay_trip.details.TravelDetailsRoute
import ir.erfansn.composablescreens.travel.qclay_trip.home.TravelHomeRoute
import kotlinx.serialization.Serializable

@Serializable
data object QclayTripRoute

@Serializable
private data object HomeRoute
@Serializable
private data object DetailsRoute

// TODO: Introduce ext fun to provide theme
fun NavGraphBuilder.qclayTripNavGraph(navController: NavController) {
    navigation<QclayTripRoute>(
        startDestination = HomeRoute
    ) {
        composable<HomeRoute> {
            TravelHomeRoute(
                onTravelGroupItemClick = {
                    navController.navigate(DetailsRoute)
                }
            )
        }
        composable<DetailsRoute> {
            TravelDetailsRoute()
        }
    }
}
