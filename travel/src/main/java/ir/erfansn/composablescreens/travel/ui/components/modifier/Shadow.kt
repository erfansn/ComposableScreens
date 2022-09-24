package ir.erfansn.composablescreens.travel.ui.components.modifier

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.boundingRect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Stable
fun Modifier.shadow(
    shape: Shape = RectangleShape,
    color: Color = Color(0xCCCCCCCC),
    radius: Dp = 0.dp,
    dx: Dp = 0.dp,
    dy: Dp = 0.dp,
) = drawWithCache {
    val paint = Paint().asFrameworkPaint().apply {
        this.color = Color.Transparent.toArgb()
        setShadowLayer(
            radius.toPx(),
            dx.toPx(),
            dy.toPx(),
            color.toArgb()
        )
    }
    onDrawBehind {
        drawIntoCanvas {
            when (val outline = shape.createOutline(size, layoutDirection, this)) {
                is Outline.Rectangle -> {
                    it.nativeCanvas.drawRect(
                        outline.rect.toAndroidRectF(),
                        paint
                    )
                }
                is Outline.Rounded -> {
                    val roundRect = outline.roundRect
                    val topLeftCorner = roundRect.topLeftCornerRadius
                    it.nativeCanvas.drawRoundRect(
                        roundRect.boundingRect.toAndroidRectF(),
                        topLeftCorner.x,
                        topLeftCorner.y,
                        paint
                    )
                }
                is Outline.Generic -> {
                    it.nativeCanvas.drawPath(
                        outline.path.asAndroidPath(),
                        paint
                    )
                }
            }
        }
    }
}