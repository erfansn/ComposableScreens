@file:OptIn(ExperimentalMaterialApi::class)

package ir.erfansn.composablescreens.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.erfansn.composablescreens.travel.ui.theme.PastelOrange
import ir.erfansn.composablescreens.ui.navigation.routes
import ir.erfansn.composablescreens.ui.theme.ComposableScreensTheme
import java.util.*

@Composable
fun ComposableScreensApp(
    onRouteClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = WindowInsets.safeContent.asPaddingValues()
    ) {
        items(routes) { route ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                onClick = { onRouteClick(route) },
                border = BorderStroke(2.dp, PastelOrange)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(text = route.replaceFirstChar { it.titlecase(Locale.getDefault()) })
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ComposableScreensAppPreview() {
    ComposableScreensTheme {
        ComposableScreensApp(
            onRouteClick = { }
        )
    }
}
