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

package ir.erfansn.composablescreens.food.kristina_cookie.feature.product

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.AddShoppingCart
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.dropUnlessResumed
import ir.erfansn.composablescreens.common.withSafeNavAnimatedContentScope
import ir.erfansn.composablescreens.common.withSafeSharedElementAnimationScopes
import ir.erfansn.composablescreens.food.kristina_cookie.R
import ir.erfansn.composablescreens.food.kristina_cookie.data.Product
import ir.erfansn.composablescreens.food.kristina_cookie.ui.KristinaCookieTheme
import ir.erfansn.composablescreens.food.kristina_cookie.ui.component.KristinaCookieFloatingScaffold
import ir.erfansn.composablescreens.food.kristina_cookie.ui.component.KristinaCookieTopBar
import ir.erfansn.composablescreens.food.kristina_cookie.ui.component.ProductImage
import ir.erfansn.composablescreens.food.kristina_cookie.ui.component.ProductImageDefault
import ir.erfansn.composablescreens.food.kristina_cookie.ui.component.VerticalHillButton
import ir.erfansn.composablescreens.food.kristina_cookie.ui.modifier.overlappedBackgroundColor
import ir.erfansn.composablescreens.food.kristina_cookie.ui.util.Cent
import ir.erfansn.composablescreens.food.kristina_cookie.ui.util.convertToDollars
import ir.erfansn.composablescreens.food.kristina_cookie.ui.util.priceByQuantityText
import ir.erfansn.composablescreens.food.kristina_cookie.ui.util.scaleEffectValue
import ir.erfansn.composablescreens.food.kristina_cookie.ui.util.sharedElementAnimSpec
import kotlin.random.Random

@Composable
internal fun ProductRoute(
  viewModel: ProductViewModel,
  onNavigateToCart: () -> Unit,
  onBackClick: () -> Unit,
  shouldRunNavAnimations: Boolean,
  modifier: Modifier = Modifier,
) {
  ProductScreen(
    product = viewModel.product,
    onNavigateToCart = onNavigateToCart,
    onBackClick = onBackClick,
    modifier = modifier,
    quantity = viewModel.quantity,
    onChangeQuantity = viewModel::changeProductQuantity,
    shouldRunNavAnimations = shouldRunNavAnimations,
  )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun ProductScreen(
  product: Product,
  quantity: Int,
  onChangeQuantity: (value: Int) -> Unit,
  onNavigateToCart: () -> Unit,
  onBackClick: () -> Unit,
  shouldRunNavAnimations: Boolean,
  modifier: Modifier = Modifier,
) {
  val scrollState = rememberScrollState()
  var mutableShouldRunNavAnimations by rememberSaveable { mutableStateOf(shouldRunNavAnimations) }
  KristinaCookieFloatingScaffold(
    modifier = modifier,
    topBar = {
      val isOverlapped by remember { derivedStateOf { scrollState.value > 24 } }
      Box(
        modifier =
          Modifier
            .withSafeSharedElementAnimationScopes {
              renderInSharedTransitionScopeOverlay(zIndexInOverlay = 2f)
                .animateEnterExit(
                  enter = fadeIn(),
                  exit = fadeOut(),
                )
            }.overlappedBackgroundColor(isOverlapped),
      ) {
        val transitionData = updateTransitionData(quantity == 0)
        ProductTopBar(
          productId = product.id,
          title = product.title,
          onBackClick =
            dropUnlessResumed {
              onBackClick()
            },
        ) {
          var addedAsFavorite by remember { mutableStateOf(false) }
          Icon(
            imageVector = if (addedAsFavorite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
            contentDescription = null,
            tint = Color(0xFFF6466B),
            modifier =
              Modifier
                .padding(end = 24.dp)
                .minimumInteractiveComponentSize()
                .size(40.dp)
                .toggleable(
                  value = addedAsFavorite,
                  onValueChange = { addedAsFavorite = !addedAsFavorite },
                  role = Role.Checkbox,
                  interactionSource = remember { MutableInteractionSource() },
                  indication = null,
                ).align(Alignment.Top)
                .graphicsLayer {
                  alpha = transitionData.alpha
                }.withSafeNavAnimatedContentScope {
                  animateEnterExit(
                    enter = scaleIn(animationSpec = sharedElementAnimSpec()),
                    exit = scaleOut(animationSpec = sharedElementAnimSpec()),
                  )
                },
          )
        }

        VerticalHillButton(
          onClick =
            dropUnlessResumed {
              mutableShouldRunNavAnimations = false
              onNavigateToCart()
            },
          title = "Cart",
          modifier =
            Modifier
              .offset {
                IntOffset(x = transitionData.offsetX.roundToPx(), y = 0)
              }.windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Top))
              .padding(top = 28.dp)
              .align(Alignment.TopEnd)
              .withSafeSharedElementAnimationScopes {
                val screenWidth =
                  with(LocalDensity.current) {
                    LocalConfiguration.current.screenWidthDp.dp
                      .roundToPx()
                  }

                if (quantity != 0) {
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
                  this
                }.sharedElement(
                  state = rememberSharedContentState("cart_button"),
                  animatedVisibilityScope = this,
                  zIndexInOverlay = 3f,
                  boundsTransform = { _, _ -> sharedElementAnimSpec() },
                )
              },
        )
      }
    },
    floatingBottomBar = {
      AnimatedContent(
        targetState = quantity,
        label = "bottom_bar",
        contentKey = { it == 0 },
        transitionSpec = {
          slideInVertically(
            initialOffsetY = { it },
            animationSpec = enterAnimationSpec(),
          ) togetherWith
            slideOutVertically(
              targetOffsetY = { it },
              animationSpec = exitAnimationSpec(),
            )
        },
      ) {
        ProductBottomBar(
          onChangeQuantity = { qty -> onChangeQuantity(qty) },
          producePriceInCent = product.priceInCent,
          orderCount = it,
          modifier =
            Modifier
              .wrapContentSize()
              .navigationBarsPadding(),
          shouldRunNavAnimations = mutableShouldRunNavAnimations,
        )
      }
    },
  ) {
    ProductContent(
      productId = product.id,
      imageId = product.imageId,
      backgroundColor = product.backgroundColor,
      priceInCent = product.priceInCent,
      ingredients = product.ingredients,
      description = product.description,
      scrollState = scrollState,
      contentPadding = it,
      shouldRunNavAnimations = mutableShouldRunNavAnimations,
    )
  }
}

