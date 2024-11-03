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

package ir.erfansn.composablescreens.food.kristina_cookie.feature.cart

import androidx.annotation.FloatRange
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import ir.erfansn.composablescreens.common.BarStyle
import ir.erfansn.composablescreens.common.TemporarySystemBarStyleEffect
import ir.erfansn.composablescreens.food.kristina_cookie.ui.KristinaCookieTheme
import kotlinx.coroutines.launch
import kotlin.math.hypot
import kotlin.random.Random

@Composable
internal fun PaymentSuccessfulPane(modifier: Modifier = Modifier) {
  val alphaAnimatable = remember { Animatable(0f) }
  val scaleAnimatable = remember { Animatable(0.9f) }
  val circleRadiusAnimatable = remember { Animatable(0f) }
  LaunchedEffect(Unit) {
    val animationSpec = tween<Float>(durationMillis = 4000)
    launch {
      alphaAnimatable.animateTo(1f, animationSpec)
    }
    launch {
      scaleAnimatable.animateTo(1f, animationSpec)
    }
    launch {
      circleRadiusAnimatable.animateTo(1f, animationSpec)
    }
  }

  if (circleRadiusAnimatable.value >= 0.9f) {
    TemporarySystemBarStyleEffect(BarStyle.Dark)
  }

  Box(
    modifier =
      modifier
        .circularReveal(progress = circleRadiusAnimatable.value)
        .background(KristinaCookieTheme.colors.secondary),
  ) {
    MeteorShower(
      meteorCount = 16,
      speed = 600,
      maxDelayInMillis = 2500,
    ) {
      Icon(
        imageVector = Icons.Rounded.ArrowBackIosNew,
        contentDescription = null,
        modifier =
          Modifier
            .rotate(Random.nextFloat() * 360f)
            .zIndex(1f)
            .size(56.dp),
        tint = colors.random(),
      )
    }
    Column(
      verticalArrangement = Arrangement.SpaceEvenly,
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier.fillMaxSize(),
    ) {
      CompositionLocalProvider(
        LocalContentColor provides KristinaCookieTheme.colors.onSecondary,
      ) {
        Text(
          text = "You're\ngreat!",
          style = KristinaCookieTheme.typography.displayLarge,
          modifier =
            Modifier
              .zIndex(3f)
              .scale(1.3f)
              .graphicsLayer {
                alpha = alphaAnimatable.value
                scaleX = scaleAnimatable.value
                scaleY = scaleX
              },
          fontWeight = FontWeight.Bold,
        )
        Text(
          text = "The order will\narrive by 12:30",
          style = KristinaCookieTheme.typography.titleLarge,
          modifier =
            Modifier
              .zIndex(2f)
              .graphicsLayer {
                alpha = alphaAnimatable.value
              },
          fontWeight = FontWeight.SemiBold,
        )
      }
    }
  }
}

private fun Modifier.circularReveal(
  @FloatRange(
    from = 0.0,
    to = 1.0,
  ) progress: Float,
) = clip(CircularRevealShape(progress))

private class CircularRevealShape(
  @FloatRange(from = 0.0, to = 1.0) private val progress: Float,
) : Shape {
  override fun createOutline(
    size: Size,
    layoutDirection: LayoutDirection,
    density: Density,
  ): Outline =
    Outline.Generic(
      android.graphics
        .Path()
        .apply {
          addCircle(
            (size.width / 2f),
            (size.height / 2f),
            hypot(size.width / 2f, size.height / 2f) * progress,
            android.graphics.Path.Direction.CW,
          )
        }.asComposePath(),
    )
}

private val colors =
  listOf(
    Color(0xFFFCE798),
    Color(0xFFE4F9CD),
    Color(0xFFEBE0FE),
    Color(0xFFDDEAFE),
    Color(0xFFFEEAE3),
  )

@Preview
@Composable
private fun PaymentSuccessfulPanePreview() {
  KristinaCookieTheme {
    PaymentSuccessfulPane()
  }
}
