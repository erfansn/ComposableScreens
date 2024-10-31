package ir.erfansn.composablescreens.auto_nav_graph_wiring.aggregation

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSType
import ir.erfansn.composablescreens.auto_nav_graph_wiring.core.InternalAutoNavGraphWiring
import ir.erfansn.composablescreens.auto_nav_graph_wiring.core.ScreenOrientation

class AutoWiringAggregationProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return AutoWiringAggregationProcessor(
            environment.codeGenerator,
            environment.logger
        )
    }
}

typealias RouteQualifiedName = String

class AutoWiringAggregationProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : SymbolProcessor {

    private var round = 0

    @OptIn(KspExperimental::class)
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val navGraphFunctions =
            resolver.getDeclarationsFromPackage(SRC_PACKAGE_NAME)
                .filterIsInstance<KSFunctionDeclaration>()
                .filter { it.isAnnotationPresent(InternalAutoNavGraphWiring::class) }

        if (round++ != 0) {
            return emptyList()
        }

        generateAggregatedNavGraphsFunction(navGraphFunctions)

        val categoryToNames = mutableMapOf<String, List<String>>()
        val nameToRouteQualifiedName = mutableMapOf<String, RouteQualifiedName>()
        val portraitOrientationRoutesQualifiedName = mutableListOf<RouteQualifiedName>()
        val landscapeOrientationRoutesQualifiedName = mutableListOf<RouteQualifiedName>()
        navGraphFunctions.forEach { navGraphFunction ->
            val annotation = navGraphFunction.annotations
                .firstOrNull { it.shortName.asString() == InternalAutoNavGraphWiring::class.simpleName }
                ?: return@forEach

            val args = annotation.arguments.associateBy { it.name?.asString()!! }

            val category = args["category"]?.value!! as String
            val name = args["name"]?.value!! as String
            val routeType = args["route"]?.value!! as KSType
            val screenOrientation = args["screenOrientation"]?.value as KSType

            val routeQualifiedName = routeType.declaration.qualifiedName!!.asString()

            categoryToNames[category] = categoryToNames.getOrDefault(category, emptyList()) + name
            nameToRouteQualifiedName[name] = routeQualifiedName

            with(screenOrientation.declaration) {
                when (ScreenOrientation.valueOf(simpleName.asString())) {
                    ScreenOrientation.Portrait -> {
                        portraitOrientationRoutesQualifiedName += routeQualifiedName
                    }

                    ScreenOrientation.Landscape -> {
                        landscapeOrientationRoutesQualifiedName += routeQualifiedName
                    }

                    ScreenOrientation.Auto -> { }
                }
            }
        }
        generateMetadataExpressions(categoryToNames, nameToRouteQualifiedName, portraitOrientationRoutesQualifiedName, landscapeOrientationRoutesQualifiedName)

        return emptyList()
    }

    private fun generateAggregatedNavGraphsFunction(functions: Sequence<KSFunctionDeclaration>) {
        val fileContent = buildString {
            appendLine("package $DST_PACKAGE_NAME")
            appendLine()
            appendLine("import androidx.navigation.NavGraphBuilder")
            appendLine("import androidx.navigation.NavController")
            functions.forEach {
                appendLine("import ${it.qualifiedName!!.asString()}")
            }
            appendLine()
            appendLine("fun NavGraphBuilder.autoAggregatedNavGraphs(navController: NavController) {")
            functions.forEach {
                appendLine("    ${it.simpleName.asString()}(navController)")
            }
            appendLine("}")
        }

        val file = codeGenerator.createNewFile(
            Dependencies.ALL_FILES,
            DST_PACKAGE_NAME,
            "AutoAggregatedNavGraphs"
        )
        file.writer().use { it.write(fileContent) }
    }

    private fun generateMetadataExpressions(
        categoryToNames: Map<String, List<String>>,
        nameToRouteQualifiedName: Map<String, RouteQualifiedName>,
        portraitOrientationRoutesQualifiedName: List<RouteQualifiedName>,
        landscapeOrientationRoutesQualifiedName: List<RouteQualifiedName>
    ) {
        val sortedGroutToNames = categoryToNames.toSortedMap().map { (category, names) -> category to names.sorted() }.toMap()

        val fileContent = buildString {
            appendLine("package $DST_PACKAGE_NAME")
            appendLine()
            appendLine("val autoWiredGraphsCategoryToNameAndRouteList = mapOf(")
            sortedGroutToNames.forEach { (category, names) ->
                val kotlinNameList = "listOf(${names.joinToString(", ") { "\"$it\" to ${nameToRouteQualifiedName[it]}" }})"
                appendLine("    \"$category\" to $kotlinNameList,")
            }
            appendLine(")")
            appendLine()
            if (portraitOrientationRoutesQualifiedName.isNotEmpty()) {
                appendLine("val autoWiredGraphPortraitOrientationRoutes = listOf(")
                portraitOrientationRoutesQualifiedName.forEach { routeQualifiedName ->
                    appendLine("    $routeQualifiedName,")
                }
                appendLine(")")
                appendLine()
            } else {
                appendLine("val autoWiredGraphPortraitOrientationRoutes = emptyList<String>()")
                appendLine()
            }
            if (landscapeOrientationRoutesQualifiedName.isNotEmpty()) {
                appendLine("val autoWiredGraphLandscapeOrientationRoutes = listOf(")
                landscapeOrientationRoutesQualifiedName.forEach { routeQualifiedName ->
                    appendLine("    $routeQualifiedName,")
                }
                appendLine(")")
            } else {
                appendLine("val autoWiredGraphLandscapeOrientationRoutes = emptyList<String>()")
            }
        }

        val file = codeGenerator.createNewFile(
            Dependencies.ALL_FILES,
            DST_PACKAGE_NAME,
            "AutoWiringNavGraphMetadata"
        )
        file.writer().use { it.write(fileContent) }
    }

    companion object {
        private const val SRC_PACKAGE_NAME = "ir.erfansn.composablescreens"
        private const val DST_PACKAGE_NAME = "ir.erfansn.composablescreens.auto_nav_graph_wiring"
    }
}
