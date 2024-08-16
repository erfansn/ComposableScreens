package ir.erfansn.composablescreens.food

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import ir.erfansn.composablescreens.food.feature.cart.CartRoute
import ir.erfansn.composablescreens.food.feature.cart.CartViewModel
import ir.erfansn.composablescreens.food.feature.home.HomeRoute
import ir.erfansn.composablescreens.food.feature.home.HomeViewModel
import ir.erfansn.composablescreens.food.feature.product.ProductRoute
import ir.erfansn.composablescreens.food.feature.product.ProductViewModel

const val FoodRoute = "food"

private data object FoodNavGraph {
    const val HomeRoute = "home"

    data object ProductRoute {
        private const val IdKey = "id"

        val Args = listOf(navArgument(IdKey) { type = NavType.IntType })

        operator fun invoke(id: Int) = "product/$id"

        override fun toString() = "product/{$IdKey}"
    }

    const val CartRoute = "cart"
}

// TODO: Animate same content with shared element
fun NavGraphBuilder.foodNavGraph(navController: NavController) {
    navigation(startDestination = FoodNavGraph.HomeRoute, route = FoodRoute) {
        composable(FoodNavGraph.HomeRoute) {
            val viewModel = viewModel<HomeViewModel>()
            HomeRoute(
                onNavigateToProduct = {
                    // TODO: Wrap with safe caller
                    navController.navigate(FoodNavGraph.ProductRoute(it))
                },
                onNavigateToCart = {
                    navController.navigate(FoodNavGraph.CartRoute)
                },
                viewModel = viewModel
            )
        }
        composable(
            FoodNavGraph.ProductRoute.toString(),
            arguments = FoodNavGraph.ProductRoute.Args
        ) {
            val viewModel = viewModel<ProductViewModel>()
            ProductRoute(
                viewModel = viewModel,
                onNavigateToCart = { navController.navigate(FoodNavGraph.CartRoute) },
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(FoodNavGraph.CartRoute) {
            val viewModel = viewModel<CartViewModel>()
            CartRoute(
                onNavigateToHome = {
                    navController.navigate(FoodNavGraph.HomeRoute) {
                        // restoreState = true
                        popUpTo(FoodNavGraph.HomeRoute) {
                            // saveState = true
                        }
                    }
                },
                onNavigateToProduct = {
                    navController.navigate(FoodNavGraph.ProductRoute(it))
                },
                viewModel = viewModel
            )
        }
    }
}
