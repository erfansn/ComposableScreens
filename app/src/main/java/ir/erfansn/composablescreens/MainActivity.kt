package ir.erfansn.composablescreens

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ir.erfansn.composablescreens.auto_nav_graph_wiring.autoAggregatedNavGraphs
import ir.erfansn.composablescreens.auto_nav_graph_wiring.autoWiredGraphLandscapeOrientationRoutes
import ir.erfansn.composablescreens.auto_nav_graph_wiring.autoWiredGraphPortraitOrientationRoutes
import ir.erfansn.composablescreens.common.BarStyle
import ir.erfansn.composablescreens.common.LocalSharedTransitionScope
import ir.erfansn.composablescreens.common.ProvideSystemBarStyleChanger
import ir.erfansn.composablescreens.ui.ComposableScreensList
import ir.erfansn.composablescreens.ui.theme.ComposableScreensTheme

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            ProvideSystemBarStyleChanger(
                onStyleChange = {
                    when (it) {
                        BarStyle.Light -> {
                            enableEdgeToEdge(
                                style = SystemBarStyle.light(
                                    Color.TRANSPARENT,
                                    Color.TRANSPARENT
                                )
                            )
                        }

                        BarStyle.Dark -> {
                            enableEdgeToEdge(
                                style = SystemBarStyle.dark(
                                    Color.TRANSPARENT
                                )
                            )
                        }

                        BarStyle.Auto -> {
                            enableEdgeToEdge()
                        }
                    }
                }
            ) {
                SharedTransitionLayout {
                    CompositionLocalProvider(LocalSharedTransitionScope provides this) {
                        val navController = rememberNavController()
                        NavHost(
                            navController = navController,
                            startDestination = "screens_list"
                        ) {
                            composable("screens_list") {
                                ComposableScreensTheme {
                                    ComposableScreensList(
                                        onRouteClick = navController::navigate
                                    )
                                }
                            }
                            autoAggregatedNavGraphs(navController)
                        }

                        ScreenOrientationEffect(navController)
                    }
                }
            }
        }
    }

    @Composable
    private fun ScreenOrientationEffect(navController: NavHostController) {
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        DisposableEffect(currentBackStackEntry) {
            fun NavDestination.hasAnyRoute(routes: List<Any>) = routes.any { hasRoute(it::class) }

            val currentDestinationHierarchy = currentBackStackEntry?.destination?.hierarchy
            requestedOrientation = when {
                currentDestinationHierarchy?.any {
                    it.hasAnyRoute(autoWiredGraphPortraitOrientationRoutes)
                } == true -> {
                    ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                }

                currentDestinationHierarchy?.any {
                    it.hasAnyRoute(autoWiredGraphLandscapeOrientationRoutes)
                } == true -> {
                    ActivityInfo.SCREEN_ORIENTATION_SENSOR
                }

                else -> {
                    ActivityInfo.SCREEN_ORIENTATION_SENSOR
                }
            }

            onDispose { }
        }
    }

    private fun enableEdgeToEdge(
        style: SystemBarStyle = SystemBarStyle.auto(
            lightScrim = Color.TRANSPARENT,
            darkScrim = Color.TRANSPARENT,
        )
    ) {
        enableEdgeToEdge(
            statusBarStyle = style,
            navigationBarStyle = style,
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }
    }
}
