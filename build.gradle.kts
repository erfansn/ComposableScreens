// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress(
    "DSL_SCOPE_VIOLATION",
    "MISSING_DEPENDENCY_CLASS",
    "UNRESOLVED_REFERENCE_WRONG_RECEIVER",
    "FUNCTION_CALL_EXPECTED"
)
plugins {
    val agpVersion = libs.versions.agp.get()
    id("com.android.application") version agpVersion apply false
    id("com.android.library") version agpVersion apply false
    kotlin("android") version "1.7.10" apply false
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}