package composablescreens

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

dependencies {
    compileOnly(project(":auto-nav-graph-wiring-processors:core"))
    ksp(project(":auto-nav-graph-wiring-processors:aggregation"))
}