package ir.erfansn.composablescreens.auto_nav_graph_wiring.preparation

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
import ir.erfansn.composablescreens.auto_nav_graph_wiring.core.AutoNavGraphWiring
import ir.erfansn.composablescreens.auto_nav_graph_wiring.core.InternalAutoNavGraphWiring
import java.util.Locale

class AutoWiringPreparationProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return AutoWiringPreparationProcessor(
            environment.options["group_name"]!!,
            environment.codeGenerator,
            environment.logger
        )
    }
}

class AutoWiringPreparationProcessor(
    private val categoryName: String,
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : SymbolProcessor {

    private var round = 0

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val navGraphFunctions =
            resolver.getSymbolsWithAnnotation(AutoNavGraphWiring::class.qualifiedName!!)
                .filterIsInstance<KSFunctionDeclaration>()

        if (round++ == 0 && navGraphFunctions.count() == 0) {
            logger.warn("No function found with AutoNavGraphWiring annotation.")
            return emptyList()
        }

        navGraphFunctions.forEach { navGraphFunction ->
            val annotation = navGraphFunction.annotations
                .firstOrNull { it.shortName.asString() == AutoNavGraphWiring::class.simpleName }
                ?: return@forEach

            val args = annotation.arguments.associateBy { it.name?.asString()!! }

            val routeType = args["route"]?.value!! as KSType
            val startDestinationType = args["startDestination"]?.value!! as KSType
            val name = args["name"]?.value!! as String
            val screenOrientation = args["screenOrientation"]?.value!!

            val routeQualifiedName =
                routeType.declaration.qualifiedName?.asString() ?: return@forEach
            val startDestinationQualifiedName =
                startDestinationType.declaration.qualifiedName?.asString() ?: return@forEach

            val fileContent = buildString {
                appendLine("package $DEST_PACKAGE_NAME")
                appendLine()
                appendLine("import androidx.navigation.NavGraphBuilder")
                appendLine("import androidx.navigation.NavController")
                appendLine("import androidx.navigation.navigation")
                appendLine("import ${navGraphFunction.qualifiedName!!.asString()}")
                appendLine()
                appendLine("/** Use aggregator version instead of this */")
                appendLine("@${InternalAutoNavGraphWiring::class.qualifiedName!!}(\"$categoryName\", \"$name\", $routeQualifiedName::class, $screenOrientation)")
                appendLine("fun NavGraphBuilder.${navGraphFunction.simpleName.asString()}Generated(navController: NavController) {")
                appendLine("    navigation<$routeQualifiedName>(")
                appendLine("        startDestination = $startDestinationQualifiedName")
                appendLine("    ) {")
                appendLine("        ${navGraphFunction.simpleName.asString()}(navController)")
                appendLine("    }")
                appendLine("}")
            }

            val file = codeGenerator.createNewFile(
                Dependencies(false),
                DEST_PACKAGE_NAME,
                "Generated${name.toPascalCase()}NavigationGraph"
            )
            file.writer().use { it.write(fileContent) }
        }
        return emptyList()
    }

    private fun String.toPascalCase(): String = split(" ").joinToString(separator = "") {
        it.replaceFirstChar { c -> if (c.isLowerCase()) c.titlecase(Locale.ROOT) else c.toString() }
    }

    companion object {
        private const val DEST_PACKAGE_NAME = "ir.erfansn.composablescreens"
    }
}
