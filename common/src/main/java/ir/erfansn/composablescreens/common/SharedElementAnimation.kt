@file:OptIn(ExperimentalSharedTransitionApi::class)

package ir.erfansn.composablescreens.common

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

class Modifier_NavAnimatedScope_SharedTransitionScope(
    modifier: Modifier,
    navAnimatedScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope
) : Modifier by modifier,
    AnimatedVisibilityScope by navAnimatedScope,
    SharedTransitionScope by sharedTransitionScope

@Composable
fun Modifier.withSafeSharedElementAnimationScopes(block: @Composable Modifier_NavAnimatedScope_SharedTransitionScope.() -> Modifier): Modifier {
    return withSafeSharedTransitionScope {
        withSafeNavAnimatedContentScope {
            Modifier_NavAnimatedScope_SharedTransitionScope(
                Modifier,
                this@withSafeNavAnimatedContentScope,
                this@withSafeSharedTransitionScope
            ).block()
        }
    }
}
