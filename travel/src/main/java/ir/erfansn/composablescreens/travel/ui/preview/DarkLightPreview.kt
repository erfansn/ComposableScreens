package ir.erfansn.composablescreens.travel.ui.preview

import android.content.res.Configuration.*
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Light Mode",
    showBackground = true
)
@Preview(
    name = "Dark Mode",
    uiMode = UI_MODE_NIGHT_YES,
    showBackground = true
)
annotation class DarkLightPreview
