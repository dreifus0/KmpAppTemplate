plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
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

    api(projects.modules.utils.coreNavigation)
    api(projects.modules.utils.uikit)

    api(projects.modules.utils.diCommon)
    ksp(projects.modules.utils.metroAndroidKsp)
}
