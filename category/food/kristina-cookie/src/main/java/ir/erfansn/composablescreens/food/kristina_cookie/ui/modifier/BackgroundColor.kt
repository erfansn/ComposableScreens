package ir.erfansn.composablescreens.food.kristina_cookie.ui.modifier

import androidx.compose.animation.animateColorAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import ir.erfansn.composablescreens.food.kristina_cookie.ui.KristinaCookieTheme

@Composable
internal fun Modifier.overlappedBackgroundColor(isOverlapped: Boolean): Modifier {
    val backgroundColor by animateColorAsState(
        label = "background_color",
        targetValue = if (isOverlapped) KristinaCookieTheme.colors.tertiary else KristinaCookieTheme.colors.background
    )
    return drawBehind {
        drawRect(backgroundColor)
    }
}
