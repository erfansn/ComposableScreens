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

@file:OptIn(ExperimentalSharedTransitionApi::class)

package ir.erfansn.composablescreens.food.kristina_cookie.feature.home

import android.util.Log
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.zIndex
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.dropUnlessResumed
import ir.erfansn.composablescreens.common.withSafeNavAnimatedContentScope
import ir.erfansn.composablescreens.common.withSafeSharedElementAnimationScopes
import ir.erfansn.composablescreens.food.kristina_cookie.ui.KristinaCookieTheme
import ir.erfansn.composablescreens.food.kristina_cookie.ui.component.KristinaCookieScaffold
import ir.erfansn.composablescreens.food.kristina_cookie.ui.component.KristinaCookieTopBar
import ir.erfansn.composablescreens.food.kristina_cookie.ui.component.VerticalHillButton
import ir.erfansn.composablescreens.food.kristina_cookie.ui.util.sharedElementAnimSpec
import kotlin.math.sign

@Composable
internal fun HomeRoute(
  viewModel: HomeViewModel,
  onNavigateToProduct: (id: Int) -> Unit,
  onNavigateToCart: () -> Unit,
  modifier: Modifier = Modifier,
) {
  HomeScreen(
    onNavigateToCart = onNavigateToCart,
    onNavigateToProduct = onNavigateToProduct,
    modifier = modifier,
    vitrineItems = viewModel.vitrineItems,
  )
}

@Composable
private fun HomeScreen(
  onNavigateToCart: () -> Unit,
  onNavigateToProduct: (id: Int) -> Unit,
  vitrineItems: List<VitrineItem>,
  modifier: Modifier = Modifier,
) {
  var shouldApplyFullWidthAnimationToCartButton by rememberSaveable {
    mutableStateOf(false)
  }
  LaunchedEffect(shouldApplyFullWidthAnimationToCartButton) {
    Log.d("HomeScreen", "Should animate full width offset to cart button: $shouldApplyFullWidthAnimationToCartButton")
  }
  LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
    shouldApplyFullWidthAnimationToCartButton = false
  }

  KristinaCookieScaffold(
    topBar = {
      HomeTopBar(
        onNavigateToCart = {
          shouldApplyFullWidthAnimationToCartButton = true
          onNavigateToCart()
        },
        shouldApplyOffScreenAnimationToCartButton = shouldApplyFullWidthAnimationToCartButton,
      )
    },
    bottomBar = {
      HomeNavigationBar()
    },
    modifier = modifier,
  ) {
    HomeContent(
      // There is no need to consumeWindowInsets because top/bottom Bar do itself
      modifier =
        Modifier
          .padding(it),
      onNavigateToProduct = { id ->
        shouldApplyFullWidthAnimationToCartButton = true
        onNavigateToProduct(id)
      },
      vitrineItems = vitrineItems,
    )
  }
}

