package ir.erfansn.composablescreens.common

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier

val LocalNavAnimatedContentScope = staticCompositionLocalOf<AnimatedContentScope?> { null }

val CompositionLocal<AnimatedContentScope?>.requiredCurrent
    @Composable
    get() = current ?: error("LocalNavAnimatedContentScope not provided from navigation route")

@Composable
fun Modifier.withSafeNavAnimatedContentScope(block: @Composable AnimatedContentScope.() -> Modifier): Modifier {
    return then(
        with(LocalNavAnimatedContentScope.current) {
            if (this != null) {
                block()
            } else {
                Modifier
            }
        }
    )
}
