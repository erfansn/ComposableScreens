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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.LifecycleResumeEffect

enum class BarStyle { Light, Dark, Auto }

private fun interface SystemBarStyleChanger {
  fun changeTo(style: BarStyle)
}

private val LocalSystemBarStyleChanger = staticCompositionLocalOf { SystemBarStyleChanger { } }
private val LocalBarStyle = compositionLocalOf<BarStyle?> { null }

@Composable
fun ProvideSystemBarStyleChanger(
  onStyleChange: (BarStyle) -> Unit,
  content: @Composable () -> Unit,
) {
  CompositionLocalProvider(LocalSystemBarStyleChanger provides onStyleChange) {
    content()
  }
}

@Composable
fun ProvideSystemBarStyle(
  style: BarStyle,
  content: @Composable () -> Unit,
) {
  val systemBarStyleChanger = LocalSystemBarStyleChanger.current
  LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
    systemBarStyleChanger.changeTo(style)
  }

  CompositionLocalProvider(LocalBarStyle provides style) {
    content()
  }
}

@Composable
fun TemporarySystemBarStyleEffect(style: BarStyle) {
  val systemBarStyleChanger = LocalSystemBarStyleChanger.current
  val appSystemBarStyle =
    requireNotNull(LocalBarStyle.current) {
      "First I must know about default system bar style. Please provide it with `ProvideDefaultSystemBarStyle`"
    }

  LifecycleResumeEffect(style) {
    systemBarStyleChanger.changeTo(style)
    onPauseOrDispose {
      systemBarStyleChanger.changeTo(appSystemBarStyle)
    }
  }
}
