package ir.erfansn.composablescreens.food.ui.util

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue

@Composable
fun MutableInteractionSource.scaleEffectValue(): State<Float> {
    val isPressed by collectIsPressedAsState()
    return animateFloatAsState(
        targetValue = if (isPressed) 0.97f else 1f,
        label = "scale"
    )
}
