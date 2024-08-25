package ir.erfansn.composablescreens.common

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier

@OptIn(ExperimentalSharedTransitionApi::class)
val LocalSharedTransitionScope = staticCompositionLocalOf<SharedTransitionScope?> { null }

@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
fun Modifier.withSafeSharedTransitionScope(block: @Composable SharedTransitionScope.() -> Modifier): Modifier {
    return then(
        with(LocalSharedTransitionScope.current) {
            if (this != null) {
                block()
            } else {
                Modifier
            }
        }
    )
}
