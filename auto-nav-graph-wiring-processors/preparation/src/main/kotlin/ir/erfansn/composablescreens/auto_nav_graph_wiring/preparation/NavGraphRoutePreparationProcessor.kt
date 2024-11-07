/*
 * Copyright 2024 Erfan Sn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

class NavGraphRoutePreparationProcessorProvider : SymbolProcessorProvider {
  override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor =
    NavGraphRoutePreparationProcessor(
      environment.options["category_name"]!!,
      environment.codeGenerator,
      environment.logger,
    )
}

class NavGraphRoutePreparationProcessor(
  private val categoryName: String,
  private val codeGenerator: CodeGenerator,
  private val logger: KSPLogger,
) : SymbolProcessor {
  private var round = 0

  override fun process(resolver: Resolver): List<KSAnnotated> {
    if (round++ != 0) return emptyList()

    val targetFunctions =
      resolver
        .getSymbolsWithAnnotation(AutoNavGraphWiring::class.qualifiedName!!)
        .filterIsInstance<KSFunctionDeclaration>()

    if (targetFunctions.count() == 0) return emptyList()

    targetFunctions.forEach { targetFunction ->
      val annotation =
        targetFunction.annotations
          .firstOrNull { it.shortName.asString() == AutoNavGraphWiring::class.simpleName }
          ?: return@forEach

      val args = annotation.arguments.associateBy { it.name?.asString()!! }

      val routeType = args["route"]?.value!! as KSType
      val name = args["name"]?.value!! as String

      val routeQualifiedName =
        routeType.declaration.qualifiedName?.asString() ?: return@forEach

      val fileContent =
        buildString {
          appendLine("package $DEST_PACKAGE_NAME")
          appendLine()
          appendLine("import androidx.navigation.NavGraphBuilder")
          appendLine("import androidx.navigation.NavController")
          appendLine("import androidx.navigation.navigation")
          appendLine("import androidx.navigation.compose.composable")
          appendLine("import ${targetFunction.qualifiedName!!.asString()}")
          appendLine()
          appendLine("/** Use aggregator version instead of this */")
          appendLine("@${InternalAutoNavGraphWiring::class.qualifiedName!!}(\"$categoryName\", \"$name\", $routeQualifiedName::class)")

          val targetFunctionTransformedName = targetFunction.simpleName.asString().replaceFirstChar { it.lowercase() }
          appendLine("fun NavGraphBuilder.${targetFunctionTransformedName}AsGeneratedDestinationRoute() {")
          appendLine("    composable<$routeQualifiedName> {")
          appendLine("        ${targetFunction.simpleName.asString()}()")
          appendLine("    }")
          appendLine("}")
        }

      val file =
        codeGenerator.createNewFile(
          Dependencies(false),
          DEST_PACKAGE_NAME,
          "Generated${name.hashCode()}NavGraphRoute",
        )
      file.writer().use { it.write(fileContent) }
    }
    return emptyList()
  }

  companion object {
    private const val DEST_PACKAGE_NAME = "ir.erfansn.composablescreens"
  }
}
