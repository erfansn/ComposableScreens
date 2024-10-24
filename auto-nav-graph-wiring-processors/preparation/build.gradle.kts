plugins {
    alias(libs.plugins.kotlin.jvm)
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    implementation(projects.autoNavGraphWiringProcessors.core)

    implementation(libs.symbol.processing.api)
}