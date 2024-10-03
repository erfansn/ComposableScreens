@file:OptIn(ExperimentalSharedTransitionApi::class)

package ir.erfansn.composablescreens.common

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier

val LocalSharedTransitionScope = staticCompositionLocalOf<SharedTransitionScope?> { null }

class Modifier_SharedTransitionScope(modifier: Modifier, sharedTransitionScope: SharedTransitionScope) : Modifier by modifier, SharedTransitionScope by sharedTransitionScope

@Composable
fun Modifier.withSafeSharedTransitionScope(block: @Composable Modifier_SharedTransitionScope.() -> Modifier): Modifier {
    return then(
        with(LocalSharedTransitionScope.current) {
            if (this != null) {
                Modifier_SharedTransitionScope(Modifier, this).block()
            } else {
                Modifier
            }
        }
    )
}
