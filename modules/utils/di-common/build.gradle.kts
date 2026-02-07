plugins {
    id("com.dreifus.kmp-compose-library")
    alias(libs.plugins.metro)
}

android {
    namespace = "com.dreifus.template.di.common"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.androidx.lifecycle.viewmodelCompose)
        }
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
        }
    }
}
