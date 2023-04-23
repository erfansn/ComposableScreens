pluginManagement {
    includeBuild("conventions")
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
        create("sdk") {
            version("compile", "33")
            version("min", "21")
            version("target", "33")
        }
    }
}
rootProject.name = "Composable Screens"
include(":app")
include(":travel")
