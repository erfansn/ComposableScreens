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
        create("sdk") {
            version("compile", "35")
            version("min", "24")
            version("target", "35")
        }
        create("jvm") {
            version("toolchain", "17")
        }
    }
}

rootProject.name = "ComposableScreens"
include(":app")
include(":travel")
include(":food")
include(":common")