private class TransitionData(
  alpha: State<Float>,
  offsetX: State<Dp>,
) {
  val alpha by alpha
  val offsetX by offsetX
}

@Composable
private fun updateTransitionData(equalsZero: Boolean): TransitionData {
  val transition = updateTransition(targetState = equalsZero, label = "action_button")
  val alpha =
    transition.animateFloat(
      label = "alpha",
      transitionSpec = { enterAnimationSpec() },
    ) {
      if (it) 1f else 0f
    }
  val offsetX =
    transition.animateDp(
      label = "offset_x",
      transitionSpec = { enterAnimationSpec() },
    ) {
      if (it) 56.dp else 0.dp
    }
  return remember(transition) { TransitionData(alpha, offsetX) }
}

@Composable
private fun ProductTopBar(
  productId: Int,
  title: String,
  onBackClick: () -> Unit,
  modifier: Modifier = Modifier,
  actionContent: @Composable RowScope.() -> Unit,
) {
  var topPadding by remember { mutableStateOf(0.dp) }
  KristinaCookieTopBar(
    modifier =
      modifier
        .padding(top = topPadding),
    title = {
      Text(
        text = title,
        style = KristinaCookieTheme.typography.displaySmall,
        fontWeight = FontWeight.SemiBold,
        modifier =
          Modifier
            .offset((-16).dp)
            .weight(1f)
            .withSafeSharedElementAnimationScopes {
              sharedElement(
                state = rememberSharedContentState(key = "title_$productId"),
                animatedVisibilityScope = this,
                zIndexInOverlay = 3f,
              )
            },
        onTextLayout = {
          if (it.lineCount > 2) {
            topPadding = 13.5.dp
          }
        },
      )
    },
    action = actionContent,
    navigation = {
      Icon(
        imageVector = Icons.Rounded.ArrowBackIosNew,
        contentDescription = "Back button",
        modifier =
          Modifier
            .padding(start = 24.dp)
            .minimumInteractiveComponentSize()
            .size(32.dp)
            .offset((-16).dp)
            .align(Alignment.Top)
            .clickable(
              interactionSource = remember { MutableInteractionSource() },
              indication = null,
            ) {
              onBackClick()
            },
      )
    },
  )
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalSharedTransitionApi::class)
@Composable
private fun ProductContent(
  productId: Int,
  imageId: Int,
  backgroundColor: Color,
  priceInCent: Cent,
  ingredients: List<String>,
  description: String,
  shouldRunNavAnimations: Boolean,
  modifier: Modifier = Modifier,
  scrollState: ScrollState = rememberScrollState(),
  contentPadding: PaddingValues = PaddingValues(0.dp),
) {
  Column(
    modifier =
      modifier
        .fillMaxSize()
        .verticalScroll(scrollState)
        .padding(contentPadding),
  ) {
    Box(
      modifier =
        Modifier
          .padding(24.dp),
    ) {
      ProductImage(
        image = painterResource(imageId),
        background = ProductImageDefault.productBackground.copy(color = backgroundColor),
        modifier =
          Modifier
            .withSafeSharedElementAnimationScopes {
              sharedBounds(
                sharedContentState = rememberSharedContentState(key = "container_$productId"),
                animatedVisibilityScope = this,
                resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
              )
            },
      )
      Text(
        text =
          priceByQuantityText(
            priceInCent = priceInCent,
            style = KristinaCookieTheme.typography.titleSmall,
          ),
        modifier =
          Modifier
            .offset((12).dp, (12).dp)
            .align(Alignment.BottomEnd)
            .withSafeSharedElementAnimationScopes {
              renderInSharedTransitionScopeOverlay(
                zIndexInOverlay = 1f,
              ).then(
                if (shouldRunNavAnimations) {
                  Modifier.animateEnterExit(
                    enter = scaleIn(animationSpec = sharedElementAnimSpec()),
                    exit = scaleOut(animationSpec = sharedElementAnimSpec()),
                  )
                } else {
                  Modifier
                },
              )
            }.clip(CircleShape)
            .background(KristinaCookieTheme.colors.primary)
            .padding(vertical = 24.dp, horizontal = 20.dp),
        style = KristinaCookieTheme.typography.titleMedium,
      )
    }
    Text(
      text = description,
      style = KristinaCookieTheme.typography.bodyMedium,
      modifier = Modifier.padding(horizontal = 24.dp),
      textAlign = TextAlign.Justify,
    )
    Spacer(modifier = Modifier.height(16.dp))
    FlowRow(
      maxItemsInEachRow = 2,
      modifier =
        Modifier
          .fillMaxWidth()
          .padding(horizontal = 24.dp),
    ) {
      for ((index, ingredient) in ingredients.withIndex()) {
        Box(
          modifier =
            Modifier
              .withSafeNavAnimatedContentScope {
                if (shouldRunNavAnimations) {
                  animateEnterExit(
                    enter =
                      slideInVertically(
                        initialOffsetY = { 40 * index },
                        animationSpec = sharedElementAnimSpec(),
                      ),
                    exit = ExitTransition.None,
                  )
                } else {
                  this
                }
              }.clip(CircleShape)
              .background(KristinaCookieTheme.colors.tertiary)
              .height(86.dp)
              .weight(1f)
              .padding(12.dp),
          contentAlignment = Alignment.Center,
        ) {
          Text(
            text = ingredient,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
          )
        }
      }
    }
    Spacer(modifier = Modifier.height(16.dp))
  }
}

