@file:OptIn(ExperimentalMaterialApi::class)

package ir.erfansn.composablescreens.travel.home

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.boundingRect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.compose.ui.zIndex
import ir.erfansn.composablescreens.travel.R
import ir.erfansn.composablescreens.travel.ui.theme.TravelOneTheme
import kotlin.random.Random
import android.graphics.Color as PlatformColor

@Composable
fun HomeScreen() {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Scaffold(
            modifier = Modifier
                .background(Color(0xFFF5F5F5))
                .fillMaxSize()
                .systemGesturesPadding()
                .systemBarsPadding(),
            backgroundColor = Color(0xFFF5F5F5),
            topBar = {
                TravelOneTopBar()
            },
            bottomBar = {
                TravelOneBottomNavigationBar()
            }
        ) { contentPadding ->
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                val availableHeightSpace = maxHeight
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
                    .verticalScroll(rememberScrollState())
                ) {
                    HomeSection(
                        title = "Travel Places",
                        modifier = Modifier.weight(0.625f).takeIf {
                            availableHeightSpace > 400.dp
                        } ?: Modifier.height(264.dp)
                    ) {
                        Row(modifier = Modifier.fillMaxHeight()) {
                            var selectedCategory by remember { mutableStateOf(PlaceCategory.Popular) }
                            PlaceCategoryTabsRow(
                                modifier = Modifier
                                    .intoVertical()
                                    .fillMaxWidth()
                                    .padding(
                                        top = 20.dp,
                                        bottom = 8.dp
                                    )
                                    .padding(horizontal = 12.dp),
                                selectedCategory = selectedCategory,
                                onTabSelect = { selectedCategory = it }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            TravelPlacesRow()
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    HomeSection(
                        modifier = Modifier.weight(0.375f).takeIf {
                            availableHeightSpace > 400.dp
                        } ?: Modifier.height(150.dp),
                        title = "Travel Groups",
                    ) {
                        TravelGroupsRow()
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
fun TravelOneTopBar(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(
                horizontal = 24.dp,
                vertical = 16.dp
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                TravelOneButton(
                    modifier = Modifier.size(56.dp),
                    onClick = { /*TODO*/ }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = "User profile",
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.width(18.dp))
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = "Hello Laura",
                    style = MaterialTheme.typography.subtitle2
                )
            }
            TravelOneButton(
                modifier = Modifier
                    .shadow(
                        color = Color(0xCCCCCCCC),
                        shape = RoundedCornerShape(18.dp),
                        radius = 24.dp,
                        dx = 10.dp,
                        dy = 10.dp
                    )
                    .size(56.dp),
                onClick = { /*TODO*/ },
                backgroundColor = Color.White
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_category),
                    contentDescription = "Menu"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = """
                        Explore the
                        Beautiful world!
                    """.trimIndent(),
            style = MaterialTheme.typography.h5,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            var searchKey by remember { mutableStateOf("") }
            TravelOneSearchBar(
                modifier = Modifier.weight(1.0f),
                value = searchKey,
                onValueChange = {
                    searchKey = it
                }
            )

            Spacer(modifier = Modifier.width(16.dp))

            TravelOneButton(
                modifier = Modifier
                    .shadow(
                        color = Color(0xFFFFE03F).copy(alpha = 0.8f),
                        shape = RoundedCornerShape(18.dp),
                        radius = 24.dp,
                        dx = 10.dp,
                        dy = 10.dp
                    )
                    .size(56.dp),
                onClick = { },
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFFFFE03F),
                                    Color(0xFFFFAB00),
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        tint = Color.White,
                        painter = painterResource(id = R.drawable.ic_filter),
                        contentDescription = "Settings"
                    )
                }
            }
        }
    }
}

