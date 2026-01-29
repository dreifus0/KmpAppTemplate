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
    // in case plugin is applied before Version Catalog (from parent project)
        ?: requireNotNull(parent).extensions.getByType<VersionCatalogsExtension>())
    val libs = versionCatalogs.named("libs")

    val composeCompiler = libs.findPlugin("compose-compiler").get().get()
    pluginManager.apply(composeCompiler.pluginId)
}
