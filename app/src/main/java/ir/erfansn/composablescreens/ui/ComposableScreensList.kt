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

package ir.erfansn.composablescreens.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.erfansn.composablescreens.auto_nav_graph_wiring.autoWiredGraphsCategoryToNameAndRouteList
import ir.erfansn.composablescreens.ui.theme.ComposableScreensTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ComposableScreensList(onRouteClick: (Any) -> Unit) {
  Surface(
    color = MaterialTheme.colors.background,
  ) {
    LazyColumn(
      modifier =
        Modifier
          .fillMaxSize(),
      verticalArrangement = Arrangement.spacedBy(8.dp),
      contentPadding = WindowInsets.safeDrawing.asPaddingValues(),
    ) {
      autoWiredGraphsCategoryToNameAndRouteList.forEach { (group, nameAndRouteList) ->
        stickyHeader {
          Text(
            text = group,
            style = MaterialTheme.typography.h5,
            modifier =
              Modifier
                .padding(top = 8.dp)
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
          )
        }

        items(nameAndRouteList) { (name, route) ->
          Card(
            modifier =
              Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 16.dp),
            onClick = { onRouteClick(route) },
            border = BorderStroke(2.dp, Color.LightGray),
          ) {
            Box(contentAlignment = Alignment.Center) {
              Text(text = name)
            }
          }
        }
      }
    }
  }
}

@Preview(
  showBackground = true,
  showSystemUi = true,
  device = "spec:width=1080px,height=2424px,cutout=punch_hole",
)
@Composable
fun ComposableScreensListPreview() {
  ComposableScreensTheme {
    ComposableScreensList(
      onRouteClick = { },
    )
  }
}
