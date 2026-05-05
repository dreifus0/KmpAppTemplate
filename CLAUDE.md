# KmpAppTemplate

Базовый KMP-шаблон для будущих проектов команды (Android + iOS из общего кода). Стартовая точка с уже настроенными архитектурными паттернами: двухуровневая VM, MVU без domain, Metro DI, persisted preferences, реальный сетевой слой, тесты.

## Стек

- Kotlin 2.3.0, Compose Multiplatform 1.10.0, Material3 1.10.0-alpha05
- Metro 0.10.2 (DI), MetroX ViewModel 0.10.2
- mvucore 0.3.0 (Store / Update / filteringHandler)
- Navigation 3 1.0.0-alpha06 (regular / dialog / bottom-sheet через NavControllersHolder)
- Ktor 3.4.0 (OkHttp Android, Darwin iOS) + ktor-logging
- multiplatform-settings 1.2.0 (key/value persistence)
- Coil 3.0.4 (картинки) с network-ktor3
- Napier 2.7.1 (debug-only логирование)
- Targets: Android (min 24, target 36), iOS (arm64, simulatorArm64)

## Модульная структура

```
composeApp/                              — приложение
  src/commonMain/kotlin/com/dreifus/app/
  ├── app/                              — AppViewModel + MVU + commandhandlers
  ├── di/                               — AppGraph + PlatformDependencies (expect)
  ├── root/                             — RootViewModel + RootScreen
  └── navigation/                       — табы

modules/
├── data/pokemon/                       — PokeApi, DTOs, PokemonRepository
├── features/
│   ├── onboarding/                     — flag persistence + 2-шаговый MVU
│   ├── pokemon/                        — list + detail
│   └── settings/                       — ThemeRepository + theme picker + reset
└── utils/
    ├── arch/                           — LceState + DI tokens (IsDebug, PlatformName)
    ├── core-extensions/                — Kotlin/Compose extensions
    ├── core-navigation/                — NavController, экраны, табы
    ├── helpers/                        — общие утилиты
    ├── network/                        — KmpHttpClient, ApiConfig, ApiError
    └── uikit/                          — тема, типографика, формы

includedBuild/
├── gradle-configs/                     — convention plugins
└── shared-consts/                      — общие build-константы
```

## Convention plugins (`includedBuild/gradle-configs`)

- `com.dreifus.kmp-library` — KMP без Compose. Для data-модулей.
- `com.dreifus.kmp-compose-library` — KMP + Compose + compose-compiler. Для feature/utils.
- `com.dreifus.kmp-compose-application` — KMP + Android application + Compose. Для composeApp (хотя сейчас composeApp использует aliases напрямую).

Metro plugin применяется per-module через `alias(libs.plugins.metro)`.

## Создание нового feature-модуля

1. Создать `modules/features/<name>/build.gradle.kts`:
   ```kotlin
   plugins {
       id("com.dreifus.kmp-compose-library")
       alias(libs.plugins.metro)
   }
   android { namespace = "com.dreifus.app.features.<name>" }
   kotlin {
       sourceSets {
           commonMain.dependencies {
               implementation(libs.mvucore)
               implementation(libs.compose.runtime)
               implementation(libs.compose.foundation)
               implementation(libs.compose.material3)
               implementation(libs.compose.ui)
               implementation(libs.androidx.lifecycle.viewmodelCompose)
               implementation(libs.metrox.viewmodel.compose)

               implementation(projects.modules.utils.arch)
               implementation(projects.modules.utils.uikit)
               implementation(projects.modules.utils.coreNavigation)
           }
           commonTest.dependencies { implementation(libs.kotlin.test) }
       }
   }
   ```
2. Добавить `:modules:features:<name>` в `settings.gradle.kts`.
3. Структура:
   ```
   src/commonMain/kotlin/com/dreifus/app/features/<name>/
   ├── data/                            — Repository, API, маппинг (если нужно)
   └── presentation/
       ├── <Name>State.kt               — отдельный файл
       ├── <Name>Event.kt
       ├── <Name>Command.kt
       ├── <Name>Effect.kt
       ├── <Name>Update.kt              — pure fn
       ├── <Name>ViewModel.kt           — Metro-injected
       ├── commandhandlers/
       └── ui/
           ├── <Name>Screen.kt          — RegularScreen или RootScreenWithTabs
           └── <Name>Content.kt         — pure composable
   src/commonTest/kotlin/.../<Name>UpdateTest.kt
   ```
4. Подключить в `composeApp/build.gradle.kts`: `implementation(projects.modules.features.<name>)`.

## MVU-паттерн (mvucore)

