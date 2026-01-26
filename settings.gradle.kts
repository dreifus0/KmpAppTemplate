pluginManagement {
    includeBuild("commonBuild")
    includeBuild("includedBuild")
}

plugins {
    id("repositories-plugin")
}

dependencyResolutionManagement {
    repositories.applyMainRepositories()
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("STABLE_CONFIGURATION_CACHE")
rootProject.name = "KmpTemplateApp"

include(
    ":android:app",
    ":modules:utils:uikit",
    ":modules:utils:di-common",
    ":modules:utils:core-navigation",
    ":modules:utils:destination-collector",
    ":modules:utils:ktorfit-di",
    ":modules:utils:metro-android-ksp",
)

