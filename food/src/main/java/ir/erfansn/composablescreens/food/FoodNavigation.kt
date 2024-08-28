package ir.erfansn.composablescreens.food

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.SizeTransform
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocal
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
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
import ir.erfansn.composablescreens.food.ui.FoodTheme

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
        foodComposable(FoodNavGraph.HomeRoute) {
            val viewModel = viewModel<HomeViewModel>()
            HomeRoute(
                onNavigateToProduct = {
                    navController.navigate(FoodNavGraph.ProductRoute(it))
                },
                onNavigateToCart = {
                    navController.navigate(FoodNavGraph.CartRoute)
                },
                viewModel = viewModel
            )
        }
        foodComposable(
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
        foodComposable(FoodNavGraph.CartRoute) {
            val viewModel = viewModel<CartViewModel>()
            CartRoute(
                onNavigateToHome = {
                    navController.navigate(FoodNavGraph.HomeRoute) {
                        popUpTo(FoodNavGraph.HomeRoute) {
                            inclusive = true
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

val LocalNavAnimatedContentScope = staticCompositionLocalOf<AnimatedContentScope?> { null }

val CompositionLocal<AnimatedContentScope?>.requiredCurrent
    @Composable
    get() = current ?: error("LocalNavAnimatedContentScope not provided from navigation route")

private fun NavGraphBuilder.foodComposable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    enterTransition:
    (@JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? =
        null,
    exitTransition:
    (@JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? =
        null,
    popEnterTransition:
    (@JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? =
        enterTransition,
    popExitTransition:
    (@JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? =
        exitTransition,
    sizeTransform:
    (@JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> SizeTransform?)? =
        null,
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = route,
        arguments = arguments,
        deepLinks = deepLinks,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
        sizeTransform = sizeTransform,
    ) {
        FoodTheme {
            CompositionLocalProvider(LocalNavAnimatedContentScope provides this) {
                this.content(it)
            }
        }
    }
}
