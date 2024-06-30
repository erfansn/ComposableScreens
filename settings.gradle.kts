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
            version("compile", "34")
            version("min", "21")
            version("target", "34")
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
