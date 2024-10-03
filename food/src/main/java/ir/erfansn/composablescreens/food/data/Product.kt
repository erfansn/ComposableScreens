package ir.erfansn.composablescreens.food.data

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import ir.erfansn.composablescreens.food.ui.util.Cent

@Immutable
internal data class Product(
    val id: Int,
    val title: String,
    @DrawableRes val imageId: Int,
    val backgroundColor: Color,
    val priceInCent: Cent,
    val ingredients: List<String>,
    val description: String
)
