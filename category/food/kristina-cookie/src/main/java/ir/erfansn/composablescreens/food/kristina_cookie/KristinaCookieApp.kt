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

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ir.erfansn.composablescreens.auto_nav_graph_wiring.core.AutoNavGraphWiring
import ir.erfansn.composablescreens.common.navAnimatedComposable
import ir.erfansn.composablescreens.food.kristina_cookie.feature.cart.CartRoute
import ir.erfansn.composablescreens.food.kristina_cookie.feature.cart.CartViewModel
import ir.erfansn.composablescreens.food.kristina_cookie.feature.home.HomeRoute
import ir.erfansn.composablescreens.food.kristina_cookie.feature.home.HomeViewModel
import ir.erfansn.composablescreens.food.kristina_cookie.feature.product.ProductRoute
import ir.erfansn.composablescreens.food.kristina_cookie.feature.product.ProductViewModel
import ir.erfansn.composablescreens.food.kristina_cookie.ui.KristinaCookieTheme
import ir.erfansn.composablescreens.food.kristina_cookie.ui.util.sharedElementAnimSpec
import kotlinx.serialization.Serializable

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
)
@Composable
fun KristinaCookieApp() {
  KristinaCookieTheme {
    val navController = rememberNavController()
    NavHost(navController, startDestination = HomeRoute) {
      navAnimatedComposable<HomeRoute>(
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
      navAnimatedComposable<ProductRoute>(
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
      navAnimatedComposable<CartRoute>(
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
  }
}
