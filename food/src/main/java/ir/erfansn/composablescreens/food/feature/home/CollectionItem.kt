package ir.erfansn.composablescreens.food.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.erfansn.composablescreens.food.ui.FoodTheme

val collections = listOf(
    Collection(
        name = "All",
        icon = Icons.Default.Menu
    ),
    Collection(
        name = "Cookie",
        icon = Icons.Default.Face
    ),
    Collection(
        name = "Brownie",
        icon = Icons.Default.Favorite
    ),
    Collection(
        name = "Cookie Cakes",
        icon = Icons.Default.Share
    ),
    Collection(
        name = "Drinks",
        icon = Icons.Default.Home
    )
)

@Immutable
data class Collection(val name: String, val icon: ImageVector)

@Composable
fun CollectionItem(
    collection: Collection,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(CircleShape)
            .background(
                if (selected) {
                    MaterialTheme.colorScheme.inverseSurface
                } else {
                    MaterialTheme.colorScheme.surfaceDim
                }
            )
            .clickable(
                role = Role.RadioButton,
            ) {
                onClick()
            }
            .padding(
                horizontal = 26.dp,
                vertical = 20.dp
            ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = collection.icon,
            contentDescription = null,
            tint = if (selected) {
                Color.Yellow
            } else {
                MaterialTheme.colorScheme.onSurface
            },
            modifier = Modifier.size(20.dp)
        )
        if (selected) {
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = collection.name,
                style = MaterialTheme.typography.labelLarge.copy(
                    color = MaterialTheme.colorScheme.inverseOnSurface
                )
            )
        }
    }
}

@Preview
@Composable
private fun CollectionItemPreview() {
    FoodTheme {
        Row {
            CollectionItem(
                selected = false,
                onClick = { },
                collection = Collection(name = "Chocolate", icon = Icons.Default.Face)
            )
        }
    }
}

@Preview
@Composable
private fun CollectionItemSelectedPreview() {
    FoodTheme {
        Row {
            CollectionItem(
                collection = Collection(name = "Chocolate", icon = Icons.Default.Face),
                selected = true,
                onClick = { },
            )
        }
    }
}
