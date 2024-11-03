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

package ir.erfansn.composablescreens.common

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier

val LocalNavAnimatedContentScope = staticCompositionLocalOf<AnimatedContentScope?> { null }

@Suppress("ktlint:standard:class-naming")
class Modifier_AnimatedVisibilityScope(
  modifier: Modifier,
  animatedContentScope: AnimatedContentScope,
) : Modifier by modifier,
  AnimatedVisibilityScope by animatedContentScope

@Composable
fun Modifier.withSafeNavAnimatedContentScope(block: @Composable Modifier_AnimatedVisibilityScope.() -> Modifier): Modifier =
  then(
    with(LocalNavAnimatedContentScope.current) {
      if (this != null) {
        Modifier_AnimatedVisibilityScope(Modifier, this).block()
      } else {
        Modifier
      }
    },
  )
