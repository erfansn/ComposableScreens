@file:OptIn(ExperimentalMaterialApi::class)

package ir.erfansn.composablescreens.travel.qclay_trip.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.erfansn.composablescreens.travel.qclay_trip.ui.components.modifier.shadow
import ir.erfansn.composablescreens.travel.qclay_trip.ui.theme.QclayTripTheme

@Composable
internal fun QclayTripButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    containerColor: Color = MaterialTheme.colors.primary,
    shadowColor: Color? = null,
    shape: Shape = RoundedCornerShape(36),
    content: @Composable BoxScope.() -> Unit,
) {
    Surface(
        modifier = modifier
            .shadow(
                shape = shape,
                radius = 24.dp,
                color = shadowColor?.copy(alpha = 0.2f) ?: Color.Transparent,
                dx = 10.dp,
                dy = 10.dp
            ),
        onClick = onClick,
        shape = shape,
        color = containerColor,
    ) {
        CompositionLocalProvider(LocalContentColor provides contentColorFor(containerColor)) {
            ProvideTextStyle(value = MaterialTheme.typography.button) {
                Box(
                    Modifier
                        .defaultMinSize(
                            minWidth = TravelButtonSize,
                            minHeight = TravelButtonSize
                        ),
                    contentAlignment = Alignment.Center,
                    content = content
                )
            }
        }
    }
}

@Composable
internal fun QclayTripIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    @DrawableRes iconId: Int,
    iconTint: Color = LocalContentColor.current,
    contentDescription: String,
    containerColor: Color = MaterialTheme.colors.primary,
    shadowColor: Color? = null,
    shape: Shape = RoundedCornerShape(36),
) {
    QclayTripButton(
        modifier = modifier,
        onClick = onClick,
        containerColor = containerColor,
        shadowColor = shadowColor,
        shape = shape
    ) {
        Icon(
            tint = iconTint,
            painter = painterResource(id = iconId),
            contentDescription = contentDescription
        )
    }
}

private val TravelButtonSize = 56.dp

@Preview
@Composable
fun QclayTripButtonText() {
    QclayTripTheme {
        QclayTripButton(
            modifier = Modifier.padding(16.dp),
            onClick = { /*TODO*/ }
        ) {
            Text("Preview")
        }
    }
}

@Preview
@Composable
fun QclayTripButtonIconShadow() {
    QclayTripTheme {
        QclayTripButton(
            modifier = Modifier.padding(16.dp),
            shadowColor = MaterialTheme.colors.primary,
            onClick = { /*TODO*/ },
        ) {
            Icon(
                imageVector = Icons.Rounded.Preview,
                contentDescription = null
            )
        }
    }
}
