package ir.erfansn.composablescreens.travel.qclay_trip.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import ir.erfansn.composablescreens.common.BarStyle
import ir.erfansn.composablescreens.common.ProvideSystemBarStyle

private val LightColorPalette = lightColors(
    primary = PastelOrange,
    primaryVariant = CarminePink,
    secondary = BrightGray,
    secondaryVariant = VampireBlack,
    background = Cultured,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

@Composable
internal fun QclayTripTheme(content: @Composable () -> Unit) {
    ProvideSystemBarStyle(BarStyle.Light) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            MaterialTheme(
                colors = LightColorPalette,
                typography = Typography,
                shapes = Shapes,
                content = content
            )
        }
    }
}
