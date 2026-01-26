plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.dreifus.template.uikit"
    configureAndroidCommon()

    buildFeatures {
        buildConfig = true
    }
}

configureKotlinAndroid()

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.compose.constraint)

    api(platform(libs.compose.bom))
    api(libs.compose.runtime)
    api(libs.compose.ui)
    api(libs.compose.preview)
    api(libs.compose.foundation)
    api(libs.compose.material3)
    api(libs.compose.materialIconsExtended)
    api(libs.compose.livedata)
    api(libs.compose.lifecycle.runtime)
    debugApi(libs.compose.tooling)
}
