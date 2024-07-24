package ir.erfansn.composablescreens.food.feature.home

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.zIndex
import ir.erfansn.composablescreens.food.ui.FoodTheme
import kotlin.math.absoluteValue

@Composable
fun HomeScreen() {
    Scaffold(
        bottomBar = {
            HomeNavigationBar()
        },
        contentWindowInsets = WindowInsets.safeDrawing
    ) {
        HomeContent(
            modifier = Modifier
                .padding(it)
                .consumeWindowInsets(it)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            val titleTextStyle = MaterialTheme.typography.displaySmall
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

            Column(
                modifier = Modifier
                    .intoVertical()
                    .defaultMinSize(109.dp, 52.dp)
                    .clip(object : Shape {
                        override fun createOutline(
                            size: Size,
                            layoutDirection: LayoutDirection,
                            density: Density
                        ): Outline {
                            val (width, height) = size

                            val peakPercent = 0.3f
                            return Outline.Generic(
                                Path().apply {
                                    moveTo(
                                        x = width * 0f,
                                        y = height * 1f
                                    )
                                    cubicTo(
                                        x1 = width * (peakPercent * 0.5f),
                                        y1 = height * 0f,
                                        x2 = width * (peakPercent * 0.5f),
                                        y2 = height * -0.05f,
                                        x3 = width * 0.5f,
                                        y3 = height * 0f
                                    )
                                    cubicTo(
                                        x1 = width * (1f - (peakPercent * 0.5f)),
                                        y1 = height * 0f,
                                        x2 = width * (1f - (peakPercent * 0.5f)),
                                        y2 = height * -0.05f,
                                        x3 = width * 1f,
                                        y3 = height * 1f
                                    )
                                },
                            )
                        }
                    })
                    .clickable { }
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(bottom = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = null,
                    modifier = Modifier
                        .size(12.dp)
                        .offset(y = 2.dp)
                )
                Text(
                    text = "Cart",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        var selectedCollection by remember { mutableStateOf(collections.first()) }
        Row(
            modifier = Modifier
                .horizontalScroll(state = rememberScrollState())
                .padding(horizontal = 24.dp),
        ) {
            for (collection in collections) {
                CollectionItem(
                    collection = collection,
                    selected = selectedCollection == collection,
                    onClick = {
                        selectedCollection = collection
                    }
                )
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
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Account")
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
                    Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null)
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
                Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings")
            }
        }

        val pagerState = rememberPagerState(pageCount = { vitrineItems.size })
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(
                horizontal = 24.dp,
                vertical = 16.dp
            ),
            key = { vitrineItems[it].hashCode() },
            beyondBoundsPageCount = 3,
        ) {
            VitrineItemCard(
                vitrineItem = vitrineItems[it],
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .graphicsLayer {
                        val currentPageOffset = pagerState.getOffsetFractionForPage(it)

                        rotationZ = 12 * currentPageOffset.coerceIn(-1f, 1f)
                        translationX = -80 * pagerState.currentPageOffsetFraction.absoluteValue * 2f
                    }
                    .zIndex(vitrineItems.size - it.toFloat()),
            )
        }
    }
}

private fun Modifier.intoVertical() = layout { measurable, constraints ->
    val placeable = measurable.measure(
        Constraints(
            minHeight = constraints.minWidth,
            maxHeight = constraints.maxWidth,
            minWidth = constraints.minHeight,
            maxWidth = constraints.maxHeight
        )
    )
    layout(placeable.height, placeable.width) {
        placeable.placeWithLayer(
            x = -(placeable.width / 2 - placeable.height / 2),
            y = -(placeable.height / 2 - placeable.width / 2),
            layerBlock = { rotationZ = -90f }
        )
    }
}

@Composable
private fun HomeNavigationBar() {
    var navigationBarItemIndex by remember { mutableIntStateOf(0) }
    FoodNavigationBar(
        containerColor = Color.Black,
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 34.dp, topEnd = 34.dp))
    ) {
        repeat(4) {
            FoodNavigationBarItem(
                selected = it == navigationBarItemIndex,
                onClick = {
                    navigationBarItemIndex = it
                },
                icon = Icons.Default.Home
            )
        }
    }
}

@Composable
private fun FoodNavigationBar(
    modifier: Modifier = Modifier,
    containerColor: Color = NavigationBarDefaults.containerColor,
    contentColor: Color = MaterialTheme.colorScheme.contentColorFor(containerColor),
    tonalElevation: Dp = NavigationBarDefaults.Elevation,
    windowInsets: WindowInsets = NavigationBarDefaults.windowInsets,
    content: @Composable RowScope.() -> Unit
) {
    Surface(
        color = containerColor,
        contentColor = contentColor,
        tonalElevation = tonalElevation,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(windowInsets)
                .defaultMinSize(minHeight = 108.dp)
                .selectableGroup()
                .padding(horizontal = 24.dp),
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
            .clickable(onClick = onClick)
            .clip(CircleShape)
            .background(
                if (selected) Color.Yellow else Color.Unspecified
            )
            .padding(
                vertical = 12.dp,
                horizontal = 2.dp,
            )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (selected) Color.Black else Color.White,
            modifier = Modifier.minimumInteractiveComponentSize()
        )
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    FoodTheme {
        HomeScreen()
    }
}
