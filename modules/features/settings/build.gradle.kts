plugins {
    id("com.dreifus.kmp-compose-library")
    alias(libs.plugins.metro)
}

android {
    namespace = "com.dreifus.app.features.settings"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.metrox.viewmodel.compose)
            implementation(libs.mvucore)
            implementation(libs.coroutines.core)
            implementation(libs.multiplatform.settings)

            implementation(projects.modules.utils.arch)
            implementation(projects.modules.utils.uikit)
            implementation(projects.modules.utils.coreNavigation)
            implementation(projects.modules.features.onboarding)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}
