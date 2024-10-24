package ir.erfansn.composablescreens.auto_nav_graph_wiring.core

import kotlin.reflect.KClass

/**
 * Don't use this manually
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
annotation class InternalAutoNavGraphWiring(
    val group: String,
    val name: String,
    val route: KClass<*>,
    val screenOrientation: ScreenOrientation
)
