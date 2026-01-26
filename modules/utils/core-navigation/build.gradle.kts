plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.metro)
}

android {
    namespace = "com.dreifus.template.core.navigation"
    configureAndroidCommon()
}

configureKotlinAndroid()

dependencies {
    api(libs.androidx.navigation3.ui)
    api(libs.androidx.navigation3.runtime)
    api(libs.androidx.lifecycle.viewmodel.navigation3)
    api(projects.modules.utils.diCommon)
    implementation(projects.modules.utils.uikit)
    implementation(libs.kotlin.serialization)
}
