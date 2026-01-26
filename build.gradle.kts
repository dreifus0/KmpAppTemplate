@file:Suppress("UnderscoresInNumericLiterals")

buildscript {
    extra.apply {
        set("support_version", "29.0.0")
    }

    dependencies {
        classpath(libs.plugin.agp)
    }
}

plugins {
    // includedBuild
    id("com.dreifus.android-app").apply(false)
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.metro).apply(false)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}
