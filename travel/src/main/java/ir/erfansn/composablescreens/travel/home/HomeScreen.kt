@file:OptIn(ExperimentalMaterialApi::class)

package ir.erfansn.composablescreens.travel.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstrainScope
import androidx.constraintlayout.compose.ConstraintLayout
import ir.erfansn.composablescreens.travel.R
import ir.erfansn.composablescreens.travel.ui.components.TravelButton
import ir.erfansn.composablescreens.travel.ui.components.layout.OverlappingRow
import ir.erfansn.composablescreens.travel.ui.components.modifier.shadow
import ir.erfansn.composablescreens.travel.ui.theme.AbrilFatfaceFontFamily
import ir.erfansn.composablescreens.travel.ui.theme.PoppinsFontFamily
import ir.erfansn.composablescreens.travel.ui.theme.TravelTheme
import kotlin.random.Random

@Composable
fun TravelHomeRoute(
    onTravelGroupItemClick: () -> Unit,
) {
    TravelTheme {
        TravelHomeScreen(onTravelGroupItemClick = onTravelGroupItemClick)
    }
}

@Composable
fun TravelHomeScreen(
    onTravelGroupItemClick: () -> Unit,
) {
    val baseModifier = Modifier.padding(horizontal = 24.dp)
    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
            .systemGesturesPadding()
            .systemBarsPadding(),
        topBar = {
            TravelHomeTopBar(
                modifier = baseModifier
            )
        },
        bottomBar = {
            TravelHomeBottomNavigationBar(
                modifier = baseModifier
            )
        }
    ) { contentPadding ->
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            val minHeightAvailable = maxHeight > 400.dp
            if (!minHeightAvailable) {
                Box(
                    modifier = Modifier
                        .zIndex(1.0f)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    MaterialTheme.colors.background,
                                    MaterialTheme.colors.background.copy(alpha = 0.98f),
                                    MaterialTheme.colors.background.copy(alpha = 0.94f),
                                    MaterialTheme.colors.background.copy(alpha = 0.86f),
                                    MaterialTheme.colors.background.copy(alpha = 0.70f),
                                    MaterialTheme.colors.background.copy(alpha = 0.38f),
                                    Color.Transparent
                                ),
                                start = Offset(0.0f, Float.POSITIVE_INFINITY),
                                end = Offset.Zero,
                                tileMode = TileMode.Clamp
                            )
                        )
                        .height(32.dp)
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                )
            }
            TravelHomeContent(
                modifier = Modifier
                    .zIndex(0.0f)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                minHeightAvailable = minHeightAvailable,
                onTravelGroupItemClick = onTravelGroupItemClick
            )
        }
    }
}

@Composable
fun TravelHomeTopBar(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                TravelButton(
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
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = PoppinsFontFamily
                    )
                )
            }
            TravelButton(
                modifier = Modifier
                    .shadow(
                        color = Color(0xFFCCCCCC).copy(alpha = 0.7f),
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
                    tint = Color.Black,
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
            TravelSearchBar(
                modifier = Modifier.weight(1.0f),
                value = searchKey,
                onValueChange = {
                    searchKey = it
                }
            )

            Spacer(modifier = Modifier.width(16.dp))

            TravelButton(
                modifier = Modifier
                    .shadow(
                        color = MaterialTheme.colors.primary.copy(alpha = 0.7f),
                        shape = RoundedCornerShape(18.dp),
                        radius = 24.dp,
                        dx = 8.dp,
                        dy = 8.dp
                    )
                    .size(56.dp),
                onClick = { },
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.linearGradient(
                                0.0f to MaterialTheme.colors.primary,
                                1.0f to MaterialTheme.colors.primaryVariant
                                    .copy(alpha = 0.6f)
                                    .compositeOver(MaterialTheme.colors.primary),
                            ),
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_filter),
                        contentDescription = "Settings"
                    )
                }
            }
        }
    }
}

