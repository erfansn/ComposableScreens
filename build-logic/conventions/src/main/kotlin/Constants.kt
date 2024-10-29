import org.gradle.api.JavaVersion

object SdkVersions {
    const val COMPILE = 34
    const val TARGET = 34
    const val MIN = 24
}

val JVM_COMPATIBILITY_VERSION = JavaVersion.VERSION_1_8