- **State** — immutable data class
- **Event** — sealed interface, действия пользователя/системы
- **Command** — sealed interface, side-effect запросы (загрузка, сохранение, навигация)
- **Effect** — sealed interface, one-shot UI-эффекты (toast, scroll, навигация)
- **Update** — `(State, Event) → Next<State, Command, Effect>`. Чистая функция, без side-effects
- **CommandHandler** — обработчик команд через `filteringHandler<MyCommand, ...>`, заменяет usecases

Domain-слоя нет. Бизнес-логика — в CommandHandler рядом с VM.

## DI (Metro)

- `@DependencyGraph(AppScope::class)` — корневой граф (`AppGraph`).
- `AppGraph.Factory` с `@Provides` для платформенных deps (`Settings`, `IsDebug`, `PlatformName`, `HttpClient`).
- `AppGraph` наследует `ViewModelGraph` (Metro-X) → `metroViewModelFactory` собирает все `@Inject` ViewModel-ы автоматически.

| Аннотация | Назначение |
|---|---|
| `@Inject` | Конструкторная инжекция |
| `@SingleIn(AppScope::class)` | Синглтон в AppScope (для repositories) |
| `@ContributesBinding(AppScope::class)` | Привязка impl к интерфейсу |
| `@ContributesIntoMap(AppScope::class)` + `@ViewModelKey(VM::class)` | ViewModel в Metro map factory |
| `@Provides` (в Factory) | Ручной providing платформенных deps |

### Как Metro создаёт ViewModel

```
@Inject @ViewModelKey(MyVM::class) @ContributesIntoMap(AppScope::class)
class MyVM(repo: Repo) : ViewModel()
   ↓ Metro codegen
metroViewModelFactory.viewModelProviders[MyVM::class] = Provider { MyVM(repo) }
   ↓ composable
metroViewModel<MyVM>(key: String? = null)  // ключ — для разных экземпляров одного типа
```

`metroViewModel<T>(key=name)` нужен, например, для `PokemonDetailViewModel` — даёт уникальный экземпляр на покемона.

### DI-правила

1. Repositories — всегда через `@Inject @SingleIn(AppScope::class)`. Никаких ручных `val repo = SomeRepo(...)`.
2. ViewModel-ы (кроме AppViewModel) — `@Inject @ViewModelKey @ContributesIntoMap`. Создаются через `metroViewModel<T>()`. Без `@ViewModelKey` + `@ContributesIntoMap` — runtime crash.
3. **AppViewModel — единственное исключение**. Создаётся через `viewModel { AppViewModel(platformDeps) }`. Принимает `PlatformDependencies` и сама строит граф через `createGraphFactory<AppGraph.Factory>().create(...)`.
4. Никаких prop-drilling DI через composable-параметры.
5. Metro plugin (`alias(libs.plugins.metro)`) — обязателен в каждом модуле с `@Inject`.

## ViewModel-архитектура

| Уровень | VM | Создание | Ответственность |
|---|---|---|---|
| App | `AppViewModel` | `viewModel { AppViewModel(deps) }` | DI-граф + routing верхнего уровня (Loading / Onboarding / Root). Без бизнес-логики фич. |
| Feature | `OnboardingVM`, `PokemonListVM`, `PokemonDetailVM`, `SettingsVM`, `RootViewModel` | `metroViewModel<T>()` | MVU фичи / табов |

`AppViewModel` — единственная не-Metro VM, потому что она *создаёт* граф.

## Ownership данных

- Feature module владеет своими данными. `OnboardingRepository` живёт в `features/onboarding/data/`, `ThemeRepository` — в `features/settings/data/`.
- Single source of truth: `ThemeRepository.themeMode` для темы, `OnboardingRepository` для флага и `onboardingReset` SharedFlow.
- `composeApp` *координирует*, но не владеет: AppViewModel наблюдает Flow'ы и переключает экраны.

## Модульные границы

- Feature-модули зависят от `utils/*` и могут зависеть друг от друга (`settings → onboarding` для reset).
- `composeApp` зависит от всех feature-модулей.
- **Feature-модули НЕ зависят от composeApp.**

| Артефакт | Место |
|---|---|
| Repository (`@Inject @SingleIn`) | `modules/features/<name>/data/` или `modules/data/<name>/` |
| ViewModel (`@Inject @ViewModelKey @ContributesIntoMap`) | `modules/features/<name>/presentation/` |
| Screen composable | `modules/features/<name>/presentation/ui/` |
| CommandHandlers | `modules/features/<name>/presentation/commandhandlers/` |
| App-level MVU | `composeApp/.../app/` |
| DI-граф, PlatformDependencies | `composeApp/.../di/` |

