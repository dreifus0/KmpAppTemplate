# KMP App Template

Базовый KMP-шаблон для будущих проектов. Android + iOS из одной кодовой базы на Kotlin Multiplatform и Compose Multiplatform. Из коробки демонстрирует продакшен-патерны, которые мы используем поверх голого KMP: двухуровневую VM-архитектуру, MVU без domain-слоя, Metro DI, persisted preferences, реальную сеть, TDD на чистых функциях.

## Tech stack

| Tech | Version | Зачем |
|---|---|---|
| Kotlin | 2.3.0 | язык |
| Compose Multiplatform | 1.10.0 | UI на Android + iOS |
| Material3 | 1.10.0-alpha05 | дизайн-система |
| Navigation 3 | 1.0.0-alpha06 | навигация (regular / dialog / bottom sheet) |
| Metro DI | 0.10.2 | компайл-тайм DI без рефлексии |
| MetroX ViewModel | 0.10.2 | мост Metro ↔ Compose ViewModel |
| MVU Core | 0.3.0 | паттерн State / Event / Command / Effect |
| Ktor Client | 3.4.0 | HTTP (OkHttp на Android, Darwin на iOS) |
| Kotlin Serialization | 1.8.0 | JSON DTO |
| Multiplatform Settings | 1.2.0 | key/value persistence (SharedPreferences / NSUserDefaults) |
| Coil 3 | 3.0.4 | загрузка картинок (через Ktor) |
| Napier | 2.7.1 | KMP логирование, debug-only |
| Coroutines | 1.10.1 | async |
| AGP / Gradle | 8.11.2 / 8.14.3 | сборка |

## Что внутри

- **Onboarding → Root routing** через MVU верхнего уровня (`AppViewModel`), persisted флаг
- **Pokémon list / detail** на реальном PokeAPI с `LceState` (Loading / Content / Error / Refreshing) и вложенной навигацией
- **Settings**: переключатель темы (Light / Dark / System) и сброс онбординга — оба через единый source of truth (`ThemeRepository`, `OnboardingRepository`)
- **Тесты на каждый Update** — pure-функции, без mock'ов

## Архитектурная карта

```
App(platformDeps)
└── AppViewModel  (vanilla VM, единственная не через Metro)
    ├── createGraphFactory<AppGraph.Factory>().create(settings, isDebug, platformName, httpClient)
    ├── MVU: AppState{screen, themeMode}
    ├── observe themeRepository.themeMode  → AppEvent.ThemeModeChanged
    ├── observe onboardingRepository.onboardingReset → AppEvent.ShowOnboarding
    └── routing по AppState.screen:
        ├── Loading       → пустой Surface
        ├── Onboarding    → OnboardingScreen → onComplete: AppEvent.OnboardingCompleted
        └── Root          → RootScreen
            └── RootViewModel  (Metro-injected)
                └── табы:
                    ├── Pokémon  → PokemonListScreen → nav → PokemonDetailScreen(name)
                    └── Settings → theme picker + reset onboarding
```

## Структура модулей

```
composeApp/                            — Android-приложение и iOS-фреймворк
  app/                                 — AppViewModel, MVU, routing верхнего уровня
  di/                                  — AppGraph + PlatformDependencies (expect/actual)
  root/                                — RootViewModel (табы) + RootScreen
  navigation/                          — табы (Pokémon / Settings)

modules/
├── data/
│   └── pokemon/                       — PokeApi (Ktor), DTO, PokemonRepository
└── features/
│   ├── onboarding/                    — MVU-онбординг, OnboardingRepository
│   ├── pokemon/                       — MVU list + detail
│   └── settings/                      — ThemeRepository, ThemeMode, MVU-settings
└── utils/
    ├── arch/                          — LceState + DI tokens (IsDebug, PlatformName)
    ├── core-extensions/               — Kotlin/Compose extensions
    ├── core-navigation/               — NavController, экраны, табы
    ├── helpers/                       — общие утилиты
    ├── network/                       — KmpHttpClient, ApiConfig, ApiError
    └── uikit/                         — тема, типографика, формы

includedBuild/
├── gradle-configs/                    — convention-плагины (kmp-library / kmp-compose-library / kmp-compose-application)
└── shared-consts/                     — общие build-константы
```

## Архитектура — 6 правил

1. **Двухуровневая VM**. `AppViewModel` создаёт DI-граф и рулит routing'ом верхнего уровня. Все остальные VM — через Metro: `@Inject @ContributesIntoMap(AppScope::class) @ViewModelKey(VM::class)`.
2. **MVU без domain**. `State` / `Event` / `Command` / `Effect` — отдельные файлы. Бизнес-логика — в `commandhandlers/` рядом с VM. Никаких usecases.
3. **Metro DI**. Repositories: `@Inject @SingleIn(AppScope::class)`. ViewModels через `metroViewModel<VM>()`.
4. **PlatformDependencies через expect/actual** — единственный шов между общим кодом и платформой. На Android прокидывается `Context`, на iOS — пустой конструктор.
5. **Repository = single source of truth**. `ThemeRepository` владеет темой целиком; `OnboardingRepository` — флагом онбординга. Никаких прямых записей в `Settings` мимо репо.
6. **TDD на Update**. Update — чистая функция → элементарно тестируется без mock'ов. CommandHandler-тесты — приоритет 2.

## Getting Started

Требуется JDK 17+, Android SDK 24+, Xcode 16+ для iOS.

```bash
# Android (debug APK)
./gradlew :composeApp:assembleDebug

# Проверить компиляцию обоих таргетов
./gradlew :composeApp:compileDebugKotlinAndroid :composeApp:compileKotlinIosSimulatorArm64

# Все тесты
./gradlew allTests

# iOS — открыть iosApp/iosApp.xcodeproj в Xcode и запустить
```

## Как добавить feature-модуль

1. **`settings.gradle.kts`** — добавить строку `":modules:features:<name>"` в `include(...)`.
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
3. **Структура внутри**:
    ```
    src/commonMain/kotlin/com/dreifus/app/features/<name>/
    ├── data/                          — Repository, API-клиенты, маппинг (опционально)
    └── presentation/
        ├── <Name>State.kt             — отдельный файл
        ├── <Name>Event.kt
        ├── <Name>Command.kt
        ├── <Name>Effect.kt
        ├── <Name>Update.kt            — pure
        ├── <Name>ViewModel.kt         — Metro-injected
        ├── commandhandlers/
        └── ui/
            ├── <Name>Screen.kt        — RegularScreen или RootScreenWithTabs
            └── <Name>Content.kt       — pure composable от state + callback
    src/commonTest/kotlin/.../<Name>UpdateTest.kt
    ```
4. **`composeApp/build.gradle.kts`** — `implementation(projects.modules.features.<name>)`.

## License

MIT.
