plugins {
    id("composablescreens.android.application")
    id("composablescreens.android.application.compose")
}

android {
    namespace = rootProject.extra["packageName"].toString()

    defaultConfig {
        applicationId = rootProject.extra["packageName"].toString()

        versionCode = 1
        versionName = "1.0"

        vectorDrawables {
            useSupportLibrary = true
        }
    }
}

dependencies {
    implementation(project(":travel"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)

    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.material.iconsExtended)
    implementation(libs.androidx.compose.material3.windowSizeClass)
}
