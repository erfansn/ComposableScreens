package ir.erfansn.composablescreens

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ir.erfansn.composablescreens.ui.navigation.ComposableScreensNavHost
import ir.erfansn.composablescreens.ui.theme.ComposableScreensTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val transparentBarStyle = SystemBarStyle.auto(
            lightScrim = Color.TRANSPARENT,
            darkScrim = Color.TRANSPARENT,
        )
        enableEdgeToEdge(
            statusBarStyle = transparentBarStyle,
            navigationBarStyle = transparentBarStyle,
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }
        super.onCreate(savedInstanceState)
        setContent {
            ComposableScreensTheme {
                ComposableScreensNavHost()
            }
        }
    }
}
