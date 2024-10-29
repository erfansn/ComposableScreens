// A workaround to prevent unresolved context-error
import org.gradle.kotlin.dsl.libs
import org.gradle.kotlin.dsl.libs as projectlibs

plugins {
    `kotlin-dsl`
}

dependencies {
    // Until resolving: https://github.com/gradle/gradle/issues/15383#issuecomment-1193076271
    implementation(files(projectlibs.javaClass.superclass.protectionDomain.codeSource.location))

    implementation("com.android.tools.build:gradle:${libs.versions.androidGradlePlugin.get()}")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${libs.versions.kotlin.get()}")
    implementation("com.google.devtools.ksp:symbol-processing-gradle-plugin:${libs.versions.symbolProcessingApi.get()}")
    implementation("org.jetbrains.kotlin:compose-compiler-gradle-plugin:${libs.versions.kotlin.get()}")
    implementation("org.jetbrains.kotlin:kotlin-serialization:${libs.versions.kotlin.get()}")
}
