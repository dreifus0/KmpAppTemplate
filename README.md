# KMP App Template

A template for developing Android applications with the ability to extend to iOS through Kotlin
Multiplatform.

## Project Structure

```
KmpAppTemplate/
├── android/
│   └── app/                       # Main Android application module
│
├── modules/utils/                 # Utility modules
│   ├── core-navigation/           # Navigation based on Jetpack Navigation 3
│   ├── screen-collector/          # KSP processor for collecting navigation screens
│   ├── di-common/                 # Dependency Injection based on Metro
│   ├── ktorfit-di/                # KSP processor for Ktorfit integration
│   ├── metro-android-ksp/         # KSP processor for Metro DI
│   └── uikit/                     # Design system module
│
├── includedBuild/                 # Convention plugins
│   ├── gradle-configs/            # Gradle convention plugins
│   └── shared-consts/             # Shared build constants
│
├── commonBuild/                   # Base settings plugin (repositories)
│
├── gradle/
│   └── libs.versions.toml         # Dependency versions
│
├── build.gradle.kts               # Root build file
├── settings.gradle.kts            # Project settings
└── gradle.properties              # Gradle properties
```

## Technology Stack

- **Gradle** 9.0
- **Kotlin** 2.1.0+
- **Android Gradle Plugin** 8.7.3
- **Jetpack Compose** with Material3
- **Navigation 3** - screen navigation
- **Metro DI** - Dependency Injection framework
- **KSP** - Kotlin Symbol Processing for code generation
- **Ktorfit** - typed HTTP client

## Utility Modules

### core-navigation

Navigation module with support for:

- Regular screens
- Dialog screens
- Bottom Sheet screens
- Integration with Metro DI

### di-common

Dependency Injection module:

- Integration with Metro DI framework
- Base classes for Activity and Application
- ViewModel factory with DI support

### screen-collector

KSP processor for automatic collection of navigation screen classes.

### ktorfit-di

KSP processor for generating DI modules for Ktorfit API clients.

### metro-android-ksp

KSP processor for generating Metro DI code for Android components.

### uikit

Design system module with app theme and common components.

## Getting Started

### Requirements

- JDK 17 or higher
- Android Studio Ladybug or higher
- Android SDK 24+

### Building the Project

```bash
./gradlew :android:app:assembleDebug
```

## License

MIT License
