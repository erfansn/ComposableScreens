package ir.erfansn.composablescreens.food.ui.util

import androidx.compose.animation.core.tween

fun <T> sharedElementAnimSpec() = tween<T>(durationMillis = 600)
