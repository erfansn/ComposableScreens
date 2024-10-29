plugins {
    id("composablescreens.library")
    id("composablescreens.common-dependencies")
    id("composablescreens.travel.auto-nav-graph-wiring")
}

android {
    namespace = "ir.erfansn.composablescreens.travel.qclay_trip"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(libs.haze)
    implementation(libs.androidx.constraintlayout.compose)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.material.iconsExtended)
    implementation(libs.androidx.compose.material3.windowSizeClass)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
