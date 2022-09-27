package ir.erfansn.composablescreens.travel.ui.components.layout

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
    space: Dp = 10.dp,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }

        val itemWidth = { it: Placeable ->  it.width - space.roundToPx() }

        val width = placeables.sumOf(itemWidth) + space.roundToPx()
        val maxHeight = placeables.maxOf { it.height }
        layout(width, maxHeight) {
            var xPosition = 0

            placeables.forEachIndexed { index, placeable ->
                placeable.placeRelative(
                    x = xPosition,
                    y = 0,
                    zIndex = index.toFloat()
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
        space = 8.dp
    ) {
        repeat(3) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = Color.White,
                        shape = CircleShape
                    )
                    .border(
                        width = 2.dp,
                        color = Color.Black,
                        shape = CircleShape
                    )
            )
        }
    }
}
