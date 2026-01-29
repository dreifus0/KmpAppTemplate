@file:Suppress("MatchingDeclarationName")

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

object Sdks {
    const val compile = 36
    const val target = 36
    const val min = 24
}

object Versions {
    const val appVersionCode = 1
    const val appVersionName = "1"
}

val Flavours = com.dreifus.constants.Flavours
val Packages = com.dreifus.constants.Packages

object AppLabels {
    const val app = "App"
}

object SupportedLanguages {
    val app = listOf("en")
}

/**
 * Simplifies adding jars. In Groovy it looked like: implementation fileTree(dir: 'libs', include: ['*.jar'])
 */
val DependencyHandler.implementationJarLibs: Project.() -> Unit
    get() = {
        add("implementation", fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    }

/**
 * Base configuration for Android modules (libraries and applications)
 */
fun CommonExtension<*, *, *, *, *, *>.configureAndroidCommon() {
    compileSdk = Sdks.compile

    defaultConfig {
        minSdk = Sdks.min
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

/**
 * Kotlin compilation setup for Android projects
 */
fun Project.configureKotlinAndroid() {
    extensions.findByType(KotlinAndroidProjectExtension::class.java)?.apply {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
}

fun CommonExtension<*, *, *, *, *, *>.addFlavoursByStaging() {
    flavorDimensions += Flavours.byStaging.dimension

    productFlavors {
        create(Flavours.byStaging.stage) {
            dimension = Flavours.byStaging.dimension
        }
        create(Flavours.byStaging.prod) {
            dimension = Flavours.byStaging.dimension
        }
    }
}
