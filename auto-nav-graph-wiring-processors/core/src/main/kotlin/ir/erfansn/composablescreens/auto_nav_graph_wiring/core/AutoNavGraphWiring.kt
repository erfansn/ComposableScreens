package ir.erfansn.composablescreens.auto_nav_graph_wiring.core

import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class AutoNavGraphWiring(
    val name: String,
    val route: KClass<*>,
    val startDestination: KClass<*>,
    val screenOrientation: ScreenOrientation = ScreenOrientation.Auto
)
