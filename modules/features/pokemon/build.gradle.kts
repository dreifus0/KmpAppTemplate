plugins {
    id("com.dreifus.kmp-compose-library")
    alias(libs.plugins.metro)
}

android {
    namespace = "com.dreifus.app.features.pokemon"
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
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor3)

            implementation(projects.modules.utils.arch)
            implementation(projects.modules.utils.uikit)
            implementation(projects.modules.utils.coreNavigation)
            api(projects.modules.data.pokemon)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}