@Composable
fun TravelHomeBottomNavigationBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(horizontal = 24.dp)
            .padding(bottom = 24.dp)
            .fillMaxWidth()
            .height(64.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        TravelButton(
            modifier = Modifier.aspectRatio(1.0f),
            backgroundColor = Color.Transparent,
            indication = null,
            onClick = { /*TODO*/ }
        ) {
            Icon(
                tint = Color.LightGray,
                painter = painterResource(id = R.drawable.ic_home),
                contentDescription = "Add"
            )
        }
        TravelButton(
            modifier = Modifier.aspectRatio(1.0f),
            backgroundColor = MaterialTheme.colors.secondaryVariant,
            indication = rememberRipple(color = Color.White),
            onClick = { /*TODO*/ }
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "Add"
            )
        }
        TravelButton(
            modifier = Modifier.aspectRatio(1.0f),
            indication = null,
            backgroundColor = Color.Transparent,
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
fun TravelHomeContent(
    modifier: Modifier = Modifier,
    minHeightAvailable: Boolean = true,
    onTravelGroupItemClick: () -> Unit,
) {
    Column(modifier = modifier) {
        HomeSection(
            title = "Travel Places",
            modifier = Modifier.weight(0.65f).takeIf { minHeightAvailable }
                ?: Modifier.height(264.dp)
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
                        ),
                    selectedCategory = selectedCategory,
                    onTabSelect = { selectedCategory = it }
                )
                Spacer(modifier = Modifier.width(8.dp))
                TravelPlacesRow()
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        HomeSection(
            modifier = Modifier.weight(0.35f).takeIf { minHeightAvailable }
                ?: Modifier.height(150.dp),
            title = "Travel Groups",
        ) {
            TravelGroupsRow(
                onTravelGroupItemClick = onTravelGroupItemClick
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
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
    CompositionLocalProvider(LocalContentColor provides MaterialTheme.colors.primaryVariant) {
        ConstraintLayout(modifier = modifier) {
            val (cursor, all, latest, popular) = createRefs()
            createHorizontalChain(all, latest, popular, chainStyle = ChainStyle.Spread)

            var cursorPlacement by remember { mutableStateOf(popular) }
            Divider(
                modifier = Modifier
                    .width(24.dp)
                    .padding()
                    .clip(CircleShape)
                    .constrainAs(cursor) {
                        linkTo(
                            start = cursorPlacement.start,
                            end = cursorPlacement.end,
                        )
                    },
                color = LocalContentColor.current,
                thickness = 4.dp
            )

            val constraintBlock: ConstrainScope.() -> Unit = { top.linkTo(cursor.bottom) }
            PlaceCategoryTab(
                modifier = Modifier.constrainAs(all, constraintBlock),
                title = "All",
                selected = selectedCategory == PlaceCategory.All,
                onClick = {
                    onTabSelect(PlaceCategory.All)
                    cursorPlacement = all
                }
            )
            PlaceCategoryTab(
                modifier = Modifier.constrainAs(latest, constraintBlock),
                title = "Latest",
                selected = selectedCategory == PlaceCategory.Latest,
                onClick = {
                    onTabSelect(PlaceCategory.Latest)
                    cursorPlacement = latest
                }
            )
            PlaceCategoryTab(
                modifier = Modifier.constrainAs(popular, constraintBlock),
                title = "Popular",
                selected = selectedCategory == PlaceCategory.Popular,
                onClick = {
                    onTabSelect(PlaceCategory.Popular)
                    cursorPlacement = popular
                }
            )
        }
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
            )
            .padding(top = 8.dp),
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = title,
            style = MaterialTheme.typography.button.copy(
                fontFamily = AbrilFatfaceFontFamily
            ),
            color = if (selected) LocalContentColor.current else MaterialTheme.colors.onBackground
        )
    }
}

@Composable
fun TravelPlacesRow(
    modifier: Modifier = Modifier,
    trips: List<Trip> = remember {
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
    },
) {
    LazyRow(
        modifier = modifier.fillMaxHeight(),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(start = 8.dp, end = 24.dp)
    ) {
        items(trips) { trip ->
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
            color = Color.LightGray.copy(alpha = 0.3f),
            shape = MaterialTheme.shapes.medium,
            radius = 16.dp,
            dx = 8.dp,
            dy = 8.dp
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
            tint = with(MaterialTheme.colors) {
                if (Random.nextBoolean()) primary else secondary
            }
        )
        Box(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colors.surface)
        ) {
            Column(modifier = Modifier
                .fillMaxHeight()
                .padding(10.dp)
            ) {
                Box {
                    Image(
                        modifier = Modifier
                            .aspectRatio(10 / 9f)
                            .clip(MaterialTheme.shapes.medium.copy(CornerSize(22.dp))),
                        painter = painterResource(id = trip.destination.imageId),
                        contentDescription = "Place",
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(end = 10.dp, bottom = 12.dp)
                            .size(64.dp, 32.dp)
                            .clip(RoundedCornerShape(33))
                            .background(MaterialTheme.colors.background),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = Modifier,
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
                                    style = MaterialTheme.typography.overline
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(40))
                                    .background(MaterialTheme.colors.background),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    modifier = Modifier
                                        .padding(6.dp),
                                    text = pluralStringResource(R.plurals.n_day,
                                        trip.days,
                                        trip.days),
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

@Composable
fun TravelGroupsRow(
    modifier: Modifier = Modifier,
    groups: List<Group> = remember {
        List(10) {
            Group(
                id = it,
                name = "The Wave Arizona",
                image = if (it % 2 == 0) R.drawable.the_wave else R.drawable.greece
            )
        }
    },
    onTravelGroupItemClick: () -> Unit,
) {
    LazyRow(
        modifier = modifier.fillMaxHeight(),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(horizontal = 24.dp)
    ) {
        items(items = groups) { group ->
            TravelGroupItem(
                modifier = Modifier
                    .fillParentMaxHeight()
                    .wrapContentWidth(),
                group = group,
                onClick = onTravelGroupItemClick
            )
        }
    }
}

@Composable
fun TravelGroupItem(
    modifier: Modifier = Modifier,
    group: Group,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .shadow(
                color = Color.LightGray.copy(alpha = 0.3f),
                shape = MaterialTheme.shapes.medium,
                radius = 16.dp,
                dx = 8.dp,
                dy = 8.dp
            )
            .clip(shape = MaterialTheme.shapes.medium.copy(CornerSize(28.dp)))
            .clickable(onClick = onClick),
    ) {
        Row(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colors.surface,
                    shape = MaterialTheme.shapes.medium.copy(CornerSize(28.dp))
                )
                .padding(14.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Image(
                modifier = Modifier
                    .aspectRatio(4 / 5f)
                    .clip(RoundedCornerShape(18.dp)),
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
                OverlappingRow(modifier = Modifier.weight(0.475f)) {
                    val profileImageIds = listOf(
                        R.drawable.person_one,
                        R.drawable.person_two,
                        R.drawable.person_three
                    )
                    profileImageIds.forEach {
                        Image(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .fillMaxSize()
                                .background(
                                    color = MaterialTheme.colors.surface,
                                    shape = CircleShape
                                )
                                .padding(2.dp)
                                .clip(CircleShape),
                            painter = painterResource(id = it),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .fillMaxSize()
                            .background(
                                color = MaterialTheme.colors.surface,
                                shape = CircleShape
                            )
                            .padding(2.dp)
                            .background(
                                color = MaterialTheme.colors.primaryVariant,
                                shape = CircleShape
                            )
                            .clip(CircleShape),
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

@ExperimentalMaterialApi
@Composable
fun TravelSearchBar(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
) {
    val customTextSelectionColors = TextSelectionColors(
        handleColor = MaterialTheme.colors.primary,
        backgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.4f)
    )
    CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
        BasicTextField(
            value = value,
            modifier = modifier
                .background(MaterialTheme.colors.surface, MaterialTheme.shapes.small)
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
                            contentDescription = null
                        )
                    },
                    trailingIcon = null,
                    singleLine = true,
                    enabled = true,
                    interactionSource = remember { MutableInteractionSource() },
                    colors = TextFieldDefaults.textFieldColors(
                        leadingIconColor = MaterialTheme.colors.onSurface,
                    ),
                    contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                        start = 28.dp
                    )
                )
            }
        )
    }
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

data class Group(
    val id: Int,
    val name: String,
    @DrawableRes val image: Int,
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

@Preview(
    showBackground = true,
    device = Devices.PHONE,
)
@Composable
fun HomeScreenPreview() {
    TravelTheme {
        TravelHomeScreen(
            onTravelGroupItemClick = { }
        )
    }
}
