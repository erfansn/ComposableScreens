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
            version("compose", "1.2.1")

            library("androidx-compose-ui", "androidx.compose.ui", "ui").versionRef("compose")
            library("androidx-compose-material", "androidx.compose.material", "material").versionRef("compose")
            library("androidx-compose-material-icons", "androidx.compose.material", "material-icons-extended").versionRef("compose")
            library("androidx-compose-ui-tooling-preview", "androidx.compose.ui", "ui-tooling-preview").versionRef("compose")

            library("androidx-compose-ui-tooling", "androidx.compose.ui", "ui-tooling").versionRef("compose")
            library("androidx-compose.ui-test-manifest", "androidx.compose.ui", "ui-test-manifest").versionRef("compose")

            bundle("compose", listOf("androidx-compose-ui", "androidx-compose-material", "androidx-compose-material-icons", "androidx-compose-ui-tooling-preview"))
            bundle("compose-debug", listOf("androidx-compose-ui-tooling", "androidx-compose.ui-test-manifest"))
            library("androidx-activity-compose", "androidx.activity:activity-compose:1.5.1")
            library("androidx-core", "androidx.core:core-ktx:1.8.0")
            library("androidx-constraintlayout", "androidx.constraintlayout:constraintlayout-compose:1.0.1")
        }
    }
}
rootProject.name = "Composable Screens"
include(":app")
include(":travel")
