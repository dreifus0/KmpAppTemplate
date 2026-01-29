package com.dreifus.gradle.plugins

import com.dreifus.gradle.utils.MetroCheckDependenciesTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.getByType

// Gradle convention plugins skeleton for future use
class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val versionCatalogs = (extensions.findByType<VersionCatalogsExtension>()
            // in case plugin is applied before Version Catalog (from parent project)
                ?: requireNotNull(parent).extensions.getByType<VersionCatalogsExtension>())
            val libs = versionCatalogs.named("libs")

            val kspPlugin = libs.findPlugin("ksp").get().get()
            pluginManager.apply(kspPlugin.pluginId)

            dependencies.add("ksp", project(":modules:utils:metro-android-ksp"))

            MetroCheckDependenciesTask.registerFor(this)
        }
    }
}
