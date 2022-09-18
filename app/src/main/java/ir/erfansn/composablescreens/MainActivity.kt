package ir.erfansn.composablescreens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import ir.erfansn.composablescreens.ui.navigation.ComposableScreensNavHost
import ir.erfansn.composablescreens.ui.theme.ComposableScreensTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContent {
            ComposableScreensTheme {
                ComposableScreensNavHost()
            }
        }
    }
}
