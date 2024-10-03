package ir.erfansn.composablescreens.common

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier

val LocalNavAnimatedContentScope = staticCompositionLocalOf<AnimatedContentScope?> { null }

val CompositionLocal<AnimatedContentScope?>.requiredCurrent
    @Composable
    get() = current ?: error("LocalNavAnimatedContentScope not provided from navigation route")

class Modifier_AnimatedVisibilityScope(modifier: Modifier, animatedContentScope: AnimatedContentScope) : Modifier by modifier, AnimatedVisibilityScope by animatedContentScope

@Composable
fun Modifier.withSafeNavAnimatedContentScope(block: @Composable Modifier_AnimatedVisibilityScope.() -> Modifier): Modifier {
    return then(
        with(LocalNavAnimatedContentScope.current) {
            if (this != null) {
                Modifier_AnimatedVisibilityScope(Modifier, this).block()
            } else {
                Modifier
            }
        }
    )
}
