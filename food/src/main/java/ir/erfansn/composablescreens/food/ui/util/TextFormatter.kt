package ir.erfansn.composablescreens.food.ui.util

import android.icu.text.NumberFormat
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import ir.erfansn.composablescreens.food.ui.FoodTheme
import java.util.Locale

typealias Cent = Int

@Composable
fun priceByQuantityText(priceInCent: Cent, style: TextStyle = FoodTheme.typography.titleMedium): AnnotatedString {
    return buildAnnotatedString {
        append(priceInCent.convertToDollars())
        withStyle(
            style = style.toSpanStyle().copy(
                color = Color(0xFF5A5350)
            )
        ) {
            append("/1qty")
        }
    }
}

fun Cent.convertToDollars(): String {
    val numberFormat = NumberFormat.getCurrencyInstance(Locale.US)
    return numberFormat.format(this / 100.0)
}
