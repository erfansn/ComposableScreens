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

package ir.erfansn.composablescreens.common

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Suppress("ktlint:standard:class-naming")
class Modifier_NavAnimatedScope_SharedTransitionScope(
  modifier: Modifier,
  navAnimatedScope: AnimatedVisibilityScope,
  sharedTransitionScope: SharedTransitionScope,
) : Modifier by modifier,
  AnimatedVisibilityScope by navAnimatedScope,
  SharedTransitionScope by sharedTransitionScope

@Composable
fun Modifier.withSafeSharedElementAnimationScopes(
  block: @Composable Modifier_NavAnimatedScope_SharedTransitionScope.() -> Modifier,
): Modifier =
  withSafeSharedTransitionScope {
    withSafeNavAnimatedContentScope {
      Modifier_NavAnimatedScope_SharedTransitionScope(
        Modifier,
        this@withSafeNavAnimatedContentScope,
        this@withSafeSharedTransitionScope,
      ).block()
    }
  }
