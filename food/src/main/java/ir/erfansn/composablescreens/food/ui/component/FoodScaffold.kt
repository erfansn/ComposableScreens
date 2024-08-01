package ir.erfansn.composablescreens.food.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ir.erfansn.composablescreens.food.ui.FoodTheme

@Composable
fun FoodScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = { },
    bottomBar: @Composable () -> Unit = { },
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = topBar,
        bottomBar = bottomBar,
        contentWindowInsets = WindowInsets.safeDrawing,
        containerColor = FoodTheme.colors.background,
        contentColor = FoodTheme.colors.onBackground,
        content = content
    )
}

@Preview
@Composable
private fun FoodScaffoldPreview() {
    FoodTheme {
        FoodScaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                Text(text = "Top Bar")
            },
            bottomBar = {
                Text(text = "Bottom Bar")
            }
        ) {
            Box(modifier = Modifier.padding(it)) {
                Text(text = "Content")
            }
        }
    }
}
