package composablescreens

import gradle.kotlin.dsl.accessors._6ab6c3f1daf0c54b8463dcc0a8d5f754.implementation
import gradle.kotlin.dsl.accessors._872ad73bda21390b99a23da2f9228964.debugImplementation
import libs

plugins {
    id("org.jetbrains.kotlin.plugin.serialization")
}

dependencies {
    implementation(project(":common"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization)
}