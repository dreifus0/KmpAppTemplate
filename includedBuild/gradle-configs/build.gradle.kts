plugins {
    // workaround https://github.com/usefulness/easylauncher-gradle-plugin/issues/324
    // also helps with the same problem with Firebase App Distribution
    `kotlin-dsl`
    `groovy-gradle-plugin`
    `kotlin-dsl-precompiled-script-plugins`
}

dependencies {
    compileOnly(libs.plugin.agp)
    compileOnly(libs.plugin.kotlin)
    compileOnly(libs.plugin.ksp)
    implementation(projects.sharedConsts)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "com.dreifus.android-app"
            implementationClass = "com.dreifus.gradle.plugins.AndroidApplicationConventionPlugin"
        }
        register("composeAndroidLibraryMaterial3") {
            id = "com.dreifus.compose-android-library-material3"
            implementationClass = "com.dreifus.gradle.plugins.ComposeAndroidLibraryMaterial3ConventionPlugin"
        }
        register("composeAndroidApplicationMaterial3") {
            id = "com.dreifus.compose-android-application-material3"
            implementationClass = "com.dreifus.gradle.plugins.ComposeAndroidApplicationMaterial3ConventionPlugin"
        }
    }
}
