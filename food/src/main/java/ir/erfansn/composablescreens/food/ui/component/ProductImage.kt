package ir.erfansn.composablescreens.food.ui.component

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ir.erfansn.composablescreens.common.withSafeNavAnimatedContentScope
import ir.erfansn.composablescreens.common.withSafeSharedTransitionScope
import ir.erfansn.composablescreens.food.R
import ir.erfansn.composablescreens.food.ui.FoodTheme
import ir.erfansn.composablescreens.food.ui.util.sharedElementAnimSpec

data class ProductBackground(
    val color: Color,
    val cornerSize: Dp,
)

object ProductImageDefault {
    val productBackground @Composable get() = ProductBackground(
        color = Color(0xFFE4F9CD),
        cornerSize = FoodTheme.cornerSize.large,
    )
}

@Composable
fun ProductImage(
    image: Painter,
    modifier: Modifier = Modifier,
    background: ProductBackground = ProductImageDefault.productBackground
) {
    Box(
        modifier = modifier
            .background(
                color = background.color,
                shape = RoundedCornerShape(background.cornerSize)
            )
    ) {
        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier
                .withSafeSharedTransitionScope {
                    renderInSharedTransitionScopeOverlay(zIndexInOverlay = 1f)
                }
                .align(Alignment.Center)
                .scale(1.1f)
                .aspectRatio(1f)
                .withSafeNavAnimatedContentScope {
                    animateEnterExit(
                        enter = fadeIn(animationSpec = sharedElementAnimSpec()),
                        exit = fadeOut(animationSpec = sharedElementAnimSpec())
                    )
                }
        )
    }
}

@Preview
@Composable
private fun ProductImagePreview() {
    FoodTheme {
        ProductImage(
            image = painterResource(id = R.drawable.caramel_sea_salt_cookie),
            modifier = Modifier.padding(8.dp)
        )
    }
}