## Navigation

- Три стека через `NavControllersHolder`: `regular`, `dialog`, `bottomSheet`.
- Типы экранов: `RegularScreen`, `DialogScreen`, `BottomSheetScreen`, `RootScreenWithTabs` (для табов).
- Навигация: `Navigation.regular.navigate(SomeScreen())`, `Navigation.regular.pop()`.
- Из MVU — через `Effect` → `LaunchedEffect` собирает `viewModel.effects` → `nav.navigate(...)`. См. `PokemonListScreen` как пример.
- Табы: `TabNavState` хранит per-tab backstack, переключение между табами не теряет глубину.

## Theme system

- `ThemeMode { Light, Dark, System }` — `modules/features/settings/data/`.
- `ThemeRepository @Inject @SingleIn(AppScope::class)` — `StateFlow<ThemeMode>`, persistence через `Settings`.
- `AppViewModel` подписан на `themeRepository.themeMode` → `AppEvent.ThemeModeChanged` → `AppState.themeMode`.
- `App.kt` резолвит `darkTheme: Boolean`:
   ```kotlin
   val isDark = when (appState.themeMode) {
       ThemeMode.Light  -> false
       ThemeMode.Dark   -> true
       ThemeMode.System -> isSystemInDarkTheme()
   }
   ```
- `SettingsViewModel` мутирует через `themeRepo.setThemeMode(mode)` — флоу автоматически донотифицирует и App, и Settings.

## Onboarding routing

- `AppState.Screen { Loading, Onboarding, Root }`.
- На `AppEvent.Init` → `AppCommand.CheckOnboardingStatus` → `OnboardingRepository.isOnboardingCompleted()` → `ShowRoot` / `ShowOnboarding`.
- `OnboardingRepository.resetOnboarding()` чистит флаг и эмитит `onboardingReset` SharedFlow → `AppViewModel` ловит → `AppEvent.ShowOnboarding` → app возвращается на онбординг.
- `OnboardingScreen(onComplete)` — callback дёргается из `OnboardingEffect.Completed` → `AppEvent.OnboardingCompleted` → `Screen.Root`.

## Persistence (multiplatform-settings)

- `Settings` экземпляр живёт в `PlatformDependencies` (SharedPreferences на Android, NSUserDefaults на iOS). Передаётся в `AppGraph.Factory`.
- Repositories инжектят `Settings` напрямую (`OnboardingRepository`, `ThemeRepository`).
- Чтобы добавить новый ключ — создать новый Repository рядом с фичей, инжектнуть `Settings`, добавить accessor в `AppGraph` если нужен из `AppViewModel`.

## Сетевой слой (`modules/utils/network`)

- `ApiConfig(baseUrl, isDebug)` — конфиг.
- `createHttpClient(config): HttpClient` — Ktor с `ContentNegotiation` (JSON), `Logging` если `isDebug`, default `url(config.baseUrl)`.
- `expect fun httpClientEngine(): HttpClientEngine` — actuals: OkHttp на Android, Darwin на iOS.
- `ApiError` — sealed (Network, Http, Serialization, Unknown). `Throwable.toApiError()` маппер.
- HttpClient — singleton в DI, провайдится из `AppViewModel` через `@Provides httpClient` в `AppGraph.Factory`.

Чтобы добавить новый endpoint — создать API class в `modules/data/<name>/`, инжектнуть `HttpClient`, использовать `client.get(...)` / `client.post(...)`.

## Testing — TDD

- **Update-тесты — приоритет 1.** Update — pure-функция, тестируется без mock'ов и dispatcher'ов. Каждый Event → проверка `next.state` + `next.commands` + `next.effects`. Лежат в `commonTest/.../<Feature>UpdateTest.kt`.
- **CommandHandler-тесты — приоритет 2.** Через fake-репозитории.
- Каждая новая фича включает Update-тесты в Definition of Done.
- Запуск: `./gradlew allTests`.

## Стиль кода Kotlin

- Корутины: `CancellationException` всегда пробрасывать (`catch (e: CancellationException) { throw e }` перед общим `catch`).
- Идиоматичный Kotlin: `?.let { }`, scope functions, trailing commas.
- Composable-функции без суффикса `View`.
- DTO в подпакете `dto/`, рядом mapper'ы `*.toDomain()`.

## Сборка

```bash
./gradlew :composeApp:assembleDebug                                                  # Android APK
./gradlew :composeApp:compileDebugKotlinAndroid :composeApp:compileKotlinIosSimulatorArm64
./gradlew allTests
```

iOS: открыть `iosApp/iosApp.xcodeproj` в Xcode.
