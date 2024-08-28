@file:OptIn(ExperimentalSharedTransitionApi::class)

package ir.erfansn.composablescreens.food.feature.home

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CardGiftcard
import androidx.compose.material.icons.rounded.DesktopWindows
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.PersonOutline
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Window
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.dropUnlessResumed
import ir.erfansn.composablescreens.common.withSafeSharedTransitionScope
import ir.erfansn.composablescreens.food.LocalNavAnimatedContentScope
import ir.erfansn.composablescreens.food.requiredCurrent
import ir.erfansn.composablescreens.food.ui.FoodTheme
import ir.erfansn.composablescreens.food.ui.component.FoodScaffold
import ir.erfansn.composablescreens.food.ui.component.VerticalHillButton

@Composable
fun HomeRoute(
    viewModel: HomeViewModel,
    onNavigateToProduct: (id: Int) -> Unit,
    onNavigateToCart: () -> Unit,
    modifier: Modifier = Modifier
) {
    HomeScreen(
        onNavigateToCart = onNavigateToCart,
        onNavigateToProduct = onNavigateToProduct,
        modifier = modifier,
        vitrineItems = viewModel.vitrineItems
    )
}

@Composable
private fun HomeScreen(
    onNavigateToCart: () -> Unit,
    onNavigateToProduct: (id: Int) -> Unit,
    vitrineItems: List<VitrineItem>,
    modifier: Modifier = Modifier
) {
    FoodScaffold(
        topBar = {
            HomeTopBar(
                onNavigateToCart = onNavigateToCart
            )
        },
        bottomBar = {
            HomeNavigationBar()
        },
        modifier = modifier,
    ) {
        HomeContent(
            modifier = Modifier
                .padding(it)
                .consumeWindowInsets(it),
            onNavigateToProduct = onNavigateToProduct,
            vitrineItems = vitrineItems
        )
    }
}

@Composable
private fun HomeTopBar(
    onNavigateToCart: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(48.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            val titleTextStyle = FoodTheme.typography.displaySmall
            val title = buildAnnotatedString {
                append("Choose ")
                withStyle(
                    titleTextStyle.copy(fontWeight = FontWeight.Bold).toSpanStyle()
                ) {
                    append("cookies\n")
                }
                append("for your tea party")
            }
            Text(
                text = title,
                style = titleTextStyle,
                maxLines = 2,
                modifier = Modifier.padding(start = 24.dp)
            )

            VerticalHillButton(
                title = "Cart",
                onClick = dropUnlessResumed {
                    onNavigateToCart()
                }
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .horizontalScroll(state = rememberScrollState())
                .padding(horizontal = 24.dp),
        ) {
            var selectedCollectionIndex by remember { mutableIntStateOf(0) }
            var previouslySelectedCollectionIndex by remember { mutableIntStateOf(0) }
            val animateDirection =
                if (selectedCollectionIndex > previouslySelectedCollectionIndex) {
                    Direction.Right
                } else {
                    Direction.Left
                }
            for ((index, collection) in collections.withIndex()) {
                key(index) {
                    CollectionItem(
                        selected = selectedCollectionIndex == index,
                        onClick = {
                            previouslySelectedCollectionIndex = selectedCollectionIndex
                            selectedCollectionIndex = index
                        },
                        name = collection.name,
                        icon = collection.icon.toPainterOrNull(),
                        animateDirection = animateDirection
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
        ) {
            IconButton(
                onClick = { /*TODO*/ },
            ) {
                Icon(imageVector = Icons.Rounded.DesktopWindows, contentDescription = "Account")
            }
            Box {
                var openPopup by remember { mutableStateOf(false) }
                var filterTextHeight by remember { mutableIntStateOf(0) }
                Row(
                    modifier = Modifier
                        .clickable { openPopup = true }
                        .onSizeChanged {
                            filterTextHeight = it.height
                        }
                ) {
                    Text(text = "Popular")
                    Icon(imageVector = Icons.Rounded.KeyboardArrowDown, contentDescription = null)
                }
                if (openPopup) {
                    Popup(
                        onDismissRequest = {
                            openPopup = false
                        },
                        offset = IntOffset(0, filterTextHeight)
                    ) {
                        Text(text = "Nothing!")
                    }
                }
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Rounded.Settings, contentDescription = "Settings")
            }
        }
    }
}

@Composable
private fun HomeContent(
    vitrineItems: List<VitrineItem>,
    onNavigateToProduct: (id: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { vitrineItems.size })
    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(
            horizontal = 24.dp,
            vertical = 16.dp
        ),
        key = { vitrineItems[it].id },
        beyondViewportPageCount = 1,
        modifier = modifier
            .fillMaxSize()
    ) {
        VitrineItemCard(
            vitrineItem = vitrineItems[it],
            onClick = dropUnlessResumed {
                onNavigateToProduct(vitrineItems[it].id)
            },
            modifier = Modifier
                .graphicsLayer {
                    val fromCurrentPageOffset = pagerState
                        .getOffsetDistanceInPages(it)

                    rotationZ = -12 * fromCurrentPageOffset.coerceIn(-1f, 1f)
                }
                .zIndex(vitrineItems.size - it.toFloat()),
        )
    }
}

private val navItemsIcons = listOf(
    Icons.Rounded.Window,
    Icons.Rounded.CardGiftcard,
    Icons.Rounded.FavoriteBorder,
    Icons.Rounded.PersonOutline
)

@Composable
private fun HomeNavigationBar() {
    var navigationBarItemIndex by remember { mutableIntStateOf(0) }
    FoodNavigationBar(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 43.dp, topEnd = 43.dp))
    ) {
        for ((index, icon) in navItemsIcons.withIndex()) {
            FoodNavigationBarItem(
                selected = index == navigationBarItemIndex,
                onClick = {
                    navigationBarItemIndex = index
                },
                icon = icon
            )
        }
    }
}

@Composable
private fun FoodNavigationBar(
    modifier: Modifier = Modifier,
    tonalElevation: Dp = NavigationBarDefaults.Elevation,
    windowInsets: WindowInsets = NavigationBarDefaults.windowInsets,
    content: @Composable RowScope.() -> Unit
) {
    Surface(
        color = FoodTheme.colors.secondary,
        contentColor = FoodTheme.colors.onSecondary,
        tonalElevation = tonalElevation,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(windowInsets)
                .defaultMinSize(minHeight = 118.dp)
                .selectableGroup()
                .padding(horizontal = 32.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            content = content
        )
    }
}

@Composable
private fun FoodNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(CircleShape)
            .clickable(onClick = onClick)
            .background(if (selected) FoodTheme.colors.primary else Color.Unspecified)
            .padding(
                vertical = 12.dp,
                horizontal = 2.dp,
            )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (selected) FoodTheme.colors.onPrimary else FoodTheme.colors.onSecondary,
            modifier = Modifier.minimumInteractiveComponentSize()
        )
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    FoodTheme {
        HomeScreen(
            onNavigateToCart = { },
            onNavigateToProduct = { },
            vitrineItems = sampleVitrineItems
        )
    }
}
