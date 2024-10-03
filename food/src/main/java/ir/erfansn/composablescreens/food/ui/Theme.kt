package ir.erfansn.composablescreens.food.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import ir.erfansn.composablescreens.common.BarStyle
import ir.erfansn.composablescreens.common.ProvideSystemBarStyle

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

@Immutable
data class FoodCornerSize(
    val large: Dp
)

val LocalFoodCornerSize = staticCompositionLocalOf {
    FoodCornerSize(
        large = Dp.Unspecified
    )
}

@Composable
fun FoodTheme(
    content: @Composable () -> Unit
) {
    ProvideSystemBarStyle(BarStyle.Light) {
        CompositionLocalProvider(
            LocalFoodColor provides foodColor,
            LocalLayoutDirection provides LayoutDirection.Ltr,
            LocalFoodCornerSize provides foodCornerSize
        ) {
            MaterialTheme(
                typography = AppTypography,
                content = content
            )
        }
    }
}

object FoodTheme {
    val colors: FoodColor
        @Composable
        get() = LocalFoodColor.current

    val typography: Typography
        @Composable
        get() = MaterialTheme.typography

    val cornerSize: FoodCornerSize
        @Composable
        get() = LocalFoodCornerSize.current
}
