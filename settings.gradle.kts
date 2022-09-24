pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }

    versionCatalogs {
        create("libs") {
            version("compileSdk", "33")
            version("minSdk", "21")
            version("targetSdk", "33")
            version("agp", "7.3.0")
            version("compose-compiler", "1.3.1")
            version("compose", "1.3.0-beta02")

            library("androidx-compose-ui", "androidx.compose.ui", "ui").versionRef("compose")
            library("androidx-compose-material", "androidx.compose.material", "material").versionRef("compose")
            library("androidx-compose-material3", "androidx.compose.material3", "material3").version("1.0.0-beta02")
            library("androidx-compose-material-icons", "androidx.compose.material", "material-icons-extended").versionRef("compose")
            library("androidx-compose-ui-tooling-preview", "androidx.compose.ui", "ui-tooling-preview").versionRef("compose")
            val composeLibraries = listOf("androidx-compose-ui", "androidx-compose-material-icons", "androidx-compose-ui-tooling-preview")
            bundle("compose", composeLibraries + "androidx-compose-material")
            bundle("compose-m3", composeLibraries + "androidx-compose-material3")

            library("androidx-compose-ui-tooling", "androidx.compose.ui", "ui-tooling").versionRef("compose")
            library("androidx-compose.ui-test-manifest", "androidx.compose.ui", "ui-test-manifest").versionRef("compose")
            bundle("compose-debug", listOf("androidx-compose-ui-tooling", "androidx-compose.ui-test-manifest"))

            library("androidx-activity-compose", "androidx.activity:activity-compose:1.5.1")
            library("androidx-core", "androidx.core:core-ktx:1.8.0")
            library("androidx-constraintlayout-compose", "androidx.constraintlayout:constraintlayout-compose:1.0.1")
            library("androidx-navigation-compose", "androidx.navigation:navigation-compose:2.5.2")
        }
    }
}
rootProject.name = "Composable Screens"
include(":app")
include(":travel")
