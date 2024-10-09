package ir.erfansn.composablescreens.food.kristina_cookie

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
import ir.erfansn.composablescreens.common.LocalNavAnimatedContentScope
import ir.erfansn.composablescreens.food.kristina_cookie.feature.cart.CartRoute
import ir.erfansn.composablescreens.food.kristina_cookie.feature.cart.CartViewModel
import ir.erfansn.composablescreens.food.kristina_cookie.feature.home.HomeRoute
import ir.erfansn.composablescreens.food.kristina_cookie.feature.home.HomeViewModel
import ir.erfansn.composablescreens.food.kristina_cookie.feature.product.ProductRoute
import ir.erfansn.composablescreens.food.kristina_cookie.feature.product.ProductViewModel
import ir.erfansn.composablescreens.food.kristina_cookie.ui.FoodTheme
import ir.erfansn.composablescreens.food.kristina_cookie.ui.util.sharedElementAnimSpec

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

fun NavGraphBuilder.foodNavGraph(navController: NavController) {
    navigation(startDestination = FoodNavGraph.HomeRoute, route = FoodRoute) {
        foodComposable(
            FoodNavGraph.HomeRoute,
            enterTransition = {
                fadeIn(animationSpec = sharedElementAnimSpec())
            },
            exitTransition = {
                fadeOut(animationSpec = sharedElementAnimSpec())
            },
        ) {
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
            arguments = FoodNavGraph.ProductRoute.Args,
            enterTransition = {
                fadeIn(animationSpec = sharedElementAnimSpec())
            },
            exitTransition = {
                fadeOut(animationSpec = sharedElementAnimSpec())
            }
        ) {
            val viewModel = viewModel<ProductViewModel>()
            ProductRoute(
                viewModel = viewModel,
                onNavigateToCart = {
                    if (navController.previousBackStackEntry?.destination?.route == FoodNavGraph.CartRoute) {
                        // TODO: Should report this, this worked to restore state (rememberSaveable) but also saving argument passed to route
                        // navController.popBackStack(route = it.destination.route!!, inclusive = true, saveState = true)
                        navController.popBackStack()
                    } else {
                        navController.navigate(FoodNavGraph.CartRoute) {
                            popUpTo(FoodNavGraph.HomeRoute) {
                                // saveState = true
                            }
                        }
                    }
                },
                onBackClick = { navController.popBackStack() }
            )
        }
        foodComposable(
            FoodNavGraph.CartRoute,
            enterTransition = {
                slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Start, animationSpec = sharedElementAnimSpec())
            },
            exitTransition = {
                slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.End, animationSpec = sharedElementAnimSpec())
            },
        ) {
            val viewModel = viewModel<CartViewModel>()
            CartRoute(
                onNavigateToHome = {
                    // TODO: Should report this, state restoration when navigation is slowly that pop operation (rememberSaveable)
                    /*navController.navigate(FoodNavGraph.HomeRoute) {
                        launchSingleTop = true
                        popUpTo(FoodNavGraph.HomeRoute)
                    }*/
                    navController.popBackStack(route = FoodNavGraph.HomeRoute, inclusive = false)
                },
                onNavigateToProduct = {
                    navController.navigate(FoodNavGraph.ProductRoute(it)) {
                        // restoreState = true
                    }
                },
                viewModel = viewModel
            )
        }
    }
}

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
