package ir.erfansn.composablescreens.food.feature.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import ir.erfansn.composablescreens.food.ui.FoodTheme
import ir.erfansn.composablescreens.food.ui.component.FoodFloatingScaffold
import ir.erfansn.composablescreens.food.ui.component.VerticalHillButton
import ir.erfansn.composablescreens.food.ui.modifier.overlappedBackgroundColor
import ir.erfansn.composablescreens.food.ui.util.Cent
import ir.erfansn.composablescreens.food.ui.util.convertToDollars

@Composable
fun CartScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToProduct: (id: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyListState()

    var paymentIsSuccessful by remember { mutableStateOf(false) }
    Box(
        contentAlignment = Alignment.Center
    ) {
        FoodFloatingScaffold(
            topBar = {
                val isOverlapped by remember {
                    derivedStateOf {
                        lazyListState.firstVisibleItemIndex > 0 || lazyListState.firstVisibleItemScrollOffset > 46
                    }
                }
                Box(
                    contentAlignment = Alignment.BottomStart,
                    modifier = Modifier.overlappedBackgroundColor(isOverlapped)
                ) {
                    CartTopBar()
                    VerticalHillButton(
                        onClick = { /*TODO*/ },
                        title = "Catalog",
                        modifier = Modifier
                            .offset(y = (-6).dp)
                            .rotate(180f)
                    )
                }
            },
            floatingBottomBar = {
                CartBottomBar(
                    totalPrice = 8900,
                    onPayClick = {
                        paymentIsSuccessful = true
                    },
                    contentPadding = it,
                )
            },
            modifier = modifier
                .fillMaxSize()
        ) {
            CartContent(
                cartProducts = sampleCartProducts,
                contentPadding = it,
                state = lazyListState
            )
        }
        if (paymentIsSuccessful) {
            PaymentSuccessfulPane(
                modifier = Modifier
            )
        }
    }
}

@Composable
private fun CartTopBar(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 66.dp, bottom = 36.dp)
            .padding(horizontal = 24.dp)
    ) {
        Text(
            "Cart",
            style = FoodTheme.typography.displaySmall,
            modifier = Modifier.align(Alignment.Center),
            fontWeight = FontWeight.SemiBold
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .clip(CircleShape)
                .background(FoodTheme.colors.primary)
                .size(36.dp)
        ) {
            Text(
                "3",
                style = FoodTheme.typography.titleLarge,
                color = FoodTheme.colors.onPrimary,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun CartContent(
    cartProducts: List<CartProduct>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    state: LazyListState = rememberLazyListState()
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxWidth(),
        contentPadding = contentPadding,
        state = state
    ) {
        items(cartProducts) {
            CartProductItem(cartProduct = it, modifier = Modifier.clickable {  })
        }
    }
}

@Composable
private fun CartBottomBar(
    totalPrice: Cent,
    onPayClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .pointerInput(Unit) {}
            .clip(CurvedShape)
            .background(FoodTheme.colors.primary)
            .padding(bottom = 8.dp, top = 24.dp)
            .padding(contentPadding)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(start = 48.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                "Total amount",
                style = FoodTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                totalPrice.convertToDollars(),
                style = FoodTheme.typography.displaySmall,
                fontWeight = FontWeight.SemiBold,
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(end = 8.dp)
                .clip(RoundedCornerShape(42.dp))
                .clickable { onPayClick() }
                .background(FoodTheme.colors.background)
                .padding(horizontal = 32.dp, vertical = 42.dp)
        ) {
            CompositionLocalProvider(LocalContentColor provides FoodTheme.colors.onBackground) {
                Text(
                    text = "Pay",
                    style = FoodTheme.typography.titleLarge,
                    modifier = Modifier
                        .offset(x = 6.dp)
                )
                repeat(3) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos,
                        contentDescription = null,
                        tint = LocalContentColor.current.copy(alpha = 1f / (3 - it)),
                        modifier = Modifier
                            .size(24.dp)
                            .offset(x = 6.dp * (3 - 1 - it))
                    )
                }
            }
        }
    }
}

private val CurvedShape = object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val (width, height) = size
        return Outline.Generic(
            Path().apply {
                moveTo(
                    x = width * 0f,
                    y = height * 1f
                )
                lineTo(
                    x = width * 0f,
                    y = height * 0.35f
                )
                quadraticBezierTo(
                    x1 = width * 0f,
                    y1 = height * 0.103f,
                    x2 = width * 0.08f,
                    y2 = height * 0.08f,
                )
                quadraticBezierTo(
                    x1 = width * 0.5f,
                    y1 = height * -0.07f,
                    x2 = width - (width * 0.08f),
                    y2 = height * 0.08f,
                )
                quadraticBezierTo(
                    x1 = width - (width * 0f),
                    y1 = height * 0.103f,
                    x2 = width - (width * 0f),
                    y2 = height * 0.35f,
                )
                lineTo(
                    x = width * 1f,
                    y = height * 1f
                )
            }
        )
    }
}

@Preview
@Composable
private fun CartScreenPreview() {
    FoodTheme {
        CartScreen(
            onNavigateToHome = { /*TODO*/ },
            onNavigateToProduct = { }
        )
    }
}