@Composable
fun TravelOneBottomNavigationBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(horizontal = 24.dp)
            .padding(bottom = 20.dp)
            .fillMaxWidth()
            .height(64.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        TravelOneButton(
            modifier = Modifier.aspectRatio(1.0f),
            indication = null,
            onClick = { /*TODO*/ }
        ) {
            Icon(
                tint = Color.LightGray,
                painter = painterResource(id = R.drawable.ic_home),
                contentDescription = "Add"
            )
        }
        TravelOneButton(
            modifier = Modifier.aspectRatio(1.0f),
            backgroundColor = Color.Black,
            indication = rememberRipple(color = Color.White),
            onClick = { /*TODO*/ }
        ) {
            Icon(
                tint = Color.White,
                imageVector = Icons.Rounded.Add,
                contentDescription = "Add"
            )
        }
        TravelOneButton(
            modifier = Modifier.aspectRatio(1.0f),
            indication = null,
            onClick = { /*TODO*/ }
        ) {
            Icon(
                tint = Color.LightGray,
                painter = painterResource(id = R.drawable.ic_chat),
                contentDescription = "Add"
            )
        }
    }
}

@Composable
fun TravelOneButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    backgroundColor: Color = Color.Transparent,
    indication: Indication? = rememberRipple(),
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(22.4.dp))
            .background(color = backgroundColor)
            .defaultMinSize(
                minWidth = 48.dp,
                minHeight = 48.dp
            )
            .clickable(
                onClick = onClick,
                enabled = true,
                role = Role.Button,
                interactionSource = remember { MutableInteractionSource() },
                indication = indication
            ),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
fun HomeSection(
    modifier: Modifier = Modifier,
    title: String,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.subtitle1
            )
            Text(
                modifier = Modifier.alpha(ContentAlpha.disabled),
                text = "Show more >",
                style = MaterialTheme.typography.caption
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        content()
    }
}

enum class PlaceCategory { All, Latest, Popular }

@Composable
fun PlaceCategoryTabsRow(
    modifier: Modifier,
    selectedCategory: PlaceCategory,
    onTabSelect: (PlaceCategory) -> Unit,
) {
    Row(
        modifier = modifier.selectableGroup(),
        horizontalArrangement = Arrangement.spacedBy(Arrangement.SpaceBetween.spacing)
    ) {
        PlaceCategoryTab(
            modifier = Modifier
                .weight(1.0f),
            title = "All",
            selected = selectedCategory == PlaceCategory.All,
            onClick = { onTabSelect(PlaceCategory.All) }
        )
        PlaceCategoryTab(
            modifier = Modifier
                .weight(1.0f),
            title = "Latest",
            selected = selectedCategory == PlaceCategory.Latest,
            onClick = { onTabSelect(PlaceCategory.Latest) }
        )
        PlaceCategoryTab(
            modifier = Modifier
                .weight(1.0f),
            title = "Popular",
            selected = selectedCategory == PlaceCategory.Popular,
            onClick = { onTabSelect(PlaceCategory.Popular) }
        )
    }
}

@Composable
fun PlaceCategoryTab(
    modifier: Modifier,
    onClick: () -> Unit,
    selected: Boolean,
    title: String,
) {
    Column(
        modifier = modifier
            .selectable(
                selected = selected,
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ),
    ) {
        val contentColor = if (selected) Color(0xFFFF6F00) else Color.Black
        CompositionLocalProvider(
            LocalContentColor provides contentColor,
            LocalContentAlpha provides ContentAlpha.high
        ) {
            if (selected) {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .align(Alignment.CenterHorizontally)
                        .clip(CircleShape)
                        .background(LocalContentColor.current),
                    thickness = 4.dp
                )
            }
            Spacer(modifier = Modifier.height(if (selected) 8.dp else 12.dp))
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = title,
                style = MaterialTheme.typography.button
            )
        }
    }
}