private typealias Quantity = Int

@Composable
private fun ProductBottomBar(
  producePriceInCent: Cent,
  orderCount: Quantity,
  onChangeQuantity: (Quantity) -> Unit,
  shouldRunNavAnimations: Boolean,
  modifier: Modifier = Modifier,
) {
  Box(modifier = modifier) {
    if (orderCount == 0) {
      val interactionSource = remember { MutableInteractionSource() }
      val scaleEffectValue by interactionSource.scaleEffectValue()

      Row(
        modifier =
          Modifier
            .graphicsLayer {
              scaleX = scaleEffectValue
              scaleY = scaleEffectValue
            }.padding(horizontal = 8.dp)
            .padding(bottom = 8.dp)
            .withSafeSharedElementAnimationScopes {
              sharedBounds(
                sharedContentState = rememberSharedContentState(key = "bottom_bar"),
                animatedVisibilityScope = this,
                resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                enter = fadeIn(sharedElementAnimSpec()),
                exit = fadeOut(sharedElementAnimSpec()),
              )
            }.clip(RoundedCornerShape(KristinaCookieTheme.cornerSize.large))
            .background(KristinaCookieTheme.colors.secondary)
            .heightIn(112.dp)
            .fillMaxWidth()
            .clickable(
              indication = ripple(color = Color.White),
              interactionSource = interactionSource,
            ) {
              onChangeQuantity(1)
            }.withSafeNavAnimatedContentScope {
              if (shouldRunNavAnimations) {
                animateEnterExit(
                  enter = scaleIn(),
                  exit = scaleOut(),
                )
              } else {
                this
              }
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
      ) {
        CompositionLocalProvider(LocalContentColor provides KristinaCookieTheme.colors.onSecondary) {
          Icon(imageVector = Icons.Rounded.AddShoppingCart, contentDescription = null)
          Spacer(modifier = Modifier.width(8.dp))
          Text(text = "Add to cart")
        }
      }
    } else {
      Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier =
          Modifier
            .withSafeNavAnimatedContentScope {
              if (shouldRunNavAnimations) {
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
              } else {
                this
              }
            }.padding(horizontal = 8.dp)
            .padding(bottom = 8.dp)
            .clip(RoundedCornerShape(KristinaCookieTheme.cornerSize.large))
            .background(KristinaCookieTheme.colors.primary)
            .heightIn(112.dp)
            .fillMaxWidth(),
      ) {
        SquareBox(
          onClick = {
            onChangeQuantity(orderCount - 1)
          },
        ) {
          Icon(
            imageVector = Icons.Rounded.Remove,
            contentDescription = "Decrease quantity",
            modifier = Modifier.size(48.dp),
          )
        }

        val priceByQuantity =
          buildAnnotatedString {
            append("${orderCount}qty")
            append("\n")
            withStyle(
              style =
                KristinaCookieTheme.typography.titleMedium
                  .toSpanStyle()
                  .copy(color = Color(0xFF5A5350)),
            ) {
              append((orderCount * producePriceInCent).convertToDollars())
            }
          }
        Text(
          text = priceByQuantity,
          style = KristinaCookieTheme.typography.headlineSmall,
          textAlign = TextAlign.Center,
        )
        SquareBox(
          onClick = {
            onChangeQuantity(orderCount + 1)
          },
        ) {
          Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = "Increase quantity",
            modifier = Modifier.size(48.dp),
          )
        }
      }
    }
  }
}

