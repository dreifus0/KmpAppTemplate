import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.project

fun Project.initKtorfit() {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
    val ksp = libs.findPlugin("com-google-devtools-ksp").get().get()
    val ktorfit = libs.findPlugin("de-jensklingenberg-ktorfit").get().get()
    pluginManager.apply(ksp.pluginId)
    pluginManager.apply(ktorfit.pluginId)

    dependencies {
        add("ksp", project(":modules:utils:ktorfit-di"))
    }
}