@Composable
fun TravelPlacesRow() {
    val trips = remember {
        List(10) {
            if (it % 2 == 0) {
                Trip(
                    id = it,
                    destination = Place(
                        cityName = "City Rome",
                        countryName = "Italy",
                        imageId = R.drawable.colossians_philemon
                    ),
                    days = 5,
                    price = 740
                )
            } else {
                Trip(
                    id = it,
                    destination = Place(
                        cityName = "Grand Canton",
                        countryName = "USA",
                        imageId = R.drawable.antelope_canyon
                    ),
                    days = 7,
                    price = 240
                )
            }
        }
    }
    LazyRow(
        modifier = Modifier.fillMaxHeight(),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(start = 8.dp, end = 16.dp)
    ) {
        items(items = trips, key = { it.id }) { trip ->
            TravelPlaceItem(
                modifier = Modifier
                    .fillParentMaxHeight()
                    .aspectRatio(4 / 5f),
                trip = trip
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TravelPlaceItem(
    modifier: Modifier = Modifier,
    trip: Trip,
) {
    Box(modifier = modifier
        .shadow(
            color = Color.LightGray.copy(alpha = 0.4f),
            shape = RoundedCornerShape(32.dp),
            radius = 18.dp,
            dx = 10.dp,
            dy = 10.dp
        )
    ) {
        Icon(
            modifier = Modifier
                .zIndex(1f)
                .align(Alignment.TopEnd)
                .size(28.dp)
                .offset(
                    y = (-6).dp,
                    x = (-30).dp
                ),
            painter = painterResource(id = R.drawable.ic_bookmark),
            contentDescription = null,
            tint = if (Random.nextBoolean()) Color(0xFFFFD600) else Color.LightGray
        )
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(32.dp))
                .background(Color.White)
        ) {
            Column(modifier = Modifier
                .fillMaxHeight()
                .padding(10.dp)
            ) {
                Box {
                    Image(
                        modifier = Modifier
                            .aspectRatio(10 / 9f)
                            .clip(RoundedCornerShape(22.dp)),
                        painter = painterResource(id = trip.destination.imageId),
                        contentDescription = "Place",
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(end = 10.dp, bottom = 12.dp)
                            .size(64.dp, 32.dp)
                            .clip(RoundedCornerShape(40))
                            .background(Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.price, trip.price),
                            style = MaterialTheme.typography.overline
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .padding(top = 12.dp)
                ) {
                    Text(
                        text = trip.destination.cityName,
                        style = MaterialTheme.typography.caption.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.disabled) {
                            Row {
                                val height = with(LocalDensity.current) { 10.sp.toDp() }
                                Icon(
                                    modifier = Modifier
                                        .height(height)
                                        .align(Alignment.CenterVertically),
                                    painter = painterResource(id = R.drawable.ic_location),
                                    contentDescription = "Location"
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(
                                    text = trip.destination.countryName,
                                    color = Color.Gray,
                                    style = MaterialTheme.typography.overline
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(40))
                                .background(Color.LightGray.copy(alpha = 0.4f))
                                .wrapContentSize()
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                modifier = Modifier.alpha(ContentAlpha.disabled),
                                text = pluralStringResource(R.plurals.n_day, trip.days, trip.days),
                                style = MaterialTheme.typography.overline
                            )
                        }
                    }
                }
            }
        }
    }
}

fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException()
}


data class Group(
    val id: Int,
    val name: String,
    @DrawableRes val image: Int,
)

@Composable
fun TravelGroupsRow() {
    val groups = remember {
        List(10) {
            Group(
                id = it,
                name = "The Wave Arizona",
                image = if (it % 2 == 0) R.drawable.the_wave else R.drawable.greece
            )
        }
    }
    LazyRow(
        modifier = Modifier.fillMaxHeight(),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(horizontal = 24.dp)
    ) {
        items(items = groups, key = { it.id }) { group ->
            TravelOneGroupItem(
                modifier = Modifier
                    .fillParentMaxHeight()
                    .wrapContentWidth(),
                group = group,
            )
        }
    }
}

@Composable
fun TravelOneGroupItem(
    modifier: Modifier = Modifier,
    group: Group,
) {
    Box(
        modifier = modifier
            .shadow(
                color = Color.LightGray.copy(alpha = 0.4f),
                shape = RoundedCornerShape(32.dp),
                radius = 18.dp,
                dx = 10.dp,
                dy = 10.dp
            ),
    ) {
        Row(
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(28.dp))
                .padding(14.dp)
                .width(IntrinsicSize.Max),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Image(
                modifier = Modifier
                    .aspectRatio(4 / 5f)
                    .clip(RoundedCornerShape(18.dp))
                    .background(color = Color.Green),
                painter = painterResource(id = group.image),
                contentScale = ContentScale.Crop,
                contentDescription = "Location"
            )
            Column(modifier = Modifier.widthIn(min = 124.dp)) {
                Column(modifier = Modifier.weight(0.5f)) {
                    Text(
                        text = group.name,
                        style = MaterialTheme.typography.caption.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        modifier = Modifier.alpha(ContentAlpha.disabled),
                        text = "Group Members",
                        style = MaterialTheme.typography.overline
                    )
                }
                Box(modifier = Modifier.weight(0.45f)) {
                    repeat(4) {
                        Box(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .fillMaxSize()
                                .offset(x = (28 * it).dp)
                                .background(
                                    color = Color.White,
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (it != 3) {
                                Image(
                                    modifier = Modifier
                                        .padding(2.dp)
                                        .fillMaxSize()
                                        .clip(CircleShape),
                                    painter = painterResource(when (it) {
                                        1 -> R.drawable.person_one
                                        2 -> R.drawable.person_two
                                        else -> R.drawable.person_three
                                    }),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Box(
                                    modifier = Modifier
                                        .padding(2.dp)
                                        .fillMaxSize()
                                        .background(
                                            color = Color(0xFFFF6F00),
                                            shape = CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "20+",
                                        color = Color.White,
                                        style = MaterialTheme.typography.overline
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun TravelOneSearchBar(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
) {
    BasicTextField(
        value = value,
        modifier = modifier
            .background(Color.White, RoundedCornerShape(22.4.dp))
            .defaultMinSize(
                minWidth = TextFieldDefaults.MinWidth,
                minHeight = TextFieldDefaults.MinHeight
            ),
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(),
        singleLine = true,
        decorationBox = @Composable { innerTextField ->
            TextFieldDefaults.TextFieldDecorationBox(
                value = value,
                visualTransformation = VisualTransformation.None,
                innerTextField = innerTextField,
                placeholder = {
                    Text(
                        text = "Search places",
                        style = MaterialTheme.typography.body2,

                    )
                },
                label = null,
                leadingIcon = {
                    Icon(
                        modifier = Modifier.padding(start = 28.dp),
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "Search"
                    )
                },
                trailingIcon = null,
                singleLine = true,
                enabled = true,
                interactionSource = remember { MutableInteractionSource() },
                colors = TextFieldDefaults.textFieldColors(
                    leadingIconColor = Color.Black,
                    placeholderColor = Color.Black.copy(ContentAlpha.medium)
                ),
                contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                    start = 28.dp
                )
            )
        }
    )
}

data class Trip(
    val id: Int,
    val destination: Place,
    val days: Int,
    val price: Int,
)

data class Place(
    @DrawableRes val imageId: Int,
    val cityName: String,
    val countryName: String,
)

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

fun Modifier.shadow(
    shape: Shape = RectangleShape,
    color: Color = Color(0xCCCCCCCC),
    radius: Dp = 0.dp,
    dx: Dp = 0.dp,
    dy: Dp = 0.dp,
) = drawBehind {
    val paint = Paint().asFrameworkPaint().apply {
        this.color = PlatformColor.TRANSPARENT
        setShadowLayer(
            radius.toPx(),
            dx.toPx(),
            dy.toPx(),
            color.toArgb()
        )
    }
    drawIntoCanvas {
        when (val outline = shape.createOutline(size, layoutDirection, this)) {
            is Outline.Rectangle -> {
                it.nativeCanvas.drawRect(
                    outline.rect.toAndroidRectF(),
                    paint
                )
            }
            is Outline.Rounded -> {
                val roundRect = outline.roundRect
                val topLeftCorner = roundRect.topLeftCornerRadius
                it.nativeCanvas.drawRoundRect(
                    roundRect.boundingRect.toAndroidRectF(),
                    topLeftCorner.x,
                    topLeftCorner.y,
                    paint
                )
            }
            is Outline.Generic -> {
                it.nativeCanvas.drawPath(
                    outline.path.asAndroidPath(),
                    paint
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    device = Devices.PHONE,
)
@Composable
fun HomeScreenPreview() {
    TravelOneTheme {
        HomeScreen()
    }
}
