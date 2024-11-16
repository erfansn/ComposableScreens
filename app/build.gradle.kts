/*
 * Copyright 2024 Erfan Sn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
        versionName = "2024.11"

        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("debug")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
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

private operator fun ProjectDependency.iterator() =
    object : Iterator<ProjectDependency> {
        var moduleCount = this@iterator::class.java.declaredMethods.size

        override fun hasNext(): Boolean = moduleCount-- != 0

        override fun next(): ProjectDependency =
            this@iterator::class.java.declaredMethods[moduleCount].invoke(
                this@iterator,
            ) as ProjectDependency
    }
