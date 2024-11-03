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

package ir.erfansn.composablescreens.travel.qclay_trip.ui.components.modifier

import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.boundingRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toAndroidRectF
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Stable
internal fun Modifier.shadow(
  shape: Shape = RectangleShape,
  color: Color = Color(0xCCCCCCCC),
  radius: Dp = 0.dp,
  dx: Dp = 0.dp,
  dy: Dp = 0.dp,
) = drawWithCache {
  val paint =
    Paint().asFrameworkPaint().apply {
      this.color = Color.Transparent.toArgb()
      setShadowLayer(
        radius.toPx(),
        dx.toPx(),
        dy.toPx(),
        color.toArgb(),
      )
    }
  onDrawBehind {
    drawIntoCanvas {
      when (val outline = shape.createOutline(size, layoutDirection, this)) {
        is Outline.Rectangle -> {
          it.nativeCanvas.drawRect(
            outline.rect.toAndroidRectF(),
            paint,
          )
        }
        is Outline.Rounded -> {
          val roundRect = outline.roundRect
          val topLeftCorner = roundRect.topLeftCornerRadius
          it.nativeCanvas.drawRoundRect(
            roundRect.boundingRect.toAndroidRectF(),
            topLeftCorner.x,
            topLeftCorner.y,
            paint,
          )
        }
        is Outline.Generic -> {
          it.nativeCanvas.drawPath(
            outline.path.asAndroidPath(),
            paint,
          )
        }
      }
    }
  }
}
