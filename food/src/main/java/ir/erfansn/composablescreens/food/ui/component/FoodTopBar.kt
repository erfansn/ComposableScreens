package ir.erfansn.composablescreens.food.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.erfansn.composablescreens.food.ui.FoodTheme

@Composable
fun FoodTopBar(
    title: @Composable RowScope.() -> Unit,
    action: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier,
    navigation: @Composable RowScope.() -> Unit = { },
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(top = 48.dp)
            .heightIn(min = 116.dp)
            .wrapContentSize()
            .fillMaxWidth()
    ) {
        navigation()
        title()
        action()
    }
}

@Preview
@Composable
private fun FoodTopBarSingleLineTitlePreview() {
    FoodTheme {
        FoodTopBar(
            title = {
                Text(
                    "Food",
                    modifier = Modifier.weight(1f),
                    style = FoodTheme.typography.headlineMedium
                )
            },
            action = {
                IconButton(onClick = { }) {
                    Icon(Icons.Rounded.MoreVert, contentDescription = null)
                }
            },
            navigation = {
                IconButton(onClick = { }, modifier = Modifier.align(Alignment.Top)) {
                    Icon(Icons.Rounded.ArrowBackIosNew, contentDescription = null)
                }
            },
            modifier = Modifier.background(color = Color.White)
        )
    }
}

@Preview
@Composable
private fun FoodTopBarTwoLineTitlePreview() {
    FoodTheme {
        FoodTopBar(
            title = {
                Text(
                    "Food\nModule",
                    modifier = Modifier.weight(1f),
                    style = FoodTheme.typography.headlineMedium
                )
            },
            action = {
                IconButton(onClick = { }) {
                    Icon(Icons.Rounded.MoreVert, contentDescription = null)
                }
            },
            navigation = {
                IconButton(onClick = { }, modifier = Modifier.align(Alignment.Top)) {
                    Icon(Icons.Rounded.ArrowBackIosNew, contentDescription = null)
                }
            },
            modifier = Modifier.background(color = Color.White)
        )
    }
}

@Preview
@Composable
private fun FoodTopBarThreeLinePreview() {
    FoodTheme {
        FoodTopBar(
            title = {
                Text(
                    "Food\nModule\nTopBar",
                    modifier = Modifier.weight(1f),
                    style = FoodTheme.typography.headlineMedium
                )
            },
            action = {
                IconButton(onClick = { }) {
                    Icon(Icons.Rounded.MoreVert, contentDescription = null)
                }
            },
            navigation = {
                IconButton(onClick = { }, modifier = Modifier.align(Alignment.Top)) {
                    Icon(Icons.Rounded.ArrowBackIosNew, contentDescription = null)
                }
            },
            modifier = Modifier.background(color = Color.White)
        )
    }
}
