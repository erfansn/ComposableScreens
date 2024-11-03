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

package ir.erfansn.composablescreens.food.kristina_cookie.ui.util

import android.icu.text.NumberFormat
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import ir.erfansn.composablescreens.food.kristina_cookie.ui.KristinaCookieTheme
import java.util.Locale

internal typealias Cent = Int

@Composable
internal fun priceByQuantityText(
  priceInCent: Cent,
  style: TextStyle = KristinaCookieTheme.typography.titleMedium,
): AnnotatedString =
  buildAnnotatedString {
    append(priceInCent.convertToDollars())
    withStyle(
      style =
        style.toSpanStyle().copy(
          color = Color(0xFF5A5350),
        ),
    ) {
      append("/1qty")
    }
  }

internal fun Cent.convertToDollars(): String {
  val numberFormat = NumberFormat.getCurrencyInstance(Locale.US)
  return numberFormat.format(this / 100.0)
}
