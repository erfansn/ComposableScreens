package ir.erfansn.composablescreens.food.feature.home

import androidx.annotation.DrawableRes
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import ir.erfansn.composablescreens.common.withSafeSharedElementAnimationScopes
import ir.erfansn.composablescreens.food.R
import ir.erfansn.composablescreens.food.data.Product
import ir.erfansn.composablescreens.food.ui.FoodTheme
import ir.erfansn.composablescreens.food.ui.util.priceByQuantityText
import ir.erfansn.composablescreens.food.ui.util.scaleEffectValue

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun VitrineItemCard(
    vitrineItem: VitrineItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val scaleEffectValue by interactionSource.scaleEffectValue()
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(43.dp),
        colors = CardDefaults.cardColors(
            containerColor = vitrineItem.backgroundColor,
            contentColor = FoodTheme.colors.onBackground
        ),
        modifier = modifier
            .graphicsLayer {
                scaleX = scaleEffectValue
                scaleY = scaleEffectValue
            },
        interactionSource = interactionSource
    ) {
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier.fillMaxSize()
        ) {
            val hazeState = remember { HazeState() }
            Column(modifier = Modifier.haze(hazeState)) {
                Text(
                    text = vitrineItem.title,
                    style = FoodTheme.typography.displaySmall,
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .padding(top = 26.dp)
                        .weight(2f)
                        .withSafeSharedElementAnimationScopes {
                            sharedElement(
                                state = rememberSharedContentState(key = "title_${vitrineItem.id}"),
                                animatedVisibilityScope = this,
                                zIndexInOverlay = 3f,
                            )
                        },
                    fontWeight = FontWeight.Bold,
                )
                Image(
                    painter = painterResource(id = vitrineItem.imageId),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.TopCenter,
                    modifier = Modifier
                        .fillMaxWidth()
                        .scale(1.1f)
                        .weight(3f),
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .height(86.dp)
                    .clip(RoundedCornerShape(43.dp))
                    .border(2.dp, color = Color(0xFFE8D0B8), shape = RoundedCornerShape(43.dp))
                    .hazeChild(
                        state = hazeState,
                        style = HazeDefaults.style(
                            backgroundColor = Color.White.copy(alpha = 0.35f),
                            blurRadius = 10.dp,
                        )
                    )
                    .padding(4.dp)
            ) {
                Text(
                    text = priceByQuantityText(priceInCent = vitrineItem.priceInCent),
                    modifier = Modifier.padding(start = 32.dp),
                    style = FoodTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                )
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(color = FoodTheme.colors.secondary)
                        .size(116.dp, 81.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.AddShoppingCart,
                        contentDescription = "Shopping Cart",
                        tint = FoodTheme.colors.onSecondary
                    )
                }
            }
        }
    }
}

typealias Cent = Int

data class VitrineItem(
    val id: Int,
    val title: String,
    @DrawableRes val imageId: Int,
    val backgroundColor: Color,
    val priceInCent: Cent,
)

fun Product.toVitrineItem() = VitrineItem(
    id = id,
    title = title,
    imageId = imageId,
    backgroundColor = backgroundColor,
    priceInCent = priceInCent
)

val sampleVitrineItems = listOf(
    VitrineItem(
        id = 0,
        title = "Peanut Butter Chocolate Chip Cookie",
        imageId = R.drawable.peanut_butter_chocolate_chip_cookie,
        backgroundColor = Color(0xFFFCE798),
        priceInCent = 600
    ),
    VitrineItem(
        id = 1,
        title = "Sea Salt Chocolate Chip Cookie",
        imageId = R.drawable.sea_salt_chocolate_chip_cookie,
        backgroundColor = Color(0xFFE4F9CD),
        priceInCent = 800
    ),
    VitrineItem(
        id = 2,
        title = "Triple Chocolate Chip Cookie",
        imageId = R.drawable.triple_chocolate_cookie,
        backgroundColor = Color(0xFFEBE0FE),
        priceInCent = 399
    ),
    VitrineItem(
        id = 3,
        title = "White Chocolate Macadamia Nut Cookie",
        imageId = R.drawable.white_chocolate_macadamia_nut_cookie,
        backgroundColor = Color(0xFFDDEAFE),
        priceInCent = 440
    ),
    VitrineItem(
        id = 4,
        title = "Snickerchurro Cookie",
        imageId = R.drawable.snickerchurro_cookie,
        backgroundColor = Color(0xFFFEEAE3),
        priceInCent = 550
    ),
    VitrineItem(
        id = 5,
        title = "S'mores Cookie",
        imageId = R.drawable.s_mores_cookie,
        backgroundColor = Color(0xFFFCE798),
        priceInCent = 550
    ),
    VitrineItem(
        id = 6,
        title = "Chocolate Peanut Butter Stuffed Cookie",
        imageId = R.drawable.chocolate_peanut_butter_stuffed_cookie,
        backgroundColor = Color(0xFFE4F9CD),
        priceInCent = 550
    ),
    VitrineItem(
        id = 7,
        title = "Oatmeal Raisin Cookie",
        imageId = R.drawable.oatmeal_raisin_cookie,
        backgroundColor = Color(0xFFDDEAFE),
        priceInCent = 590
    ),
    VitrineItem(
        id = 8,
        title = "Caramel Sea Salt Cookie",
        imageId = R.drawable.caramel_sea_salt_cookie,
        backgroundColor = Color(0xFFFEEAE3),
        priceInCent = 550
    )
)

@Preview
@Composable
private fun VitrineCardPreview() {
    FoodTheme {
        VitrineItemCard(
            vitrineItem = sampleVitrineItems.first(),
            onClick = { },
            modifier = Modifier.aspectRatio(1f)
        )
    }
}
