/*
 * Copyright 2024 Erfan Sn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import ir.erfansn.composablescreens.auto_nav_graph_wiring.core.AutoNavGraphWiring
import ir.erfansn.composablescreens.auto_nav_graph_wiring.core.ScreenOrientation
import ir.erfansn.composablescreens.common.LocalNavAnimatedContentScope
import ir.erfansn.composablescreens.food.kristina_cookie.feature.cart.CartRoute
import ir.erfansn.composablescreens.food.kristina_cookie.feature.cart.CartViewModel
import ir.erfansn.composablescreens.food.kristina_cookie.feature.home.HomeRoute
import ir.erfansn.composablescreens.food.kristina_cookie.feature.home.HomeViewModel
import ir.erfansn.composablescreens.food.kristina_cookie.feature.product.ProductRoute
import ir.erfansn.composablescreens.food.kristina_cookie.feature.product.ProductViewModel
import ir.erfansn.composablescreens.food.kristina_cookie.ui.KristinaCookieTheme
import ir.erfansn.composablescreens.food.kristina_cookie.ui.util.sharedElementAnimSpec
import kotlinx.serialization.Serializable
import kotlin.reflect.KType

@Serializable
data object KristinaCookieRoute

@Serializable
internal data object HomeRoute

@Serializable
internal data class ProductRoute(
  val id: Int,
)

@Serializable
internal data object CartRoute

@AutoNavGraphWiring(
  name = "Kristina Cookie",
  route = KristinaCookieRoute::class,
  startDestination = HomeRoute::class,
  screenOrientation = ScreenOrientation.Portrait,
)
internal fun NavGraphBuilder.kristinaCookieNavGraph(navController: NavController) {
  kristinaCookieComposable<HomeRoute>(
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
        navController.navigate(ProductRoute(it))
      },
      onNavigateToCart = {
        navController.navigate(CartRoute)
      },
      viewModel = viewModel,
    )
  }
  kristinaCookieComposable<ProductRoute>(
    enterTransition = {
      fadeIn(animationSpec = sharedElementAnimSpec())
    },
    exitTransition = {
      fadeOut(animationSpec = sharedElementAnimSpec())
    },
  ) {
    val viewModel = viewModel<ProductViewModel>()
    ProductRoute(
      viewModel = viewModel,
      onNavigateToCart = {
        if (navController.previousBackStackEntry?.destination?.hasRoute<CartRoute>() == true) {
          navController.popBackStack()
        } else {
          navController.navigate(CartRoute) {
            popUpTo(HomeRoute)
          }
        }
      },
      onBackClick = { navController.popBackStack() },
      shouldRunNavAnimations = navController.previousBackStackEntry?.destination?.hasRoute<HomeRoute>() ?: false,
    )
  }
  kristinaCookieComposable<CartRoute>(
    enterTransition = {
      slideIntoContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Start,
        animationSpec = sharedElementAnimSpec(),
      )
    },
    exitTransition = {
      slideOutOfContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.End,
        animationSpec = sharedElementAnimSpec(),
      )
    },
  ) {
    val viewModel = viewModel<CartViewModel>()
    CartRoute(
      onNavigateToHome = {
        navController.popBackStack(route = HomeRoute, inclusive = false)
      },
      onNavigateToProduct = {
        navController.navigate(ProductRoute(it))
      },
      viewModel = viewModel,
    )
  }
}

private inline fun <reified T : Any> NavGraphBuilder.kristinaCookieComposable(
  typeMap: Map<KType, @JvmSuppressWildcards NavType<*>> = emptyMap(),
  deepLinks: List<NavDeepLink> = emptyList(),
  noinline enterTransition: (
    @JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?
  )? =
    null,
  noinline exitTransition: (
    @JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?
  )? =
    null,
  noinline popEnterTransition: (
    @JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?
  )? =
    enterTransition,
  noinline popExitTransition: (
    @JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?
  )? =
    exitTransition,
  noinline sizeTransform: (
    @JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> SizeTransform?
  )? =
    null,
  crossinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
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
    KristinaCookieTheme {
      CompositionLocalProvider(LocalNavAnimatedContentScope provides this) {
        this.content(it)
      }
    }
  }
}
