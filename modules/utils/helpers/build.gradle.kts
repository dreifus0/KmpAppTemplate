plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.metro)
}

android {
    namespace = "com.dreifus.helpers"
    configureAndroidCommon()
}

configureKotlinAndroid()

dependencies {
    api(projects.modules.utils.diCommon)
}
