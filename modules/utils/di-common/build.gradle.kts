plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.metro)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.dreifus.template.di.common"
    configureAndroidCommon()
}

configureKotlinAndroid()

dependencies {
    api(libs.androidx.appcompat)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.activity.compose)
}
