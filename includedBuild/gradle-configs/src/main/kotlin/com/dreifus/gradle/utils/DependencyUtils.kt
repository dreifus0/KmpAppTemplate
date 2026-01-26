package com.dreifus.gradle.utils

import org.gradle.api.artifacts.VersionCatalog
import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.addImplementation(libs: VersionCatalog, alias: String) {
    val dependency = libs.findLibrary(alias).get()
    add("implementation", dependency)
}

fun DependencyHandlerScope.addDebugImplementation(libs: VersionCatalog, alias: String) {
    val dependency = libs.findLibrary(alias).get()
    add("debugImplementation", dependency)
}

fun DependencyHandlerScope.addBom(libs: VersionCatalog, alias: String) {
    val bom = libs.findLibrary(alias).get()
    add("implementation", platform(bom))
}
