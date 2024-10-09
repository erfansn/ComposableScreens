package ir.erfansn.composablescreens

import android.annotation.SuppressLint
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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ir.erfansn.composablescreens.common.BarStyle
import ir.erfansn.composablescreens.common.LocalSharedTransitionScope
import ir.erfansn.composablescreens.common.ProvideSystemBarStyleChanger
import ir.erfansn.composablescreens.food.kristina_cookie.foodNavGraph
import ir.erfansn.composablescreens.travel.qclay_trip.ui.navigation.travelNavigationGraph
import ir.erfansn.composablescreens.ui.ComposableScreensList
import ir.erfansn.composablescreens.ui.theme.ComposableScreensTheme

class MainActivity : ComponentActivity() {

    @SuppressLint("RestrictedApi", "SourceLockedOrientationActivity")
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
                            travelNavigationGraph(navController)
                            foodNavGraph(navController)
                        }

                        val currentBackStack by navController.currentBackStack.collectAsState()
                        LaunchedEffect(currentBackStack) {
                            requestedOrientation = when {
                                currentBackStack.any { it.destination.route in listOf("food", "travel") } -> {
                                    ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                                }

                                else -> {
                                    ActivityInfo.SCREEN_ORIENTATION_SENSOR
                                }
                            }
                        }
                    }
                }
            }
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
