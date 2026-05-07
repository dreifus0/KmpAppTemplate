plugins {
    id("com.dreifus.kmp-compose-library")
    alias(libs.plugins.metro)
}

android {
    namespace = "com.dreifus.app.features.onboarding"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.metrox.viewmodel.compose)
            implementation(libs.mvucore)
            implementation(libs.multiplatform.settings)

            implementation(projects.modules.utils.arch)
            implementation(projects.modules.utils.uikit)
            implementation(projects.modules.utils.coreNavigation)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}
