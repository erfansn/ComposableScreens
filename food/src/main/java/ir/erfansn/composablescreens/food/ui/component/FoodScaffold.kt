package ir.erfansn.composablescreens.food.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import ir.erfansn.composablescreens.food.ui.FoodTheme

@Composable
internal fun FoodScaffold(
    bottomBar: @Composable () -> Unit,
    topBar: @Composable () -> Unit,
    modifier: Modifier = Modifier,
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

@Composable
internal fun FoodFloatingScaffold(
    topBar: @Composable () -> Unit,
    floatingBottomBar: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = topBar,
        bottomBar = {
            // To prevent applying insets padding to bottom side
            Box(Modifier)
        },
        containerColor = FoodTheme.colors.background,
        contentColor = FoodTheme.colors.onBackground
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
        ) {
            var bottomBarHeight by remember { mutableIntStateOf(0) }
            content(PaddingValues(bottom = with(LocalDensity.current) { bottomBarHeight.toDp() }))
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .onSizeChanged {
                        bottomBarHeight = it.height
                    }
            ) {
                floatingBottomBar()
            }
        }
    }
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

@Preview
@Composable
private fun FoodFloatingScaffoldPreview() {
    FoodTheme {
        FoodFloatingScaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                Text(text = "Top Bar")
            },
            floatingBottomBar = {
                Text(text = "Floating bottom Bar")
            }
        ) {
            Box(modifier = Modifier.padding(it)) {
                Text(text = "Content")
            }
        }
    }
}
