package composablescreens

import JVM_COMPATIBILITY_VERSION
import SdkVersions

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    compileSdk = SdkVersions.COMPILE
    defaultConfig {
        minSdk = SdkVersions.MIN

        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JVM_COMPATIBILITY_VERSION
        targetCompatibility = JVM_COMPATIBILITY_VERSION
    }
    kotlinOptions {
        jvmTarget = JVM_COMPATIBILITY_VERSION.toString()
    }
}
