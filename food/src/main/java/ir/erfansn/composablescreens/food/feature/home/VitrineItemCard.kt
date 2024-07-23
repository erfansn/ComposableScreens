package ir.erfansn.composablescreens.food.feature.home

import android.icu.text.NumberFormat
import androidx.annotation.DrawableRes
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import ir.erfansn.composablescreens.food.R
import ir.erfansn.composablescreens.food.ui.FoodTheme
import java.util.Locale

val vitrineItems = listOf(
    VitrineItem(
        title = "Sea Salt Chocolate Chip Cookie",
        imageId = R.drawable.peanut_butter_chocolate_chipcookie,
        backgroundColor = Color.Yellow,
        priceInCent = 148
    ),
    VitrineItem(
        title = "Sea Salt Chocolate Chip Cookie",
        imageId = R.drawable.peanut_butter_chocolate_chipcookie,
        backgroundColor = Color.Blue,
        priceInCent = 148
    ),
    VitrineItem(
        title = "Sea Salt Chocolate Chip Cookie",
        imageId = R.drawable.peanut_butter_chocolate_chipcookie,
        backgroundColor = Color.Cyan,
        priceInCent = 148
    ),
    VitrineItem(
        title = "Sea Salt Chocolate Chip Cookie",
        imageId = R.drawable.peanut_butter_chocolate_chipcookie,
        backgroundColor = Color.DarkGray,
        priceInCent = 148
    ),
    VitrineItem(
        title = "Sea Salt Chocolate Chip Cookie",
        imageId = R.drawable.peanut_butter_chocolate_chipcookie,
        backgroundColor = Color.Red,
        priceInCent = 148
    )
)

typealias Cent = Int

data class VitrineItem(
    val title: String,
    @DrawableRes val imageId: Int,
    val backgroundColor: Color,
    val priceInCent: Cent,
)

@Composable
fun VitrineItemCard(
    vitrineItem: VitrineItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        shape = MaterialTheme.shapes.large.copy(CornerSize(34.dp)),
        colors = CardDefaults.cardColors(containerColor = vitrineItem.backgroundColor),
        modifier = modifier,
    ) {
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier.aspectRatio(4/4.5f)
        ) {
            val hazeState = remember { HazeState() }
            Column(modifier = Modifier.haze(hazeState)) {
                Text(
                    text = vitrineItem.title,
                    style = MaterialTheme.typography.displaySmall,
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .padding(top = 18.dp)
                        .weight(2f),
                    fontWeight = FontWeight.Bold,
                    maxLines = 3
                )
                Image(
                    painter = painterResource(id = vitrineItem.imageId),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.TopCenter,
                    modifier = Modifier
                        .scale(1.2f)
                        .weight(3f),
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .clip(CircleShape)
                    .border(1.dp, color = Color.Gray, shape = CircleShape)
                    .hazeChild(
                        hazeState,
                        CircleShape,
                        HazeStyle(
                            tint = Color.White.copy(alpha = 0.3f),
                            blurRadius = 30.dp)
                    )
                    .padding(4.dp)
            ) {
                val productPriceByKg = buildAnnotatedString {
                    append(vitrineItem.priceInCent.convertToDollars())
                    withStyle(
                        style = MaterialTheme.typography.labelMedium.toSpanStyle()
                    ) {
                        append("/1kg")
                    }
                }
                Text(
                    text = productPriceByKg,
                    modifier = Modifier.padding(start = 16.dp),
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
        VitrineItemCard(
            vitrineItem = VitrineItem(
                title = "Peanut butter chocolate chip cookie",
                priceInCent = 689,
                imageId = R.drawable.peanut_butter_chocolate_chipcookie,
                backgroundColor = Color.Yellow
            ),
            onClick = { }
        )
    }
}
