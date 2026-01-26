@file:Suppress("UnstableApiUsage")

package com.dreifus.gradle.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.getByType

class ComposeAndroidLibraryMaterial3ConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")
            configureAndroidCompose()
        }
    }
}

class ComposeAndroidApplicationMaterial3ConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.application")
            configureAndroidCompose()
        }
    }
}

internal fun Project.configureAndroidCompose() {
    val versionCatalogs = (extensions.findByType<VersionCatalogsExtension>()
    // на случай если плагин применяется до применения Version Catalog (из родительского проекта)
        ?: requireNotNull(parent).extensions.getByType<VersionCatalogsExtension>())
    val libs = versionCatalogs.named("libs")

    // для отлова иерархии через sentry
    // https://docs.sentry.io/platforms/android/enriching-events/viewhierarchy/#jetpack-compose-support-using-the-sentry-kotlin-compiler-plugin
    val sentryKotlinCompiler = libs.findPlugin("io-sentry-kotlin-compiler-gradle").get().get()
    pluginManager.apply(sentryKotlinCompiler.pluginId)
    val composeCompiler = libs.findPlugin("compose-compiler").get().get()
    pluginManager.apply(composeCompiler.pluginId)
}
