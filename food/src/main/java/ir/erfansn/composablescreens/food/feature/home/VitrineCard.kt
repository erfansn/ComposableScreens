package ir.erfansn.composablescreens.food.feature.home

import android.icu.text.NumberFormat
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.erfansn.composablescreens.food.R
import ir.erfansn.composablescreens.food.ui.FoodTheme
import java.util.Locale

typealias Cent = Int

data class Item(
    val title: String,
    val image: Painter,
    val backgroundColor: Color,
    val priceInCent: Cent,
)

@Composable
fun VitrineCard(
    item: Item,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onClick,
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = item.backgroundColor),
        modifier = modifier,
    ) {
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier.aspectRatio(4/5f)
        ) {
            Column {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                        .padding(top = 12.dp),
                    fontWeight = FontWeight.Bold
                )
                Image(
                    painter = item.image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.TopCenter,
                    modifier = Modifier.scale(1.2f)
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .clip(CircleShape)
                    .background(Color.LightGray)
                    .border(1.dp, color = Color.Gray, shape = CircleShape)
                    .padding(4.dp)
            ) {
                val productPriceByKg = buildAnnotatedString {
                    append(item.priceInCent.convertToDollars())
                    withStyle(
                        style = MaterialTheme.typography.labelMedium.toSpanStyle()
                    ) {
                        append("/1kg")
                    }
                }
                Text(
                    text = productPriceByKg,
                    modifier = Modifier.padding(start = 16.dp)
                )
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(color = MaterialTheme.colorScheme.onSurface)
                        .padding(
                            vertical = 16.dp,
                            horizontal = 28.dp
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Shopping Cart",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

private fun Int.convertToDollars(): String {
    val numberFormat = NumberFormat.getCurrencyInstance(Locale.US)
    return numberFormat.format(this / 100.0)
}

@Preview
@Composable
private fun VitrineCardPreview() {
    FoodTheme {
        VitrineCard(
            item = Item(
                title = "Peanut butter chocolate chip cookie",
                priceInCent = 689,
                image = painterResource(id = R.drawable.peanut_butter_chocolate_chipcookie),
                backgroundColor = Color.Yellow
            ),
            onClick = { },
            modifier = Modifier.width(240.dp)
        )
    }
}
