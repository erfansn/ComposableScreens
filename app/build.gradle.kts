plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.plugin.compose)
    alias(libs.plugins.ksp)
}

android {
    namespace = "ir.erfansn.composablescreens"
    compileSdk = sdk.versions.compile.get().toInt()

    defaultConfig {
        applicationId = "ir.erfansn.composablescreens"
        targetSdk = sdk.versions.target.get().toInt()
        minSdk = sdk.versions.min.get().toInt()

        versionCode = 1
        versionName = "1.0"

        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("debug")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

kotlin {
    jvmToolchain(jvm.versions.toolchain.get().toInt())
}

dependencies {
    implementation(projects.common)
    for (module in projects.travel) implementation(module)
    for (module in projects.food) implementation(module)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.material.iconsExtended)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)

    compileOnly(projects.autoNavGraphWiringProcessors.core)
    ksp(projects.autoNavGraphWiringProcessors.aggregation)
}

private operator fun ProjectDependency.iterator() = object : Iterator<ProjectDependency> {

    var moduleCount = this@iterator::class.java.declaredMethods.size

    override fun hasNext(): Boolean {
        return moduleCount-- != 0
    }

    override fun next(): ProjectDependency {
        return this@iterator::class.java.declaredMethods[moduleCount].invoke(this@iterator) as ProjectDependency
    }
}
