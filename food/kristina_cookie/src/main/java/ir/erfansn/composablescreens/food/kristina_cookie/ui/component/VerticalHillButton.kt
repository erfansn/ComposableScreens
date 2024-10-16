package ir.erfansn.composablescreens.food.kristina_cookie.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import ir.erfansn.composablescreens.food.kristina_cookie.ui.KristinaCookieTheme

@Composable
internal fun VerticalHillButton(
    onClick: () -> Unit,
    title: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .intoVertical()
            .sizeIn(116.dp, 56.dp)
            .clip(HillShape)
            .clickable { onClick() }
            .background(KristinaCookieTheme.colors.primary)
            .padding(bottom = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowUp,
            contentDescription = null,
            modifier = Modifier
                .size(16.dp)
                .offset(y = 5.dp),
            tint = KristinaCookieTheme.colors.onPrimary
        )
        Text(
            text = title,
            style = KristinaCookieTheme.typography.titleMedium,
            color = KristinaCookieTheme.colors.onPrimary
        )
    }
}

private fun Modifier.intoVertical() = layout { measurable, constraints ->
    val placeable = measurable.measure(
        Constraints(
            minHeight = constraints.minWidth,
            maxHeight = constraints.maxWidth,
            minWidth = constraints.minHeight,
            maxWidth = constraints.maxHeight
        )
    )
    layout(placeable.height, placeable.width) {
        placeable.placeWithLayer(
            x = -(placeable.width / 2 - placeable.height / 2),
            y = -(placeable.height / 2 - placeable.width / 2),
            layerBlock = { rotationZ = -90f }
        )
    }
}

private val HillShape = object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val (width, height) = size

        return Outline.Generic(
            Path().apply {
                moveTo(
                    x = width * 0f,
                    y = height * 1f
                )
                lineTo(
                    x = width * 0.125f,
                    y = height * 0.425f
                )
                quadraticTo(
                    x1 = width * 0.2f,
                    y1 = height * 0f,
                    x2 = width * 0.4f,
                    y2 = height * 0f,
                )
                lineTo(
                    x = width - (width * 0.4f),
                    y = height * 0f
                )
                quadraticTo(
                    x1 = width - (width * 0.2f),
                    y1 = height * 0f,
                    x2 = width - (width * 0.125f),
                    y2 = height * 0.425f,
                )
                lineTo(
                    x = width * 1f,
                    y = height * 1f
                )
            },
        )
    }
}
