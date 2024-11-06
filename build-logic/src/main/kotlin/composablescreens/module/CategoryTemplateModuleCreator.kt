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

package composablescreens.module

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.time.Year

internal abstract class CategoryTemplateModuleCreator : DefaultTask() {
  @get:Input
  abstract val categoryName: Property<String>

  @get:Input
  abstract val moduleName: Property<String>

  init {
    group = "create"
    description = "Create a new category module"
  }

  @TaskAction
  fun createModule() {
    val category = File(project.rootDir, "category/${categoryName.get()}")
    if (!category.exists() || !category.isDirectory) {
      throw GradleException("I don't find such \"${categoryName.get()}\" category")
    }

    if (!moduleName.get().isKebabCase()) {
      throw GradleException("Module name must be in kebab-case format")
    }

    if (File(category, moduleName.get()).exists()) {
      throw GradleException("Module with name \"${moduleName.get()}\" already exists in \"${categoryName.get()}\" category")
    }

    val newModule = category.createNewFolderIn(moduleName.get())
    val srcFolder = newModule.createNewFolderIn("src")

    newModule.createNewFileIn("consumer-rules.pro")
    newModule.createNewFileIn("proguard-rules.pro")
    val buildScriptFile = newModule.createNewFileIn("build.gradle.kts")
    buildScriptFile.writer().use { it.appendLine(buildScriptFileContent) }
    val gitIgnoreFile = newModule.createNewFileIn(".gitignore")
    gitIgnoreFile.writer().use { it.appendLine(gitIgnoreFileContent) }

    val mainSourceSetFolder = srcFolder.createNewFolderIn("main")
    val androidManifestFile = mainSourceSetFolder.createNewFileIn("AndroidManifest.xml")
    androidManifestFile.writer().use { it.appendLine(androidManifestFileContent) }
    val mainKotlinFolder = mainSourceSetFolder.createNewFolderIn("kotlin")
    val mainKotlinPackageFolder = mainKotlinFolder.createNewFolderIn(packageNameHierarchy)
    val moduleNavigationFile = mainKotlinPackageFolder.createNewFileIn("${moduleName.get().toPascalCase()}Navigation.kt")
    moduleNavigationFile.writer().use { it.appendLine(moduleNavigationFileContent) }
    val moduleUiFolder = mainKotlinPackageFolder.createNewFolderIn("ui")
    val moduleThemeFile = moduleUiFolder.createNewFileIn("Theme.kt")
    moduleThemeFile.writer().use { it.appendLine(moduleThemeFileContent) }
    val mainResFolder = mainSourceSetFolder.createNewFolderIn("res")
    mainResFolder.createNewFolderIn("drawable")
    mainResFolder.createNewFolderIn("values")

    val androidTestSourceSetFolder = File(srcFolder, "androidTest")
    val androidTestKotlinFolder = androidTestSourceSetFolder.createNewFolderIn("kotlin")
    val androidTestKotlinPackageFolder = androidTestKotlinFolder.createNewFolderIn(packageNameHierarchy)
    val androidTestFile = androidTestKotlinPackageFolder.createNewFileIn("ExampleInstrumentedTest.kt")
    androidTestFile.writer().use { it.appendLine(androidTestFileContent) }

    val testSourceSetFolder = File(srcFolder, "test")
    val testKotlinFolder = testSourceSetFolder.createNewFolderIn("kotlin")
    val testKotlinPackageFolder = testKotlinFolder.createNewFolderIn(packageNameHierarchy)
    val testFile = testKotlinPackageFolder.createNewFileIn("ExampleUnitTest.kt")
    testFile.writer().use { it.appendLine(testFileContent) }
  }

  private val buildScriptFileContent
    get() =
      """
      $licenseHeaderContent

      plugins {
        id("composablescreens.library")
        id("composablescreens.common-dependencies")
        id("composablescreens.${categoryName.get()}.auto-nav-graph-wiring")
      }

      android {
        namespace = "$packageName"

        defaultConfig {
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
      }

      dependencies {
        implementation(libs.haze)

        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.androidx.compose.material3)
        implementation(libs.androidx.compose.material3.windowSizeClass)
        implementation(libs.androidx.material.icons.extended)
        implementation(libs.androidx.ui.text.google.fonts)

        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
      }
      """.trimIndent()

  private val gitIgnoreFileContent = "/build"

  private val androidManifestFileContent =
    """
    <?xml version="1.0" encoding="utf-8"?>
    <manifest/>
    """.trimIndent()

