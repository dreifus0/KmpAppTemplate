plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.dreifus.core.extensions"
    configureAndroidCommon()
}

configureKotlinAndroid()

dependencies {
    // coroutines
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    api(platform(libs.compose.bom))
    implementation(libs.compose.runtime)
    implementation(libs.compose.ui)
}
