plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.metro)
    alias(libs.plugins.ksp)
}

android {
    configureApplication(
        android = this,
        supportedLanguages = SupportedLanguages.app,
        appLabel = AppLabels.app,
        applicationId = Packages.app,
        devAppIdSuffix = Packages.devSuffix,
    )
    configureAndroidCommon()

    defaultConfig {
        versionCode = Versions.appVersionCode
        versionName = Versions.appVersionName
    }
}

configureKotlinAndroid()

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.material)
    implementation(libs.play.featureDelivery)
    implementation(libs.mvu.core)
    implementation(libs.androidx.splashScreen)

    implementation(projects.modules.utils.coreNavigation)
    implementation(projects.modules.utils.coreExtensions)
    implementation(projects.modules.utils.uikit)
    implementation(projects.modules.utils.helpers)

    implementation(projects.modules.utils.diCommon)
    ksp(projects.modules.utils.metroAndroidKsp)
}
