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

package ir.erfansn.composablescreens.food.kristina_cookie.feature.home

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddChart
import androidx.compose.material.icons.rounded.Hub
import androidx.compose.material.icons.rounded.Storefront
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextMotion
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.erfansn.composablescreens.food.kristina_cookie.R
import ir.erfansn.composablescreens.food.kristina_cookie.ui.KristinaCookieTheme

internal val collections =
  listOf(
    Collection(
      name = "All",
      icon = Icon.None,
    ),
    Collection(
      name = "Chocolate",
      icon = Icon.Resource(R.drawable.chocolate_48),
    ),
    Collection(
      name = "Peanut",
      icon = Icon.Resource(R.drawable.peanut_outline),
    ),
    Collection(
      name = "Nuts",
      icon = Icon.Vector(Icons.Rounded.Storefront),
    ),
  )

@Immutable
internal data class Collection(
  val name: String,
  val icon: Icon,
)

internal sealed interface Icon {
  data class Vector(
    val vector: ImageVector,
  ) : Icon

  data class Resource(
    @DrawableRes val id: Int,
  ) : Icon

  data object None : Icon
}

@Composable
internal fun Icon.toPainterOrNull(): Painter? =
  when (this) {
    is Icon.Resource -> painterResource(id = id)
    is Icon.Vector -> rememberVectorPainter(image = vector)
    Icon.None -> null
  }

internal enum class Direction(
  val sign: Int,
) {
  Left(-1),
  Right(1),
}

@Composable
internal fun CollectionItem(
  name: String,
  selected: Boolean,
  onClick: () -> Unit,
  animateDirection: Direction,
  modifier: Modifier = Modifier,
  icon: Painter? = null,
) {
  AnimatedContent(
    targetState = selected,
    label = "content",
    transitionSpec = {
      (EnterTransition.None togetherWith ExitTransition.None).using(
        SizeTransform(
          sizeAnimationSpec = { _, _ ->
            animationSpec()
          },
        ),
      )
    },
    modifier =
      modifier
        .clip(CircleShape)
        .background(KristinaCookieTheme.colors.tertiary)
        .clickable(role = Role.RadioButton) { onClick() },
    contentAlignment = Alignment.CenterStart,
  ) { extended ->
    val contentAlpha by transition.animateFloat(
      label = "content_alpha",
      transitionSpec = { animationSpec() },
    ) {
      when (it) {
        EnterExitState.PreEnter -> 0f
        EnterExitState.Visible -> 1f
        EnterExitState.PostExit -> 0f
      }
    }
    if (extended) {
      ExpandedCollectionItemContent(
        name = name,
        icon = icon,
        modifier =
          Modifier
            .animateEnterExit(
              enter =
                slideInHorizontally(
                  initialOffsetX = {
                    -animateDirection.sign * it
                  },
                  animationSpec = animationSpec(),
                ),
              exit =
                slideOutHorizontally(
                  targetOffsetX = {
                    animateDirection.sign * it
                  },
                  animationSpec = animationSpec(),
                ),
            ),
        contentAlpha = { contentAlpha },
      )
    } else {
      CollectionItemContent(
        name = name,
        icon = icon,
        contentAlpha = { contentAlpha },
      )
    }
  }
}

private fun <T> animationSpec() = tween<T>(600)

@Composable
private fun ExpandedCollectionItemContent(
  name: String,
  icon: Painter?,
  modifier: Modifier = Modifier,
  contentAlpha: () -> Float = { 1f },
) {
  Box(
    modifier =
      modifier
        .clip(CircleShape)
        .background(KristinaCookieTheme.colors.secondary)
        .contentPadding(),
    contentAlignment = Alignment.Center,
  ) {
    Row(
      modifier =
        Modifier
          .graphicsLayer {
            alpha = contentAlpha()
          },
    ) {
      if (icon == null) {
        Text(
          text = name,
          style = KristinaCookieTheme.typography.labelLarge,
          color = KristinaCookieTheme.colors.onSecondary,
        )
      } else {
        Icon(
          painter = icon,
          contentDescription = null,
          modifier = Modifier.size(20.dp),
          tint = KristinaCookieTheme.colors.primary,
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = name,
          style = KristinaCookieTheme.typography.labelLarge,
          color = KristinaCookieTheme.colors.onSecondary,
        )
      }
    }
  }
}

@Composable
private fun CollectionItemContent(
  icon: Painter?,
  name: String,
  modifier: Modifier = Modifier,
  contentAlpha: () -> Float = { 1f },
) {
  Box(
    modifier =
      modifier
        .contentPadding(),
  ) {
    val contentModifier =
      Modifier.graphicsLayer {
        alpha = contentAlpha()
      }
    if (icon == null) {
      Text(
        text = name,
        style =
          KristinaCookieTheme.typography.labelLarge.copy(
            textMotion = TextMotion.Static,
          ),
        color = KristinaCookieTheme.colors.onTertiary,
        modifier = contentModifier,
      )
    } else {
      Icon(
        painter = icon,
        contentDescription = null,
        modifier =
          contentModifier
            .size(20.dp),
        tint = KristinaCookieTheme.colors.onTertiary,
      )
    }
  }
}

private fun Modifier.contentPadding(): Modifier =
  padding(
    horizontal = 26.dp,
    vertical = 20.dp,
  )

@Preview
@Composable
private fun CollectionItemWithIconPreview() {
  KristinaCookieTheme {
    CollectionItem(
      selected = false,
      onClick = { },
      name = "Chocolate",
      icon = rememberVectorPainter(image = Icons.Rounded.AddChart),
      animateDirection = remember { Direction.entries.random() },
    )
  }
}

@Preview
@Composable
private fun CollectionItemWithIconSelectedPreview() {
  KristinaCookieTheme {
    CollectionItem(
      selected = true,
      onClick = { },
      name = "Chocolate",
      icon = rememberVectorPainter(image = Icons.Rounded.AddChart),
      animateDirection = remember { Direction.entries.random() },
    )
  }
}

@Preview
@Composable
private fun CollectionItemPreview() {
  KristinaCookieTheme {
    CollectionItem(
      selected = false,
      onClick = { },
      name = "All",
      animateDirection = remember { Direction.entries.random() },
    )
  }
}

@Preview
@Composable
private fun CollectionItemSelectedPreview() {
  KristinaCookieTheme {
    CollectionItem(
      selected = true,
      onClick = { },
      name = "All",
      animateDirection = remember { Direction.entries.random() },
    )
  }
}

@Preview
@Composable
private fun CollectionItemInteractivePreview() {
  KristinaCookieTheme {
    var selected by remember { mutableStateOf(false) }
    CollectionItem(
      selected = selected,
      onClick = { selected = !selected },
      name = "Chocolate",
      icon = rememberVectorPainter(image = Icons.Rounded.Hub),
      animateDirection = remember { Direction.entries.random() },
    )
  }
}
