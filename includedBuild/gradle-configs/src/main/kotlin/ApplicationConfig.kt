@file:Suppress("MatchingDeclarationName", "LongMethod")

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Project
import java.util.Properties

fun Project.configureApplication(
    android: BaseAppModuleExtension,
    supportedLanguages: List<String>,
    supportedLanguagesDebug: List<String> = supportedLanguages,
    appLabel: String,
    applicationId: String,
    devAppIdSuffix: String? = null,
    configureStages: Boolean = true,
) {
    with(android) {
        namespace = applicationId
        compileSdk = Sdks.compile

        androidResources.localeFilters.addAll(supportedLanguages)

        defaultConfig {
            this@defaultConfig.applicationId = applicationId
            minSdk = Sdks.min
            targetSdk = Sdks.target
            vectorDrawables.useSupportLibrary = true

            manifestPlaceholders["mainActivityClass"] = "$applicationId.MainActivity"
        }
        val keystorePropertiesFile = rootProject.file("keystore.properties")
        if (keystorePropertiesFile.exists()) {
            signingConfigs {
                getByName("debug") {
                    val keystoreProperties = Properties()
                    keystoreProperties.load(keystorePropertiesFile.inputStream())

                    storeFile =
                        rootProject.file(file(keystoreProperties.getProperty("keystoreFile")).name)
                    storePassword = keystoreProperties.getProperty("keystorePassword")
                    keyAlias = keystoreProperties.getProperty("keyAlias")
                    keyPassword = keystoreProperties.getProperty("keyPassword")
                }
            }
        }
        buildTypes {
            debug {
                buildConfigField(
                    "String[]",
                    "SUPPORTED_LANGUAGES",
                    "{ \"" + supportedLanguagesDebug.joinToString("\", \"") + "\" }"
                )
                applicationIdSuffix = devAppIdSuffix
                isPseudoLocalesEnabled = true
                multiDexEnabled = true
                isDefault = true
                resValue("string", "app_name", "$appLabel Debug")
            }
            release {
                buildConfigField(
                    "String[]",
                    "SUPPORTED_LANGUAGES",
                    "{ \"" + supportedLanguages.joinToString("\", \"") + "\" }"
                )
                multiDexEnabled = true
                isMinifyEnabled = true
                isShrinkResources = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt")
                )
                if (keystorePropertiesFile.exists()) {
                    signingConfig = signingConfigs.getByName("debug")
                }
                resValue("string", "app_name", appLabel)
            }
        }
        if (configureStages) {
            flavorDimensions.add(Flavours.byStaging.dimension)
            productFlavors {
                create(Flavours.byStaging.stage) {
                    dimension = Flavours.byStaging.dimension
                    isDefault = true
                }
                create(Flavours.byStaging.prod) {
                    dimension = Flavours.byStaging.dimension
                }
            }
        }
        packaging {
            resources {
                excludes.addAll(
                    listOf(
                        "/META-INF/{AL2.0,LGPL2.1}",
                        "META-INF/INDEX.LIST",
                        "META-INF/DEPENDENCIES",
                        // Duplicates:
                        // com.squareup.okhttp3:logging-interceptor:5.3.2/logging-interceptor-5.3.2.jar
                        // org.jspecify:jspecify:1.0.0/jspecify-1.0.0.jar
                        "META-INF/versions/9/OSGI-INF/MANIFEST.MF"
                    )
                )
            }
        }
        applicationVariants.all {
            outputs.all {
                val part1 = if (configureStages) productFlavors[0].name else ""
                val part2 = buildType.name
                (this as com.android.build.gradle.internal.api.BaseVariantOutputImpl).outputFileName =
                    "${applicationId}_${part1}_${part2}.apk"
            }
        }
        buildFeatures {
            buildConfig = true
        }
        lint {
            abortOnError = false
            checkReleaseBuilds = true
        }
    }
}
