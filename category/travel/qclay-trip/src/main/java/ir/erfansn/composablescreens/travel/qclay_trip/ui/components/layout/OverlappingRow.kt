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

package ir.erfansn.composablescreens.travel.qclay_trip.ui.components.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
internal fun OverlappingRow(
  modifier: Modifier = Modifier,
  overlapWidth: Dp = 10.dp,
  content: @Composable () -> Unit,
) {
  Layout(
    modifier = modifier,
    content = content,
  ) { measurables, constraints ->
    val placeables =
      measurables.map { measurable ->
        measurable.measure(constraints)
      }

    val itemWidth = { it: Placeable -> it.width - overlapWidth.roundToPx() }

    val width = placeables.sumOf(itemWidth) + overlapWidth.roundToPx()
    val maxHeight = placeables.maxOf { it.height }
    layout(width, maxHeight) {
      var xPosition = 0

      placeables.forEachIndexed { index, placeable ->
        placeable.placeRelative(
          x = xPosition,
          y = 0,
          zIndex = index.toFloat(),
        )

        xPosition += itemWidth(placeable)
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun OverlappingRowPreview() {
  OverlappingRow(
    overlapWidth = 8.dp,
  ) {
    repeat(3) {
      Box(
        modifier =
          Modifier
            .size(48.dp)
            .background(
              color = Color.White,
              shape = CircleShape,
            ).border(
              width = 2.dp,
              color = Color.Black,
              shape = CircleShape,
            ),
      )
    }
  }
}
