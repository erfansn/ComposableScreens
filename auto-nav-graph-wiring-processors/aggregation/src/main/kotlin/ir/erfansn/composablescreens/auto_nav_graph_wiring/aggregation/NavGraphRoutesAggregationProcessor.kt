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

class NavGraphRoutesAggregationProcessorProvider : SymbolProcessorProvider {
  override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor =
    NavGraphRoutesAggregationProcessor(
      environment.codeGenerator,
      environment.logger,
    )
}

typealias RouteQualifiedName = String

class NavGraphRoutesAggregationProcessor(
  private val codeGenerator: CodeGenerator,
  private val logger: KSPLogger,
) : SymbolProcessor {
  @OptIn(KspExperimental::class)
  override fun process(resolver: Resolver): List<KSAnnotated> {
    val navGraphRoutesFunctions =
      resolver
        .getDeclarationsFromPackage(SRC_PACKAGE_NAME)
        .filterIsInstance<KSFunctionDeclaration>()
        .filter { it.isAnnotationPresent(InternalAutoNavGraphWiring::class) }
        .toList()

    if (navGraphRoutesFunctions.isEmpty()) return emptyList()

    generateAggregatedNavGraphRoutesFunction(navGraphRoutesFunctions)
    generateMetadataExpressions(navGraphRoutesFunctions)

    return emptyList()
  }

  private fun generateAggregatedNavGraphRoutesFunction(functions: List<KSFunctionDeclaration>) {
    val fileContent =
      buildString {
        appendLine("package $DST_PACKAGE_NAME")
        appendLine()
        appendLine("import androidx.navigation.NavGraphBuilder")
        appendLine("import androidx.navigation.NavController")
        functions.forEach {
          appendLine("import ${it.qualifiedName!!.asString()}")
        }
        appendLine()
        appendLine(
          "fun NavGraphBuilder.autoAggregatedNavGraphRoutes() {",
        )
        functions.forEach {
          appendLine("    ${it.simpleName.asString()}()")
        }
        appendLine("}")
      }

    try {
      val file =
        codeGenerator.createNewFile(
          Dependencies.ALL_FILES,
          DST_PACKAGE_NAME,
          "AutoAggregatedNavGraphRoutes",
        )
      file.writer().use { it.write(fileContent) }
    } catch (e: FileAlreadyExistsException) {
      // Error swallowing :)
    }
  }

  private fun generateMetadataExpressions(functions: List<KSFunctionDeclaration>) {
    val categoryToNames = mutableMapOf<String, List<String>>()
    val nameToRouteQualifiedName = mutableMapOf<String, RouteQualifiedName>()
    functions.forEach { navGraphRoutesFunction ->
      val annotation =
        navGraphRoutesFunction.annotations
          .firstOrNull {
            it.shortName.asString() ==
              InternalAutoNavGraphWiring::class.simpleName
          }
          ?: return@forEach

      val args = annotation.arguments.associateBy { it.name?.asString()!! }

      val category = args["category"]?.value!! as String
      val name = args["name"]?.value!! as String
      val routeType = args["route"]?.value!! as KSType

      val routeQualifiedName = routeType.declaration.qualifiedName!!.asString()

      categoryToNames[category] = categoryToNames.getOrDefault(category, emptyList()) + name
      nameToRouteQualifiedName[name] = routeQualifiedName
    }

    val sortedGroutToNames =
      categoryToNames
        .toSortedMap()
        .map { (category, names) ->
          category to
            names.sorted()
        }.toMap()

    val fileContent =
      buildString {
        appendLine("package $DST_PACKAGE_NAME")
        appendLine()
        appendLine("val autoWiredRoutesCategoryToNameAndRouteList = mapOf(")
        sortedGroutToNames.forEach { (category, names) ->
          val kotlinNameList =
            "listOf(${names.joinToString(", ") { """"$it" to ${nameToRouteQualifiedName[it]}""" }})"
          appendLine("    \"$category\" to $kotlinNameList,")
        }
        appendLine(")")
      }

    try {
      val file =
        codeGenerator.createNewFile(
          Dependencies.ALL_FILES,
          DST_PACKAGE_NAME,
          "AutoWiringNavGraphRoutesMetadata",
        )
      file.writer().use { it.write(fileContent) }
    } catch (e: FileAlreadyExistsException) {
      // Error swallowing :)
    }
  }

  companion object {
    private const val SRC_PACKAGE_NAME = "ir.erfansn.composablescreens"
    private const val DST_PACKAGE_NAME = "ir.erfansn.composablescreens.auto_nav_graph_wiring"
  }
}
