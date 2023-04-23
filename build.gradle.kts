// Top-level build file where you can add configuration options common to all sub-projects/modules.
extra["packageName"] = "ir.erfansn.composablescreens"

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
