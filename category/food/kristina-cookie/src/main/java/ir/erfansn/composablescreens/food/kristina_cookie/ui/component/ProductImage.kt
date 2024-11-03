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

package ir.erfansn.composablescreens.food.kristina_cookie.ui.component

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ir.erfansn.composablescreens.common.withSafeNavAnimatedContentScope
import ir.erfansn.composablescreens.common.withSafeSharedTransitionScope
import ir.erfansn.composablescreens.food.kristina_cookie.R
import ir.erfansn.composablescreens.food.kristina_cookie.ui.KristinaCookieTheme
import ir.erfansn.composablescreens.food.kristina_cookie.ui.util.sharedElementAnimSpec

internal data class ProductBackground(
  val color: Color,
  val cornerSize: Dp,
)

internal object ProductImageDefault {
  val productBackground @Composable get() =
    ProductBackground(
      color = Color(0xFFE4F9CD),
      cornerSize = KristinaCookieTheme.cornerSize.large,
    )
}

@Composable
internal fun ProductImage(
  image: Painter,
  modifier: Modifier = Modifier,
  background: ProductBackground = ProductImageDefault.productBackground,
) {
  Box(
    modifier =
      modifier
        .background(
          color = background.color,
          shape = RoundedCornerShape(background.cornerSize),
        ),
  ) {
    Image(
      painter = image,
      contentDescription = null,
      modifier =
        Modifier
          .withSafeSharedTransitionScope {
            renderInSharedTransitionScopeOverlay(zIndexInOverlay = 1f)
          }.align(Alignment.Center)
          .scale(1.1f)
          .aspectRatio(1f)
          .withSafeNavAnimatedContentScope {
            animateEnterExit(
              enter = fadeIn(animationSpec = sharedElementAnimSpec()),
              exit = fadeOut(animationSpec = sharedElementAnimSpec()),
            )
          },
    )
  }
}

@Preview
@Composable
private fun ProductImagePreview() {
  KristinaCookieTheme {
    ProductImage(
      image = painterResource(id = R.drawable.caramel_sea_salt_cookie),
      modifier = Modifier.padding(8.dp),
    )
  }
}
