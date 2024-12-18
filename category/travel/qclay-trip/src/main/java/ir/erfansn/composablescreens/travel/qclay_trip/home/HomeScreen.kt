/*
 * Copyright 2024 Erfan Sn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:OptIn(ExperimentalMaterialApi::class)

package ir.erfansn.composablescreens.travel.qclay_trip.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstrainScope
import androidx.constraintlayout.compose.ConstraintLayout
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import ir.erfansn.composablescreens.travel.qclay_trip.R
import ir.erfansn.composablescreens.travel.qclay_trip.ui.components.QclayTripButton
import ir.erfansn.composablescreens.travel.qclay_trip.ui.components.QclayTripIconButton
import ir.erfansn.composablescreens.travel.qclay_trip.ui.components.layout.OverlappingRow
import ir.erfansn.composablescreens.travel.qclay_trip.ui.components.modifier.shadow
import ir.erfansn.composablescreens.travel.qclay_trip.ui.theme.AbrilFatfaceFontFamily
import ir.erfansn.composablescreens.travel.qclay_trip.ui.theme.PoppinsFontFamily
import ir.erfansn.composablescreens.travel.qclay_trip.ui.theme.QclayTripTheme
import kotlin.random.Random

@Composable
internal fun HomeRoute(onTravelGroupItemClick: () -> Unit) {
  HomeScreen(onTravelGroupItemClick = onTravelGroupItemClick)
}

@Composable
private fun HomeScreen(onTravelGroupItemClick: () -> Unit) {
  val baseModifier = Modifier.padding(horizontal = 24.dp)
  Scaffold(
    modifier =
      Modifier
        .background(MaterialTheme.colors.background)
        .fillMaxSize()
        .safeDrawingPadding(),
    topBar = {
      HomeTopBar(
        modifier = baseModifier,
      )
    },
    bottomBar = {
      HomeBottomNavigationBar(
        modifier = baseModifier,
      )
    },
  ) { contentPadding ->
    BoxWithConstraints(
      modifier =
        Modifier
          .fillMaxSize()
          .padding(contentPadding),
    ) {
      val minHeightAvailable = maxHeight > 400.dp
      if (!minHeightAvailable) {
        Box(
          modifier =
            Modifier
              .zIndex(1.0f)
              .background(
                brush =
                  Brush.linearGradient(
                    colors =
                      listOf(
                        MaterialTheme.colors.background,
                        MaterialTheme.colors.background.copy(alpha = 0.98f),
                        MaterialTheme.colors.background.copy(alpha = 0.94f),
                        MaterialTheme.colors.background.copy(alpha = 0.86f),
                        MaterialTheme.colors.background.copy(alpha = 0.70f),
                        MaterialTheme.colors.background.copy(alpha = 0.38f),
                        Color.Transparent,
                      ),
                    start = Offset(0.0f, Float.POSITIVE_INFINITY),
                    end = Offset.Zero,
                    tileMode = TileMode.Clamp,
                  ),
              ).height(32.dp)
              .fillMaxWidth()
              .align(Alignment.BottomCenter),
        )
      }
      HomeContent(
        modifier =
          Modifier
            .zIndex(0.0f)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        minHeightAvailable = minHeightAvailable,
        onTravelGroupItemClick = onTravelGroupItemClick,
      )
    }
  }
}

@Composable
private fun HomeTopBar(modifier: Modifier = Modifier) {
  Column(
    modifier =
      modifier
        .padding(vertical = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
    ) {
      Row {
        QclayTripButton(
          modifier = Modifier.size(56.dp),
          onClick = { },
        ) {
          Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "User profile",
            contentScale = ContentScale.Crop,
          )
        }
        Spacer(modifier = Modifier.width(18.dp))
        Text(
          modifier = Modifier.align(Alignment.CenterVertically),
          text = "Hello Laura",
          style =
            MaterialTheme.typography.subtitle1.copy(
              fontWeight = FontWeight.SemiBold,
              fontFamily = PoppinsFontFamily,
            ),
        )
      }
      QclayTripIconButton(
        iconTint = Color.Black,
        iconId = R.drawable.ic_category,
        contentDescription = "Category",
        shadowColor = Color.DarkGray,
        containerColor = Color.White,
        onClick = { },
      )
    }
    Text(
      text =
        """
        Explore the
        Beautiful world!
        """.trimIndent(),
      style = MaterialTheme.typography.h5,
    )
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(16.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      var query by remember { mutableStateOf("") }
      SearchBar(
        modifier = Modifier.weight(1.0f),
        value = query,
        onValueChange = { query = it },
      )

      QclayTripButton(
        containerColor = Color.Transparent,
        shadowColor = MaterialTheme.colors.primary,
        onClick = { },
      ) {
        Box(
          modifier =
            Modifier
              .matchParentSize()
              .background(
                brush =
                  Brush.linearGradient(
                    0.0f to MaterialTheme.colors.primary,
                    1.0f to
                      MaterialTheme.colors.primaryVariant
                        .copy(alpha = 0.6f)
                        .compositeOver(MaterialTheme.colors.primary),
                  ),
              ),
          contentAlignment = Alignment.Center,
        ) {
          Icon(
            tint = Color.White,
            painter = painterResource(id = R.drawable.ic_filter),
            contentDescription = "Settings",
          )
        }
      }
    }
  }
}

@Composable
private fun HomeBottomNavigationBar(modifier: Modifier = Modifier) {
  Row(
    modifier =
      modifier
        .padding(bottom = 24.dp)
        .fillMaxWidth()
        .height(64.dp),
    horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    QclayTripIconButton(
      modifier = Modifier.aspectRatio(1.0f),
      containerColor = Color.Transparent,
      onClick = { },
      iconId = R.drawable.ic_home,
      iconTint = Color.LightGray,
      contentDescription = "Home",
    )
    QclayTripIconButton(
      modifier = Modifier.aspectRatio(1.0f),
      containerColor = MaterialTheme.colors.secondaryVariant,
      onClick = { },
      iconId = R.drawable.ic_add,
      iconTint = Color.White,
      contentDescription = "Add",
    )
    QclayTripIconButton(
      modifier = Modifier.aspectRatio(1.0f),
      containerColor = Color.Transparent,
      onClick = { },
      iconId = R.drawable.ic_chat,
      iconTint = Color.LightGray,
      contentDescription = "Chat",
    )
  }
}

@Composable
private fun HomeContent(
  modifier: Modifier = Modifier,
  minHeightAvailable: Boolean = true,
  onTravelGroupItemClick: () -> Unit,
) {
  Column(modifier = modifier) {
    HomeSection(
      modifier =
        Modifier.run {
          if (minHeightAvailable) weight(0.65f) else height(264.dp)
        },
      title = "Travel Places",
    ) {
      var selectedCategory by remember { mutableStateOf(PlaceCategory.Popular) }
      PlaceCategoryTabsRow(
        modifier =
          Modifier
            .intoVertical()
            .fillMaxWidth()
            .padding(
              top = 20.dp,
              bottom = 8.dp,
            ),
        selectedTab = selectedCategory,
        onTabSelect = { selectedCategory = it },
      )
      Spacer(modifier = Modifier.width(8.dp))
      PlacesRow()
    }
    Spacer(modifier = Modifier.height(24.dp))

    HomeSection(
      modifier =
        Modifier.run {
          if (minHeightAvailable) weight(0.35f) else height(150.dp)
        },
      title = "Travel Groups",
    ) {
      GroupsRow(
        onTravelGroupItemClick = onTravelGroupItemClick,
      )
    }
    Spacer(modifier = Modifier.height(24.dp))
  }
}

@Composable
private fun HomeSection(
  modifier: Modifier = Modifier,
  title: String,
  content: @Composable RowScope.() -> Unit,
) {
  Column(modifier = modifier) {
    Row(
      modifier =
        Modifier
          .fillMaxWidth()
          .padding(horizontal = 24.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween,
    ) {
      Text(
        text = title,
        style = MaterialTheme.typography.subtitle1,
      )
      Text(
        modifier = Modifier.alpha(ContentAlpha.disabled),
        text = "Show more >",
        style = MaterialTheme.typography.caption,
      )
    }
    Spacer(modifier = Modifier.height(16.dp))
    Row(
      content = content,
    )
  }
}

enum class PlaceCategory { All, Latest, Popular }

@Composable
private fun PlaceCategoryTabsRow(
  modifier: Modifier,
  selectedTab: PlaceCategory,
  onTabSelect: (PlaceCategory) -> Unit,
) {
  CompositionLocalProvider(LocalContentColor provides MaterialTheme.colors.primaryVariant) {
    ConstraintLayout(modifier = modifier) {
      val (cursor, all, latest, popular) = createRefs()
      createHorizontalChain(all, latest, popular, chainStyle = ChainStyle.Spread)

      var cursorPlacement by remember { mutableStateOf(popular) }
      Divider(
        modifier =
          Modifier
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
        thickness = 4.dp,
      )

      val constraintBlock: ConstrainScope.() -> Unit = { top.linkTo(cursor.bottom) }
      PlaceCategoryTab(
        modifier = Modifier.constrainAs(all, constraintBlock),
        title = "All",
        selected = selectedTab == PlaceCategory.All,
        onClick = {
          onTabSelect(PlaceCategory.All)
          cursorPlacement = all
        },
      )
      PlaceCategoryTab(
        modifier = Modifier.constrainAs(latest, constraintBlock),
        title = "Latest",
        selected = selectedTab == PlaceCategory.Latest,
        onClick = {
          onTabSelect(PlaceCategory.Latest)
          cursorPlacement = latest
        },
      )
      PlaceCategoryTab(
        modifier = Modifier.constrainAs(popular, constraintBlock),
        title = "Popular",
        selected = selectedTab == PlaceCategory.Popular,
        onClick = {
          onTabSelect(PlaceCategory.Popular)
          cursorPlacement = popular
        },
      )
    }
  }
}

@Composable
private fun PlaceCategoryTab(
  modifier: Modifier,
  onClick: () -> Unit,
  selected: Boolean,
  title: String,
) {
  Column(
    modifier =
      modifier
        .selectable(
          selected = selected,
          onClick = onClick,
          interactionSource = remember { MutableInteractionSource() },
          indication = null,
        ).padding(top = 8.dp),
  ) {
    Text(
      modifier = Modifier.align(Alignment.CenterHorizontally),
      text = title,
      style =
        MaterialTheme.typography.button.copy(
          fontFamily = AbrilFatfaceFontFamily,
        ),
      color = if (selected) LocalContentColor.current else MaterialTheme.colors.onBackground,
    )
  }
}

@Composable
private fun PlacesRow(
  modifier: Modifier = Modifier,
  trips: List<Trip> =
    remember {
      List(10) {
        if (it % 2 == 0) {
          Trip(
            id = it,
            destination =
              Place(
                cityName = "City Rome",
                countryName = "Italy",
                imageId = R.drawable.colossians_philemon,
              ),
            days = 5,
            price = 740,
          )
        } else {
          Trip(
            id = it,
            destination =
              Place(
                cityName = "Grand Canton",
                countryName = "USA",
                imageId = R.drawable.antelope_canyon,
              ),
            days = 7,
            price = 240,
          )
        }
      }
    },
) {
  LazyRow(
    modifier = modifier.fillMaxHeight(),
    horizontalArrangement = Arrangement.spacedBy(24.dp),
    contentPadding = PaddingValues(start = 8.dp, end = 24.dp),
  ) {
    items(trips) { trip ->
      PlaceItem(
        modifier =
          Modifier
            .fillParentMaxHeight()
            .aspectRatio(4 / 5f),
        trip = trip,
      )
    }
  }
}

@Composable
private fun PlaceItem(
  modifier: Modifier = Modifier,
  trip: Trip,
) {
  Box(
    modifier =
      modifier
        .shadow(
          color = Color.LightGray.copy(alpha = 0.3f),
          shape = MaterialTheme.shapes.medium,
          radius = 16.dp,
          dx = 8.dp,
          dy = 8.dp,
        ),
  ) {
    Icon(
      modifier =
        Modifier
          .zIndex(1f)
          .align(Alignment.TopEnd)
          .size(28.dp)
          .offset(
            y = (-6).dp,
            x = (-30).dp,
          ),
      painter = painterResource(id = R.drawable.ic_bookmark),
      contentDescription = null,
      tint =
        with(MaterialTheme.colors) {
          if (Random.nextBoolean()) primary else secondary
        },
    )
    Box(
      modifier =
        Modifier
          .clip(MaterialTheme.shapes.medium)
          .background(MaterialTheme.colors.surface),
    ) {
      Column(
        modifier =
          Modifier
            .fillMaxHeight()
            .padding(10.dp),
      ) {
        Box {
          val hazeState = remember { HazeState() }
          Image(
            modifier =
              Modifier
                .aspectRatio(10 / 9f)
                .clip(MaterialTheme.shapes.medium.copy(CornerSize(22.dp)))
                .haze(hazeState),
            painter = painterResource(id = trip.destination.imageId),
            contentDescription = "Place",
            contentScale = ContentScale.Crop,
          )
          Box(
            modifier =
              Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 10.dp, bottom = 12.dp)
                .size(64.dp, 32.dp)
                .clip(RoundedCornerShape(33))
                .border(
                  Dp.Hairline,
                  color = Color(0x50FFFFFF),
                  shape = RoundedCornerShape(33),
                ).hazeChild(
                  state = hazeState,
                  style =
                    HazeDefaults.style(
                      backgroundColor = Color.Transparent,
                      blurRadius = 4.dp,
                    ),
                ).background(
                  Brush.horizontalGradient(
                    0.0f to Color.Transparent,
                    1.0f to Color.White.copy(alpha = 0.25f),
                  ),
                ),
            contentAlignment = Alignment.Center,
          ) {
            Text(
              modifier = Modifier,
              text = stringResource(R.string.price, trip.price),
              style = MaterialTheme.typography.overline,
              color = MaterialTheme.colors.surface,
            )
          }
        }
        Column(
          modifier =
            Modifier
              .padding(horizontal = 12.dp)
              .padding(top = 12.dp),
        ) {
          Text(
            text = trip.destination.cityName,
            style =
              MaterialTheme.typography.caption.copy(
                fontWeight = FontWeight.Bold,
              ),
          )
          Spacer(modifier = Modifier.height(2.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
          ) {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.disabled) {
              Row {
                val height = with(LocalDensity.current) { 10.sp.toDp() }
                Icon(
                  modifier =
                    Modifier
                      .height(height)
                      .align(Alignment.CenterVertically),
                  painter = painterResource(id = R.drawable.ic_location),
                  contentDescription = "Location",
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                  text = trip.destination.countryName,
                  style = MaterialTheme.typography.overline,
                )
              }
              Box(
                modifier =
                  Modifier
                    .clip(RoundedCornerShape(40))
                    .background(MaterialTheme.colors.background),
                contentAlignment = Alignment.Center,
              ) {
                Text(
                  modifier =
                    Modifier
                      .padding(6.dp),
                  text =
                    pluralStringResource(
                      R.plurals.n_day,
                      trip.days,
                      trip.days,
                    ),
                  style = MaterialTheme.typography.overline,
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
private fun GroupsRow(
  modifier: Modifier = Modifier,
  groups: List<Group> =
    remember {
      List(10) {
        Group(
          id = it,
          name = "The Wave Arizona",
          image = if (it % 2 == 0) R.drawable.the_wave else R.drawable.greece,
        )
      }
    },
  onTravelGroupItemClick: () -> Unit,
) {
  LazyRow(
    modifier = modifier.fillMaxHeight(),
    horizontalArrangement = Arrangement.spacedBy(24.dp),
    contentPadding = PaddingValues(horizontal = 24.dp),
  ) {
    items(items = groups) { group ->
      GroupItem(
        modifier =
          Modifier
            .fillParentMaxHeight()
            .wrapContentWidth(),
        group = group,
        onClick = onTravelGroupItemClick,
      )
    }
  }
}

@Composable
private fun GroupItem(
  modifier: Modifier = Modifier,
  group: Group,
  onClick: () -> Unit,
) {
  Box(
    modifier =
      modifier
        .shadow(
          color = Color.LightGray.copy(alpha = 0.3f),
          shape = MaterialTheme.shapes.medium,
          radius = 16.dp,
          dx = 8.dp,
          dy = 8.dp,
        ).clip(shape = MaterialTheme.shapes.medium.copy(CornerSize(28.dp)))
        .clickable(onClick = onClick),
  ) {
    Row(
      modifier =
        Modifier
          .background(
            color = MaterialTheme.colors.surface,
            shape = MaterialTheme.shapes.medium.copy(CornerSize(28.dp)),
          ).padding(14.dp),
      horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
      Image(
        modifier =
          Modifier
            .aspectRatio(4 / 5f)
            .clip(RoundedCornerShape(18.dp)),
        painter = painterResource(id = group.image),
        contentScale = ContentScale.Crop,
        contentDescription = "Location",
      )
      Column(modifier = Modifier.widthIn(min = 124.dp)) {
        Column(modifier = Modifier.weight(0.5f)) {
          Text(
            text = group.name,
            style =
              MaterialTheme.typography.caption.copy(
                fontWeight = FontWeight.Bold,
              ),
          )
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            modifier = Modifier.alpha(ContentAlpha.disabled),
            text = "Group Members",
            style = MaterialTheme.typography.overline,
          )
        }
        OverlappingRow(modifier = Modifier.weight(0.475f)) {
          val profileImageIds =
            listOf(
              R.drawable.person_one,
              R.drawable.person_two,
              R.drawable.person_three,
            )
          profileImageIds.forEach {
            Image(
              modifier =
                Modifier
                  .aspectRatio(1f)
                  .fillMaxSize()
                  .background(
                    color = MaterialTheme.colors.surface,
                    shape = CircleShape,
                  ).padding(2.dp)
                  .clip(CircleShape),
              painter = painterResource(id = it),
              contentDescription = null,
              contentScale = ContentScale.Crop,
            )
          }
          Box(
            modifier =
              Modifier
                .aspectRatio(1f)
                .fillMaxSize()
                .background(
                  color = MaterialTheme.colors.surface,
                  shape = CircleShape,
                ).padding(2.dp)
                .background(
                  color = MaterialTheme.colors.primaryVariant,
                  shape = CircleShape,
                ).clip(CircleShape),
            contentAlignment = Alignment.Center,
          ) {
            Text(
              text = "20+",
              color = Color.White,
              style = MaterialTheme.typography.overline,
            )
          }
        }
      }
    }
  }
}

@Composable
private fun SearchBar(
  modifier: Modifier = Modifier,
  value: String,
  onValueChange: (String) -> Unit,
) {
  val customTextSelectionColors =
    TextSelectionColors(
      handleColor = MaterialTheme.colors.primary,
      backgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.4f),
    )
  CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
    BasicTextField(
      value = value,
      modifier =
        modifier
          .defaultMinSize(
            minWidth = TextFieldDefaults.MinWidth,
            minHeight = TextFieldDefaults.MinHeight,
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
              contentDescription = null,
            )
          },
          trailingIcon = null,
          singleLine = true,
          enabled = true,
          interactionSource = remember { MutableInteractionSource() },
          contentPadding =
            TextFieldDefaults.textFieldWithoutLabelPadding(
              start = 28.dp,
            ),
          shape = MaterialTheme.shapes.small,
          colors =
            TextFieldDefaults.textFieldColors(
              backgroundColor = MaterialTheme.colors.surface,
            ),
        )
      },
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

private fun Modifier.intoVertical() =
  layout { measurable, constraints ->
    val placeable =
      measurable.measure(
        Constraints(
          minHeight = constraints.minWidth,
          maxHeight = constraints.maxWidth,
          minWidth = constraints.minHeight,
          maxWidth = constraints.maxHeight,
        ),
      )
    layout(placeable.height, placeable.width) {
      placeable.placeWithLayer(
        x = -(placeable.width / 2 - placeable.height / 2),
        y = -(placeable.height / 2 - placeable.width / 2),
        layerBlock = { rotationZ = -90f },
      )
    }
  }

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
  QclayTripTheme {
    HomeScreen(
      onTravelGroupItemClick = { },
    )
  }
}
