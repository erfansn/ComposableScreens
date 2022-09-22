@file:OptIn(ExperimentalMaterial3Api::class)

package ir.erfansn.composablescreens.travel.ui.components

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Preview
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.erfansn.composablescreens.travel.ui.components.modifier.shadow
import ir.erfansn.composablescreens.travel.ui.theme.TravelTheme

@Composable
fun TravelButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    shadowColor: Color? = null,
    shape: Shape = MaterialTheme.shapes.small,
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
            ProvideTextStyle(value = MaterialTheme.typography.labelLarge) {
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
fun TravelIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    @DrawableRes iconId: Int,
    iconTint: Color = LocalContentColor.current,
    contentDescription: String,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    shadowColor: Color? = null,
    shape: Shape = MaterialTheme.shapes.small,
) {
    TravelButton(
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

@Preview(
    name = "Light theme",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
)
@Preview(
    name = "Dark theme",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
annotation class ThemePreviews

@ThemePreviews
@Composable
fun TravelButtonText() {
    TravelTheme {
        TravelButton(
            modifier = Modifier.padding(16.dp),
            onClick = { /*TODO*/ }
        ) {
            Text("Preview")
        }
    }
}

@ThemePreviews
@Composable
fun TravelButtonIconShadow() {
    TravelTheme {
        TravelButton(
            modifier = Modifier.padding(16.dp),
            shadowColor = MaterialTheme.colorScheme.primary,
            onClick = { /*TODO*/ },
        ) {
            Icon(
                imageVector = Icons.Rounded.Preview,
                contentDescription = null
            )
        }
    }
}