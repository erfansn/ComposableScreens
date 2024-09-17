@file:OptIn(ExperimentalMaterialApi::class)

package ir.erfansn.composablescreens.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
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
import ir.erfansn.composablescreens.ui.theme.ComposableScreensTheme

private typealias NameRoutePair = Pair<String, String>

val routes: List<NameRoutePair> = listOf(
    "Travel" to "travel",
    "Food" to "food"
)

@Composable
fun ComposableScreensList(
    onRouteClick: (String) -> Unit
) {
    Surface(color = MaterialTheme.colors.background) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = WindowInsets.safeDrawing.add(WindowInsets(top = 16.dp))
                .asPaddingValues()
        ) {
            items(routes) { (name, route) ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    onClick = { onRouteClick(route) },
                    border = BorderStroke(2.dp, Color.LightGray)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(text = name)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ComposableScreensListPreview() {
    ComposableScreensTheme {
        ComposableScreensList(
            onRouteClick = { }
        )
    }
}
