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

package ir.erfansn.composablescreens.food.kristina_cookie.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import ir.erfansn.composablescreens.common.BarStyle
import ir.erfansn.composablescreens.common.ProvideSystemBarStyle

@Immutable
internal data class KristinaCookieColor(
  val primary: Color,
  val onPrimary: Color,
  val secondary: Color,
  val onSecondary: Color,
  val tertiary: Color,
  val onTertiary: Color,
  val background: Color,
  val onBackground: Color,
)

private val kristinaCookieColor =
  KristinaCookieColor(
    primary = primary,
    onPrimary = onPrimary,
    secondary = secondary,
    onSecondary = onSecondary,
    tertiary = tertiary,
    onTertiary = onTertiary,
    background = background,
    onBackground = onBackground,
  )

private val LocalKristinaCookieColor =
  staticCompositionLocalOf {
    KristinaCookieColor(
      primary = Color.Unspecified,
      onPrimary = Color.Unspecified,
      secondary = Color.Unspecified,
      onSecondary = Color.Unspecified,
      tertiary = Color.Unspecified,
      onTertiary = Color.Unspecified,
      background = Color.Unspecified,
      onBackground = Color.Unspecified,
    )
  }

@Immutable
internal data class KristinaCookieCornerSize(
  val large: Dp,
)

private val LocalKristinaCookieCornerSize =
  staticCompositionLocalOf {
    KristinaCookieCornerSize(
      large = Dp.Unspecified,
    )
  }

@Composable
internal fun KristinaCookieTheme(content: @Composable () -> Unit) {
  ProvideSystemBarStyle(BarStyle.Light) {
    CompositionLocalProvider(
      LocalKristinaCookieColor provides kristinaCookieColor,
      LocalLayoutDirection provides LayoutDirection.Ltr,
      LocalKristinaCookieCornerSize provides kristinaCookieCornerSize,
    ) {
      MaterialTheme(
        typography = AppTypography,
        content = content,
      )
    }
  }
}

internal object KristinaCookieTheme {
  val colors: KristinaCookieColor
    @Composable
    get() = LocalKristinaCookieColor.current

  val typography: Typography
    @Composable
    get() = MaterialTheme.typography

  val cornerSize: KristinaCookieCornerSize
    @Composable
    get() = LocalKristinaCookieCornerSize.current
}
