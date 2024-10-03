package ir.erfansn.composablescreens.food.ui.modifier

import androidx.compose.animation.animateColorAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import ir.erfansn.composablescreens.food.ui.FoodTheme

@Composable
internal fun Modifier.overlappedBackgroundColor(isOverlapped: Boolean): Modifier {
    val backgroundColor by animateColorAsState(
        label = "background_color",
        targetValue = if (isOverlapped) FoodTheme.colors.tertiary else FoodTheme.colors.background
    )
    return drawBehind {
        drawRect(backgroundColor)
    }
}
