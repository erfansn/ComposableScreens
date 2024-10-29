package composablescreens

import JVM_COMPATIBILITY_VERSION
import SdkVersions

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    compileSdk = SdkVersions.COMPILE
    defaultConfig {
        targetSdk = SdkVersions.TARGET
        minSdk = SdkVersions.MIN
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