@Composable
private fun HomeTopBar(
  onNavigateToCart: () -> Unit,
  shouldApplyOffScreenAnimationToCartButton: Boolean,
  modifier: Modifier = Modifier,
) {
  Column(modifier = modifier.fillMaxWidth()) {
    KristinaCookieTopBar(
      title = {
        val titleTextStyle = KristinaCookieTheme.typography.displaySmall
        val title =
          buildAnnotatedString {
            append("Choose ")
            withStyle(
              titleTextStyle.copy(fontWeight = FontWeight.Bold).toSpanStyle(),
            ) {
              append("cookies\n")
            }
            append("for your tea party")
          }
        Text(
          text = title,
          style = titleTextStyle,
          maxLines = 2,
          modifier =
            Modifier
              .padding(start = 24.dp)
              .withSafeNavAnimatedContentScope {
                Modifier.animateEnterExit(
                  enter = fadeIn(animationSpec = sharedElementAnimSpec()),
                  exit = fadeOut(animationSpec = sharedElementAnimSpec()),
                )
              },
        )
      },
      action = {
        VerticalHillButton(
          title = "Cart",
          onClick =
            dropUnlessResumed {
              onNavigateToCart()
            },
          modifier =
            Modifier.withSafeSharedElementAnimationScopes {
              if (shouldApplyOffScreenAnimationToCartButton) {
                val screenWidth =
                  with(LocalDensity.current) {
                    LocalConfiguration.current.screenWidthDp.dp
                      .roundToPx()
                  }

                animateEnterExit(
                  enter =
                    slideInHorizontally(
                      initialOffsetX = { -screenWidth },
                      animationSpec = sharedElementAnimSpec(),
                    ),
                  exit =
                    slideOutHorizontally(
                      targetOffsetX = { -screenWidth },
                      animationSpec = sharedElementAnimSpec(),
                    ),
                )
              } else {
                animateEnterExit(
                  enter =
                    slideInHorizontally(
                      initialOffsetX = { it },
                      animationSpec = sharedElementAnimSpec(),
                    ),
                  exit =
                    slideOutHorizontally(
                      targetOffsetX = { it },
                      animationSpec = sharedElementAnimSpec(),
                    ),
                )
              }.sharedElement(
                state = rememberSharedContentState("cart_button"),
                animatedVisibilityScope = this,
                zIndexInOverlay = 2f,
                boundsTransform = { _, _ -> sharedElementAnimSpec() },
              )
            },
        )
      },
    )
    Spacer(modifier = Modifier.height(20.dp))

    Row(
      modifier =
        Modifier
          .fillMaxWidth()
          .horizontalScroll(state = rememberScrollState())
          .withSafeNavAnimatedContentScope {
            animateEnterExit(
              enter =
                slideInHorizontally(
                  initialOffsetX = { it },
                  animationSpec = sharedElementAnimSpec(),
                ),
              exit =
                slideOutHorizontally(
                  targetOffsetX = { it },
                  animationSpec = sharedElementAnimSpec(),
                ),
            )
          }.padding(horizontal = 24.dp),
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
            animateDirection = animateDirection,
          )
        }
      }
    }
    Spacer(modifier = Modifier.height(16.dp))

    Row(
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically,
      modifier =
        Modifier
          .padding(horizontal = 24.dp)
          .fillMaxWidth()
          .withSafeNavAnimatedContentScope {
            animateEnterExit(
              enter = fadeIn(animationSpec = sharedElementAnimSpec()),
              exit = fadeOut(animationSpec = sharedElementAnimSpec()),
            )
          },
    ) {
      IconButton(
        onClick = { },
      ) {
        Icon(imageVector = Icons.Rounded.DesktopWindows, contentDescription = "Account")
      }
      Box {
        var openPopup by remember { mutableStateOf(false) }
        var filterTextHeight by remember { mutableIntStateOf(0) }
        Row(
          modifier =
            Modifier
              .clickable { openPopup = true }
              .onSizeChanged {
                filterTextHeight = it.height
              },
        ) {
          Text(text = "Popular")
          Icon(imageVector = Icons.Rounded.KeyboardArrowDown, contentDescription = null)
        }
        if (openPopup) {
          Popup(
            onDismissRequest = {
              openPopup = false
            },
            offset = IntOffset(0, filterTextHeight),
          ) {
            Text(text = "Nothing!")
          }
        }
      }
      IconButton(onClick = { }) {
        Icon(imageVector = Icons.Rounded.Settings, contentDescription = "Settings")
      }
    }
  }
}

@Composable
private fun HomeContent(
  vitrineItems: List<VitrineItem>,
  onNavigateToProduct: (id: Int) -> Unit,
  modifier: Modifier = Modifier,
) {
  val pagerState = rememberPagerState(pageCount = { vitrineItems.size })

  val initOffset =
    rememberSaveable(
      saver =
        Saver(
          save = { it.value },
          restore = { Animatable(initialValue = it, typeConverter = Float.VectorConverter) },
        ),
    ) {
      Animatable(2f)
    }
  LaunchedEffect(initOffset) {
    Log.d("HomeScreen", "InitOffset: ${initOffset.value}")
  }
  LaunchedEffect(Unit) {
    initOffset.animateTo(0f, animationSpec = sharedElementAnimSpec())
  }

  HorizontalPager(
    state = pagerState,
    contentPadding =
      PaddingValues(
        horizontal = 24.dp,
        vertical = 16.dp,
      ),
    key = { vitrineItems[it].id },
    beyondViewportPageCount = 1,
    modifier =
      modifier
        .fillMaxSize(),
  ) { page ->
    VitrineItemCard(
      vitrineItem = vitrineItems[page],
      onClick =
        dropUnlessResumed {
          if (page != pagerState.currentPage) return@dropUnlessResumed

          onNavigateToProduct(vitrineItems[page].id)
        },
      modifier =
        Modifier
          .withSafeSharedElementAnimationScopes {
            if (pagerState.currentPage == page) {
              sharedBounds(
                sharedContentState = rememberSharedContentState(key = "container_${vitrineItems[page].id}"),
                animatedVisibilityScope = this,
                resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                zIndexInOverlay = 1f,
                enter = fadeIn(animationSpec = sharedElementAnimSpec()),
                exit = fadeOut(animationSpec = sharedElementAnimSpec()),
              )
            } else {
              renderInSharedTransitionScopeOverlay(
                zIndexInOverlay = if (page - pagerState.currentPage > 0) 0f else 2f,
              )
            }
          }.graphicsLayer {
            val fromCurrentPageOffset =
              pagerState
                .getOffsetDistanceInPages(page)

            rotationZ = -12 * fromCurrentPageOffset.coerceIn(-1f, 1f)
          }.zIndex(vitrineItems.size - page.toFloat())
          .then(
            if (LocalInspectionMode.current) {
              Modifier
            } else {
              Modifier.graphicsLayer {
                rotationZ = -12 * initOffset.value.coerceIn(0f, 1f)
                translationX = initOffset.value * pagerState.layoutInfo.pageSize
              }
            },
          ).withSafeNavAnimatedContentScope {
            if (pagerState.currentPage == page) {
              animateEnterExit(
                enter = EnterTransition.None,
                exit = fadeOut(animationSpec = sharedElementAnimSpec()),
              )
            } else {
              val offsetX: (fullWidth: Int) -> Int = {
                (page - pagerState.currentPage).sign * (it / 4)
              }
              animateEnterExit(
                enter =
                  slideInHorizontally(
                    initialOffsetX = offsetX,
                    animationSpec = sharedElementAnimSpec(),
                  ),
                exit =
                  slideOutHorizontally(
                    targetOffsetX = offsetX,
                    animationSpec = sharedElementAnimSpec(),
                  ),
              )
            }
          },
    )
  }
}

