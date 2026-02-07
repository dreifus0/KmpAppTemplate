# KMP App Template

A cross-platform application template built with **Kotlin Multiplatform** + **Compose Multiplatform** for Android and iOS.

The goal is to maximize code sharing between platforms. All business logic, navigation, UI and DI live in `commonMain`. Platform-specific code is kept to an absolute minimum: `MainActivity` on Android, `MainViewController` on iOS.

## Tech Stack

| Technology | Version | Purpose |
|---|---|---|
| Kotlin | 2.3.0 | Language |
| Compose Multiplatform | 1.10.0 | UI framework (Android + iOS) |
| Material3 | 1.10.0-alpha05 | Design system |
| Navigation 3 | 1.0.0-alpha06 | Screen navigation |
| Metro DI | 0.10.2 | Dependency Injection |
| MVU Core | 0.3.0 | Model-View-Update architecture |
| AGP | 8.11.2 | Android build |
| Gradle | 8.14.3 | Build system |

## Project Structure

```
KmpAppTemplate/
├── composeApp/                        # Main KMP application module
│   ├── src/commonMain/                #   Shared code (UI, logic, navigation)
│   │   └── kotlin/com/dreifus/app/
│   │       ├── App.kt                 #     Root: theme + navigation + tabs
│   │       ├── counter/               #     "Counter" feature (MVU example)
│   │       │   ├── mvu/               #       State, Event, Update, Handler
│   │       │   └── ui/                #       CounterScreen
│   │       ├── stub/ui/               #     Stub feature (second tab)
│   │       └── navigation/            #     Tab configuration
│   ├── src/androidMain/               #   Android: MainActivity
│   └── src/iosMain/                   #   iOS: MainViewController
│
├── iosApp/                            # Xcode project for iOS
│
├── modules/utils/                     # Reusable KMP modules
│   ├── core-navigation/               #   Navigation (regular, dialog, bottomsheet, tabs)
│   ├── di-common/                     #   DI infrastructure (Metro, ViewModel)
│   ├── uikit/                         #   Design system (theme, components, icons)
│   ├── helpers/                       #   Utilities (SnackbarManager)
│   ├── core-extensions/               #   Kotlin extensions
│   └── screen-collector/              #   KSP processor for navigation
│
├── includedBuild/                     # Gradle convention plugins
│   ├── gradle-configs/                #   KMP Compose Application/Library plugins
│   └── shared-consts/                 #   Shared build constants
│
├── commonBuild/                       # Settings plugin (repositories)
└── gradle/libs.versions.toml          # Version catalog
```

## Architecture

- **Maximize shared code** — UI, navigation, DI and business logic all live in `commonMain`
- **Minimize platform code** — only entry points (`MainActivity` / `MainViewController`)
- **MVU (Model-View-Update)** — unidirectional data flow: State, Event, Command, Effect
- **Feature-based structure** — each feature in its own package (`counter/`, `stub/`)
- **Navigation 3 + tabs** — `RootScreenWithTabs` with bottom navigation

## Getting Started

### Requirements

- JDK 17+
- Android Studio / IntelliJ IDEA
- Xcode 16+ (for iOS)
- Android SDK 24+

### Build

```bash
# Android
./gradlew :composeApp:assembleDebug

# iOS (simulator)
./gradlew :composeApp:compileKotlinIosSimulatorArm64
# Then open iosApp/ in Xcode and run

# Verify both platforms
./gradlew :composeApp:compileDebugKotlinAndroid :composeApp:compileKotlinIosSimulatorArm64
```

## License

MIT License
