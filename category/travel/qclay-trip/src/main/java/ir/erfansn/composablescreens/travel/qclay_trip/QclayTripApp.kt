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

package ir.erfansn.composablescreens.travel.qclay_trip

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ir.erfansn.composablescreens.auto_nav_graph_wiring.core.AutoNavGraphWiring
import ir.erfansn.composablescreens.travel.qclay_trip.details.DetailsRoute
import ir.erfansn.composablescreens.travel.qclay_trip.home.HomeRoute
import ir.erfansn.composablescreens.travel.qclay_trip.ui.theme.QclayTripTheme
import kotlinx.serialization.Serializable

@Serializable
data object QclayTripRoute

@Serializable
internal data object HomeRoute

@Serializable
internal data object DetailsRoute

@AutoNavGraphWiring(
  name = "Qclay Trip",
  route = QclayTripRoute::class,
)
@Composable
fun QclayTripApp() {
  QclayTripTheme {
    val navController = rememberNavController()
    NavHost(navController, startDestination = HomeRoute) {
      composable<HomeRoute> {
        HomeRoute(
          onTravelGroupItemClick = {
            navController.navigate(DetailsRoute)
          },
        )
      }
      composable<DetailsRoute> {
        DetailsRoute()
      }
    }
  }
}
