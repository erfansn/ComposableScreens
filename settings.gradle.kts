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

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "ComposableScreens"

includeBuild("build-logic")

private fun subprojects(path: String) =
    file(path).listFiles()?.filter {
        it.isDirectory && it.listFiles()?.any { file -> file.name == "build.gradle.kts" } ?: false
    }?.map {
        "$path:${it.name}"
    }.orEmpty()

include("app")
include("common")
include(subprojects("auto-nav-graph-wiring-processors"))
include(subprojects("travel"))
include(subprojects("food"))
