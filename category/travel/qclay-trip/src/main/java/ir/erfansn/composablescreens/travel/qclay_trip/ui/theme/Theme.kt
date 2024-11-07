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

package ir.erfansn.composablescreens.travel.qclay_trip.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import ir.erfansn.composablescreens.common.BarStyle
import ir.erfansn.composablescreens.common.PortraitOrientationLockerEffect
import ir.erfansn.composablescreens.common.ProvideSystemBarStyle

private val LightColorPalette =
  lightColors(
    primary = PastelOrange,
    primaryVariant = CarminePink,
    secondary = BrightGray,
    secondaryVariant = VampireBlack,
    background = Cultured,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
  )

@Composable
internal fun QclayTripTheme(content: @Composable () -> Unit) {
  PortraitOrientationLockerEffect()

  ProvideSystemBarStyle(BarStyle.Light) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
      MaterialTheme(
        colors = LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content,
      )
    }
  }
}
