package composablescreens.food

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

ksp {
    arg("category_name", "Food")
}

dependencies {
    compileOnly(project(":auto-nav-graph-wiring-processors:core"))
    ksp(project(":auto-nav-graph-wiring-processors:preparation"))
}