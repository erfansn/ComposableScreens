package ir.erfansn.composablescreens.food.feature.cart

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.erfansn.composablescreens.food.data.Product
import ir.erfansn.composablescreens.food.feature.home.sampleVitrineItems
import ir.erfansn.composablescreens.food.ui.FoodTheme
import ir.erfansn.composablescreens.food.ui.component.ProductBackground
import ir.erfansn.composablescreens.food.ui.component.ProductImage
import ir.erfansn.composablescreens.food.ui.util.convertToDollars
import kotlin.random.Random

@Composable
fun CartProductItem(
    onClick: () -> Unit,
    cartProduct: CartProduct,
    modifier: Modifier = Modifier
) {
    ListItem(
        leadingContent = {
            ProductImage(
                background = ProductBackground(
                    color = cartProduct.backgroundColor,
                    cornerSize = 12.dp,
                ),
                image = painterResource(id = cartProduct.imageId),
            )
        },
        headlineContent = {
            Text(
                text = cartProduct.title,
                style = FoodTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        },
        supportingContent = {
            Text(
                text = "Qty: ${cartProduct.quantity}",
                style = FoodTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 8.dp),
                fontWeight = FontWeight.SemiBold
            )
        },
        trailingContent = {
            Text(
                text = cartProduct.totalPrice.convertToDollars(),
                style = FoodTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
            )
        },
        modifier = modifier
            .clickable { onClick() }
            .padding(end = 6.dp)
            .height(128.dp)
            .fillMaxWidth(),
        colors = ListItemDefaults.colors(
            containerColor = FoodTheme.colors.background,
            headlineColor = FoodTheme.colors.onBackground,
            trailingIconColor = FoodTheme.colors.onBackground,
            supportingColor = Color(0xFF5A5350)
        )
    )
}

data class CartProduct(
    val id: Int,
    val backgroundColor: Color,
    @DrawableRes val imageId: Int,
    val title: String,
    val quantity: Int,
    val price: Int,
) {
    val totalPrice: Int = quantity * price
}

fun Product.toCartProduct(quantity: Int) = CartProduct(
    id = id,
    backgroundColor = backgroundColor,
    imageId = imageId,
    title = title,
    quantity = quantity,
    price = priceInCent
)

val sampleCartProducts = sampleVitrineItems.map {
    CartProduct(
        id = it.id,
        backgroundColor = it.backgroundColor,
        imageId = it.imageId,
        title = it.title,
        quantity = Random.nextInt(1, 10),
        price = it.priceInCent
    )
}

@Preview
@Composable
private fun CartProductItemPreview() {
    FoodTheme {
        CartProductItem(
            cartProduct = sampleCartProducts.first(),
            onClick = { }
        )
    }
}