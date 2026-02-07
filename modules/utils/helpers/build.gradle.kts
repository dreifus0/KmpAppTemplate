plugins {
    id("com.dreifus.kmp-compose-library")
    alias(libs.plugins.metro)
}

android {
    namespace = "com.dreifus.helpers"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.modules.utils.diCommon)
            implementation(libs.coroutines.core)
            implementation(libs.compose.runtime)
        }
    }
}