private val navItemsIcons =
  listOf(
    Icons.Rounded.Window,
    Icons.Rounded.CardGiftcard,
    Icons.Rounded.FavoriteBorder,
    Icons.Rounded.PersonOutline,
  )

@Composable
private fun HomeNavigationBar() {
  var navigationBarItemIndex by remember { mutableIntStateOf(0) }
  NavigationBar(
    modifier =
      Modifier
        .withSafeNavAnimatedContentScope {
          animateEnterExit(
            enter =
              slideInVertically(
                initialOffsetY = { it },
                animationSpec = sharedElementAnimSpec(),
              ),
            exit =
              slideOutVertically(
                targetOffsetY = { it },
                animationSpec = sharedElementAnimSpec(),
              ),
          )
        }.withSafeSharedElementAnimationScopes {
          sharedBounds(
            sharedContentState = rememberSharedContentState(key = "bottom_bar"),
            animatedVisibilityScope = this,
            resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
            enter = fadeIn(animationSpec = sharedElementAnimSpec()),
            exit = fadeOut(animationSpec = sharedElementAnimSpec()),
          )
        }.clip(RoundedCornerShape(topStart = KristinaCookieTheme.cornerSize.large, topEnd = KristinaCookieTheme.cornerSize.large)),
  ) {
    for ((index, icon) in navItemsIcons.withIndex()) {
      NavigationBarItem(
        selected = index == navigationBarItemIndex,
        onClick = {
          navigationBarItemIndex = index
        },
        icon = icon,
        modifier =
          Modifier.withSafeNavAnimatedContentScope {
            animateEnterExit(
              enter = scaleIn(animationSpec = sharedElementAnimSpec()),
              exit = scaleOut(animationSpec = sharedElementAnimSpec()),
            )
          },
      )
    }
  }
}

@Composable
private fun NavigationBar(
  modifier: Modifier = Modifier,
  tonalElevation: Dp = NavigationBarDefaults.Elevation,
  windowInsets: WindowInsets = NavigationBarDefaults.windowInsets,
  content: @Composable RowScope.() -> Unit,
) {
  Surface(
    color = KristinaCookieTheme.colors.secondary,
    contentColor = KristinaCookieTheme.colors.onSecondary,
    tonalElevation = tonalElevation,
    modifier = modifier,
  ) {
    Row(
      modifier =
        Modifier
          .fillMaxWidth()
          .windowInsetsPadding(windowInsets)
          .defaultMinSize(minHeight = 118.dp)
          .selectableGroup()
          .padding(horizontal = 32.dp),
      horizontalArrangement = Arrangement.SpaceAround,
      verticalAlignment = Alignment.CenterVertically,
      content = content,
    )
  }
}

@Composable
private fun NavigationBarItem(
  selected: Boolean,
  onClick: () -> Unit,
  icon: ImageVector,
  modifier: Modifier = Modifier,
) {
  Box(
    contentAlignment = Alignment.Center,
    modifier =
      modifier
        .clip(CircleShape)
        .clickable(onClick = onClick)
        .background(if (selected) KristinaCookieTheme.colors.primary else Color.Unspecified)
        .padding(
          vertical = 12.dp,
          horizontal = 2.dp,
        ),
  ) {
    Icon(
      imageVector = icon,
      contentDescription = null,
      tint = if (selected) KristinaCookieTheme.colors.onPrimary else KristinaCookieTheme.colors.onSecondary,
      modifier = Modifier.minimumInteractiveComponentSize(),
    )
  }
}

@Preview
@Composable
private fun HomeScreenPreview() {
  KristinaCookieTheme {
    HomeScreen(
      onNavigateToCart = { },
      onNavigateToProduct = { },
      vitrineItems = sampleVitrineItems,
    )
  }
}