  private val moduleNavigationFileContent
    get() =
      """
      $licenseHeaderContent

      package $packageName

      import androidx.compose.animation.AnimatedContentScope
      import androidx.compose.animation.AnimatedContentTransitionScope
      import androidx.compose.animation.EnterTransition
      import androidx.compose.animation.ExitTransition
      import androidx.compose.animation.SizeTransform
      import androidx.compose.foundation.layout.fillMaxSize
      import androidx.compose.material3.Surface
      import androidx.compose.material3.Text
      import androidx.compose.runtime.Composable
      import androidx.compose.runtime.CompositionLocalProvider
      import androidx.compose.ui.Modifier
      import androidx.navigation.NavBackStackEntry
      import androidx.navigation.NavController
      import androidx.navigation.NavDeepLink
      import androidx.navigation.NavGraphBuilder
      import androidx.navigation.NavType
      import androidx.navigation.compose.composable
      import ir.erfansn.composablescreens.auto_nav_graph_wiring.core.AutoNavGraphWiring
      import ir.erfansn.composablescreens.common.LocalNavAnimatedContentScope
      import $packageName.ui.${moduleName.get().toPascalCase()}Theme
      import kotlinx.serialization.Serializable
      import kotlin.reflect.KType

      @Serializable
      data object ${moduleName.get().toPascalCase()}Route

      @Serializable
      internal data object HomeRoute

      @AutoNavGraphWiring(
        name = "${moduleName.get().toSpacedPascalCase()}",
        route = ${moduleName.get().toPascalCase()}Route::class,
        startDestination = HomeRoute::class,
      )
      internal fun NavGraphBuilder.${moduleName.get().toCamelCase()}NavGraph(navController: NavController) {
        ${moduleName.get().toCamelCase()}Composable<HomeRoute> {
          Surface(modifier = Modifier.fillMaxSize()) {
            Text("Hello Composable Screens to ${moduleName.get().toSpacedPascalCase()}")
          }
        }
      }

      private inline fun <reified T : Any> NavGraphBuilder.${moduleName.get().toCamelCase()}Composable(
        typeMap: Map<KType, @JvmSuppressWildcards NavType<*>> = emptyMap(),
        deepLinks: List<NavDeepLink> = emptyList(),
        noinline enterTransition: (
          @JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?
        )? =
          null,
        noinline exitTransition: (
          @JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?
        )? =
          null,
        noinline popEnterTransition: (
          @JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?
        )? =
          enterTransition,
        noinline popExitTransition: (
          @JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?
        )? =
          exitTransition,
        noinline sizeTransform: (
          @JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> SizeTransform?
        )? =
          null,
        crossinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
      ) {
        composable<T>(
          typeMap = typeMap,
          deepLinks = deepLinks,
          enterTransition = enterTransition,
          exitTransition = exitTransition,
          popEnterTransition = popEnterTransition,
          popExitTransition = popExitTransition,
          sizeTransform = sizeTransform,
        ) {
          ${moduleName.get().toPascalCase()}Theme {
            CompositionLocalProvider(LocalNavAnimatedContentScope provides this) {
              this.content(it)
            }
          }
        }
      }
      """.trimIndent()

  private val moduleThemeFileContent
    get() =
      """
      $licenseHeaderContent

      package $packageName.ui

      import androidx.compose.material3.MaterialTheme
      import androidx.compose.material3.Typography
      import androidx.compose.runtime.Composable
      import androidx.compose.runtime.CompositionLocalProvider
      import androidx.compose.runtime.Immutable
      import androidx.compose.runtime.staticCompositionLocalOf
      import androidx.compose.ui.graphics.Color
      import androidx.compose.ui.platform.LocalLayoutDirection
      import androidx.compose.ui.unit.Dp
      import androidx.compose.ui.unit.LayoutDirection
      import ir.erfansn.composablescreens.common.BarStyle
      import ir.erfansn.composablescreens.common.ProvideSystemBarStyle

      @Composable
      internal fun ${moduleName.get().toPascalCase()}Theme(content: @Composable () -> Unit) {
        ProvideSystemBarStyle(BarStyle.Auto) {
          MaterialTheme(
            content = content,
          )
        }
      }
      """.trimIndent()

  private val androidTestFileContent
    get() =
      """
      $licenseHeaderContent

      package $packageName

      import androidx.test.ext.junit.runners.AndroidJUnit4
      import androidx.test.platform.app.InstrumentationRegistry
      import org.junit.Assert.assertEquals
      import org.junit.Test
      import org.junit.runner.RunWith

      /**
       * Instrumented test, which will execute on an Android device.
       *
       * See [testing documentation](http://d.android.com/tools/testing).
       */
      @RunWith(AndroidJUnit4::class)
      class ExampleInstrumentedTest {
        @Test
        fun useAppContext() {
          // Context of the app under test.
          val appContext = InstrumentationRegistry.getInstrumentation().targetContext
          assertEquals(
            "$packageName.test",
            appContext.packageName,
          )
        }
      }

      """.trimIndent()

  private val testFileContent
    get() =
      """
      $licenseHeaderContent

      package $packageName

      import org.junit.Assert.assertEquals
      import org.junit.Test

      /**
       * Example local unit test, which will execute on the development machine (host).
       *
       * See [testing documentation](http://d.android.com/tools/testing).
       */
      class ExampleUnitTest {
        @Test
        fun addition_isCorrect() {
          assertEquals(4, 2 + 2)
        }
      }
      """.trimIndent()

  private val licenseHeaderContent
    get() =
      """/*
       * Copyright ${Year.now()} Erfan Sn
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
       */"""

  private val packageNameHierarchy
    get() = "ir/erfansn/composablescreens/${categoryName.get()}/${moduleName.get().toSnakeCase()}"

  private val packageName
    get() = packageNameHierarchy.replace('/', '.')

  private fun File.createNewFileIn(path: String): File =
    File(this, path).also {
      it.createNewFile()
    }

  private fun File.createNewFolderIn(path: String): File =
    File(this, path).also {
      it.mkdirs()
    }
}

private fun String.isKebabCase(): Boolean = matches("^[a-z]+[0-9]*(-[a-z]+[0-9]*)*$".toRegex())

private fun String.toSnakeCase(): String = split('-').joinToString(separator = "_")

private fun String.toPascalCase(): String = split('-').joinToString(separator = "") { it.replaceFirstChar { it.uppercase() } }

private fun String.toSpacedPascalCase(): String = split('-').joinToString(separator = " ") { it.replaceFirstChar { it.uppercase() } }

private fun String.toCamelCase(): String =
  split('-')
    .mapIndexed { index, s -> s.replaceFirstChar { if (index == 0) it.lowercase() else it.uppercase() } }
    .joinToString(separator = "")