@Composable
private fun SquareBox(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  content: @Composable () -> Unit,
) {
  Box(
    contentAlignment = Alignment.Center,
    modifier =
      modifier
        .size(112.dp)
        .padding(4.dp)
        .clip(RoundedCornerShape(KristinaCookieTheme.cornerSize.large - 4.dp))
        .background(KristinaCookieTheme.colors.background)
        .clickable {
          onClick()
        },
  ) {
    CompositionLocalProvider(LocalContentColor provides KristinaCookieTheme.colors.onBackground) {
      content()
    }
  }
}

private fun <T> enterAnimationSpec() = tween<T>(durationMillis = 800)

private fun <T> exitAnimationSpec() = tween<T>(durationMillis = 500)

private val sampleProduct =
  Product(
    id = 0,
    title = "Bitter choco & cream\n1",
    imageId = R.drawable.chocolate_peanut_butter_stuffed_cookie,
    backgroundColor = Color(0xFFE4F9CD),
    priceInCent = 600,
    ingredients =
      listOf(
        "Organic Flour",
        "Organic Cane Sugar",
        "Organic Peanut Butter",
        "Organic Graham Crackers",
        "Non Gmo Baking Powder",
      ),
    description =
      "You can always eat white, even after Labor Day. Our wholesome yet slightly wicked white chocolate " +
        "macadamia cookie is the perfectly alluring antidote to quell your hunger. Reliably chewy, these soft " +
        "white chocolate macadamia nut cookies are the  stuff. Made with deceptively sweet white chocolate chips and humungo" +
        " macadamia nuts, these are the best white chocolate macadamia nut cookie on the scene. Just one bite, and you’ll be licking " +
        "the crumbs off the plate.",
  )

@Preview
@Composable
private fun ProductScreenPreview() {
  KristinaCookieTheme {
    var quntity by remember { mutableIntStateOf(0) }
    ProductScreen(
      product = sampleProduct,
      quantity = quntity,
      onChangeQuantity = { quntity = it },
      onNavigateToCart = { },
      onBackClick = { },
      shouldRunNavAnimations = Random.nextBoolean(),
    )
  }
}
