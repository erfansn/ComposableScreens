ext {
    this["compose_compiler_version"] = "1.3.0"
    this["compose_version"] = "1.2.1"
}// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "7.2.2" apply false
    id("com.android.library") version "7.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.7.10" apply false
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}