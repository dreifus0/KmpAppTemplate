# KMP App Template

A KMP starter we use as the baseline for new projects. Android + iOS from a single codebase on Kotlin Multiplatform and Compose Multiplatform. Out of the box it ships the production patterns we layer on top of vanilla KMP: a two-level ViewModel architecture, MVU without a domain layer, Metro DI, persisted preferences, real networking, and TDD on pure functions.

## Tech stack

| Tech | Version | Purpose |
|---|---|---|
| Kotlin | 2.3.0 | language |
| Compose Multiplatform | 1.10.0 | UI on Android + iOS |
| Material3 | 1.10.0-alpha05 | design system |
| Navigation 3 | 1.0.0-alpha06 | navigation (regular / dialog / bottom sheet) |
| Metro DI | 0.10.2 | compile-time DI, no reflection |
| MetroX ViewModel | 0.10.2 | bridge between Metro and Compose ViewModel |
| MVU Core | 0.3.0 | State / Event / Command / Effect pattern |
| Ktor Client | 3.4.0 | HTTP (OkHttp on Android, Darwin on iOS) |
| Kotlin Serialization | 1.8.0 | JSON DTOs |
| Multiplatform Settings | 1.2.0 | key/value persistence (SharedPreferences / NSUserDefaults) |
| Coil 3 | 3.0.4 | image loading (over Ktor) |
| Napier | 2.7.1 | KMP logging, debug-only |
| Coroutines | 1.10.1 | async |
| AGP / Gradle | 8.11.2 / 8.14.3 | build |

## What's inside

- **Onboarding → Root routing** through a top-level MVU (`AppViewModel`) with a persisted flag
- **Pokémon list / detail** against the real PokeAPI with `LceState` (Loading / Content / Error / Refreshing) and nested navigation
- **Settings**: theme picker (Light / Dark / System) and onboarding reset — both go through a single source of truth (`ThemeRepository`, `OnboardingRepository`)
- **A test next to every Update** — pure functions, no mocks

## Architecture map

```
App(platformDeps)
└── AppViewModel  (vanilla VM, the only one not wired through Metro)
    ├── createGraphFactory<AppGraph.Factory>().create(settings, isDebug, platformName, httpClient)
    ├── MVU: AppState{screen, themeMode}
    ├── observe themeRepository.themeMode  → AppEvent.ThemeModeChanged
    ├── observe onboardingRepository.onboardingReset → AppEvent.ShowOnboarding
    └── routes by AppState.screen:
        ├── Loading       → empty Surface
        ├── Onboarding    → OnboardingScreen → onComplete: AppEvent.OnboardingCompleted
        └── Root          → RootScreen
            └── RootViewModel  (Metro-injected)
                └── tabs:
                    ├── Pokémon  → PokemonListScreen → nav → PokemonDetailScreen(name)
                    └── Settings → theme picker + reset onboarding
```

## Module layout

```
composeApp/                            — Android app and iOS framework
  app/                                 — AppViewModel, MVU, top-level routing
  di/                                  — AppGraph + PlatformDependencies (expect/actual)
  root/                                — RootViewModel (tabs) + RootScreen
  navigation/                          — tabs (Pokémon / Settings)

modules/
├── data/
│   └── pokemon/                       — PokeApi (Ktor), DTOs, PokemonRepository
└── features/
│   ├── onboarding/                    — MVU onboarding, OnboardingRepository
│   ├── pokemon/                       — MVU list + detail
│   └── settings/                      — ThemeRepository, ThemeMode, MVU settings
└── utils/
    ├── arch/                          — LceState + DI tokens (IsDebug, PlatformName)
    ├── core-extensions/               — Kotlin/Compose extensions
    ├── core-navigation/               — NavController, screens, tabs
    ├── helpers/                       — shared utilities
    ├── network/                       — KmpHttpClient, ApiConfig, ApiError
    └── uikit/                         — theme, typography, shapes

includedBuild/
├── gradle-configs/                    — convention plugins (kmp-library / kmp-compose-library / kmp-compose-application)
└── shared-consts/                     — shared build constants
```

## Architecture in 6 rules

1. **Two-level ViewModels.** `AppViewModel` builds the DI graph and owns top-level routing. Every other VM goes through Metro: `@Inject @ContributesIntoMap(AppScope::class) @ViewModelKey(VM::class)`.
2. **MVU, no domain layer.** `State` / `Event` / `Command` / `Effect` live in separate files. Business logic sits in `commandhandlers/` next to its VM. No use-cases.
3. **Metro DI.** Repositories: `@Inject @SingleIn(AppScope::class)`. ViewModels via `metroViewModel<VM>()`.
4. **PlatformDependencies via expect/actual** — the single seam between common code and the platform. Android takes `Context`, iOS uses an empty constructor.
5. **Repository = single source of truth.** `ThemeRepository` owns theme end-to-end; `OnboardingRepository` owns the onboarding flag. Nothing writes to `Settings` directly bypassing a repo.
6. **TDD on Update.** Update is a pure function → trivial to test without mocks. CommandHandler tests are priority 2.

## Getting started

Requires JDK 17+, Android SDK 24+, Xcode 16+ for iOS.

```bash
# Android (debug APK)
./gradlew :composeApp:assembleDebug

# Verify compilation on both targets
./gradlew :composeApp:compileDebugKotlinAndroid :composeApp:compileKotlinIosSimulatorArm64

# Run all tests
./gradlew allTests

# iOS — open iosApp/iosApp.xcodeproj in Xcode and run
```

## Adding a feature module

1. **`settings.gradle.kts`** — add `":modules:features:<name>"` to `include(...)`.
2. **`modules/features/<name>/build.gradle.kts`**:
    ```kotlin
    plugins {
        id("com.dreifus.kmp-compose-library")
        alias(libs.plugins.metro)
    }
    android { namespace = "com.dreifus.app.features.<name>" }
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

                implementation(projects.modules.utils.arch)
                implementation(projects.modules.utils.uikit)
                implementation(projects.modules.utils.coreNavigation)
            }
            commonTest.dependencies { implementation(libs.kotlin.test) }
        }
    }
    ```
3. **Inner layout**:
    ```
    src/commonMain/kotlin/com/dreifus/app/features/<name>/
    ├── data/                          — Repository, API clients, mapping (optional)
    └── presentation/
        ├── <Name>State.kt             — one file each
        ├── <Name>Event.kt
        ├── <Name>Command.kt
        ├── <Name>Effect.kt
        ├── <Name>Update.kt            — pure
        ├── <Name>ViewModel.kt         — Metro-injected
        ├── commandhandlers/
        └── ui/
            ├── <Name>Screen.kt        — RegularScreen or RootScreenWithTabs
            └── <Name>Content.kt       — pure composable taking state + callbacks
    src/commonTest/kotlin/.../<Name>UpdateTest.kt
    ```
4. **`composeApp/build.gradle.kts`** — `implementation(projects.modules.features.<name>)`.

## License

MIT.
