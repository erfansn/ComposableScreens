package ir.erfansn.composablescreens.food.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import ir.erfansn.composablescreens.common.PortraitOrientationLockerEffect

val foodColor = FoodColor(
    primary = primary,
    onPrimary = onPrimary,
    secondary = secondary,
    onSecondary = onSecondary,
    tertiary = tertiary,
    onTertiary = onTertiary,
    background = background,
    onBackground = onBackground
)

@Immutable
data class FoodColor(
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val onSecondary: Color,
    val tertiary: Color,
    val onTertiary: Color,
    val background: Color,
    val onBackground: Color
)

val LocalFoodColor = staticCompositionLocalOf {
    FoodColor(
        primary = Color.Unspecified,
        onPrimary = Color.Unspecified,
        secondary = Color.Unspecified,
        onSecondary = Color.Unspecified,
        tertiary = Color.Unspecified,
        onTertiary = Color.Unspecified,
        background = Color.Unspecified,
        onBackground = Color.Unspecified
    )
}

@Composable
fun FoodTheme(
    content: @Composable () -> Unit
) {
    PortraitOrientationLockerEffect()

    CompositionLocalProvider(
        LocalFoodColor provides foodColor,
        LocalLayoutDirection provides LayoutDirection.Ltr
    ) {
        MaterialTheme(
            typography = AppTypography,
            content = content
        )
    }
}

object FoodTheme {
    val colors: FoodColor
        @Composable
        get() = LocalFoodColor.current

    val typography: Typography
        @Composable
        get() = MaterialTheme.typography

    // TODO: 6 Think about it, custom or not?
    val shapes: Shapes
        @Composable
        get() = MaterialTheme.shapes
}
