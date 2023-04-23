import com.android.build.api.dsl.ApplicationExtension
import configuration.configureCommon
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            val sdk = extensions.getByType<VersionCatalogsExtension>().named("sdk")

            extensions.configure<ApplicationExtension> {
                defaultConfig.targetSdk = sdk.findVersion("target").get().requiredVersion.toInt()
                configureCommon(this)
            }
        }
    }
}
