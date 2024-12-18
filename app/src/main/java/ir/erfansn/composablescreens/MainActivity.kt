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

package ir.erfansn.composablescreens

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ir.erfansn.composablescreens.auto_nav_graph_wiring.autoAggregatedNavGraphRoutes
import ir.erfansn.composablescreens.common.BarStyle
import ir.erfansn.composablescreens.common.LocalSharedTransitionScope
import ir.erfansn.composablescreens.common.ProvideSystemBarStyleChanger
import ir.erfansn.composablescreens.ui.ComposableScreensList
import ir.erfansn.composablescreens.ui.theme.ComposableScreensTheme
import kotlinx.serialization.Serializable

@Serializable
data object ComposableScreensList

class MainActivity : ComponentActivity() {
  @OptIn(ExperimentalSharedTransitionApi::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    enableEdgeToEdge()
    super.onCreate(savedInstanceState)
    setContent {
      ProvideSystemBarStyleChanger(
        onStyleChange = {
          when (it) {
            BarStyle.Light -> {
              enableEdgeToEdge(
                style =
                  SystemBarStyle.light(
                    Color.TRANSPARENT,
                    Color.TRANSPARENT,
                  ),
              )
            }

            BarStyle.Dark -> {
              enableEdgeToEdge(
                style =
                  SystemBarStyle.dark(
                    Color.TRANSPARENT,
                  ),
              )
            }

            BarStyle.Auto -> {
              enableEdgeToEdge()
            }
          }
        },
      ) {
        SharedTransitionLayout {
          CompositionLocalProvider(LocalSharedTransitionScope provides this) {
            val navController = rememberNavController()
            NavHost(
              navController = navController,
              startDestination = ComposableScreensList,
            ) {
              composable<ComposableScreensList> {
                ComposableScreensTheme {
                  ComposableScreensList(
                    onRouteClick = navController::navigate,
                  )
                }
              }
              autoAggregatedNavGraphRoutes()
            }
          }
        }
      }
    }
  }

  private fun enableEdgeToEdge(
    style: SystemBarStyle =
      SystemBarStyle.auto(
        lightScrim = Color.TRANSPARENT,
        darkScrim = Color.TRANSPARENT,
      ),
  ) {
    enableEdgeToEdge(
      statusBarStyle = style,
      navigationBarStyle = style,
    )
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
      window.isNavigationBarContrastEnforced = false
    }
  }
}
