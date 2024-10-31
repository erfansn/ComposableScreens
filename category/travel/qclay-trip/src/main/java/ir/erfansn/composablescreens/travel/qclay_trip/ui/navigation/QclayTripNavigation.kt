package ir.erfansn.composablescreens.travel.qclay_trip.ui.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.SizeTransform
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import ir.erfansn.composablescreens.auto_nav_graph_wiring.core.AutoNavGraphWiring
import ir.erfansn.composablescreens.auto_nav_graph_wiring.core.ScreenOrientation
import ir.erfansn.composablescreens.common.LocalNavAnimatedContentScope
import ir.erfansn.composablescreens.travel.qclay_trip.details.DetailsRoute
import ir.erfansn.composablescreens.travel.qclay_trip.home.HomeRoute
import ir.erfansn.composablescreens.travel.qclay_trip.ui.theme.QclayTripTheme
import kotlinx.serialization.Serializable
import kotlin.reflect.KType

@Serializable
data object QclayTripRoute

@Serializable
internal data object HomeRoute
@Serializable
internal data object DetailsRoute

@AutoNavGraphWiring(
    name = "Qclay Trip",
    route = QclayTripRoute::class,
    startDestination = HomeRoute::class,
    screenOrientation = ScreenOrientation.Portrait
)
internal fun NavGraphBuilder.qclayTripNavGraph(navController: NavController) {
    qclayTripComposable<HomeRoute> {
        HomeRoute(
            onTravelGroupItemClick = {
                navController.navigate(DetailsRoute)
            }
        )
    }
    qclayTripComposable<DetailsRoute> {
        DetailsRoute()
    }
}

private inline fun <reified T : Any> NavGraphBuilder.qclayTripComposable(
    typeMap: Map<KType, @JvmSuppressWildcards NavType<*>> = emptyMap(),
    deepLinks: List<NavDeepLink> = emptyList(),
    noinline enterTransition:
    (@JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? =
        null,
    noinline exitTransition:
    (@JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? =
        null,
    noinline popEnterTransition:
    (@JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? =
        enterTransition,
    noinline popExitTransition:
    (@JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? =
        exitTransition,
    noinline sizeTransform:
    (@JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> SizeTransform?)? =
        null,
    crossinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable<T>(
        typeMap = typeMap,
        deepLinks = deepLinks,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
        sizeTransform = sizeTransform,
    ) {
        QclayTripTheme {
            CompositionLocalProvider(LocalNavAnimatedContentScope provides this) {
                this.content(it)
            }
        }
    }
}
