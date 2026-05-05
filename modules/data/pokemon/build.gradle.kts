plugins {
    id("com.dreifus.kmp-library")
    alias(libs.plugins.metro)
    alias(libs.plugins.kotlinSerialization)
}

android {
    namespace = "com.dreifus.app.data.pokemon"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.coroutines.core)
            implementation(libs.kotlin.serialization)
            implementation(libs.ktor.client.content.negotiation)

            implementation(projects.modules.utils.network)
        }
    }
}
