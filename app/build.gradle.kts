plugins {
    id("composablescreens.application")
    id("composablescreens.common-dependencies")
    id("composablescreens.auto-nav-graph-wiring")
}

android {
    namespace = "ir.erfansn.composablescreens"

    defaultConfig {
        applicationId = "ir.erfansn.composablescreens"

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

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    for (module in projects.category.travel) implementation(module)
    for (module in projects.category.food) implementation(module)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.material.iconsExtended)
    implementation(libs.androidx.compose.material3.windowSizeClass)
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
