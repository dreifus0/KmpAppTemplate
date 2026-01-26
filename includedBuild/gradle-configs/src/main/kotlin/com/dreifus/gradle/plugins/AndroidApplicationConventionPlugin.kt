package com.dreifus.gradle.plugins

import com.android.build.api.dsl.ApplicationExtension
import com.dreifus.gradle.utils.MetroCheckDependenciesTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.getByType

// Скелет Gradle convention plugins на будущее
class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val versionCatalogs = (extensions.findByType<VersionCatalogsExtension>()
            // на случай если плагин применяется до применения Version Catalog (из родительского проекта)
                ?: requireNotNull(parent).extensions.getByType<VersionCatalogsExtension>())
            val libs = versionCatalogs.named("libs")

            val kspPlugin = libs.findPlugin("ksp").get().get()
            pluginManager.apply(kspPlugin.pluginId)

            dependencies.add("ksp", project(":modules:utils:metro-android-ksp"))

            val android = extensions.getByType<ApplicationExtension>()
            android.packaging.resources.excludes += "**/*.proto"

            MetroCheckDependenciesTask.registerFor(this)
        }
    }
}
