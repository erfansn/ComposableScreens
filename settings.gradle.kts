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
            version("agp", "7.2.2")
            version("compose-compiler", "1.3.0")
            version("compose", "1.2.1")

            library("androidx-compose-ui", "androidx.compose.ui", "ui").versionRef("compose")
            library("androidx-compose-material", "androidx.compose.material", "material").versionRef("compose")
            library("androidx-compose-ui-tooling-preview", "androidx.compose.ui", "ui-tooling-preview").versionRef("compose")
            library("androidx-core", "androidx.core:core-ktx:1.8.0")
            library("androidx-lifecycle", "androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
            library("androidx-activity-compose", "androidx.activity:activity-compose:1.5.1")

            library("junit", "junit:junit:4.13.2")

            library("androidx-test-ext", "androidx.test.ext:junit:1.1.3")
            library("androidx-test-espresso", "androidx.test.espresso:espresso-core:3.4.0")
            library("androidx-compose-ui-test", "androidx.compose.ui", "ui-test-junit4").versionRef("compose")
            library("androidx-compose-ui-tooling", "androidx.compose.ui", "ui-tooling").versionRef("compose")
            library("androidx-compose-ui-test-manifest", "androidx.compose.ui", "ui-test-manifest").versionRef("compose")
        }
    }
}
rootProject.name = "Composable Screens"
include(":app")
