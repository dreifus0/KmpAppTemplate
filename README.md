# KMP App Template

Шаблон для разработки Android приложений с возможностью расширения на iOS через Kotlin Multiplatform.

## Структура проекта

```
KmpAppTemplate/
├── android/
│   └── app/                       # Основной модуль Android приложения
│
├── modules/utils/                 # Утилитные модули
│   ├── core-navigation2/          # Навигация на базе Jetpack Navigation 3
│   ├── destination-collector/     # KSP процессор для сбора destinations
│   ├── di-common/                 # Dependency Injection на базе Metro
│   ├── ktorfit-di/                # KSP процессор для Ktorfit интеграции
│   ├── metro-android-ksp/         # KSP процессор для Metro DI
│   └── uikit/                     # Модуль дизайн-системы
│
├── includedBuild/                 # Convention plugins
│   ├── gradle-configs/            # Gradle convention plugins
│   └── shared-consts/             # Общие константы для сборки
│
├── commonBuild/                   # Базовый settings plugin (repositories)
│
├── gradle/
│   └── libs.versions.toml         # Версии зависимостей
│
├── build.gradle.kts               # Корневой build файл
├── settings.gradle.kts            # Настройки проекта
└── gradle.properties              # Gradle свойства
```

## Технологический стек

- **Gradle** 9.0
- **Kotlin** 2.1.0+
- **Android Gradle Plugin** 8.7.3
- **Jetpack Compose** с Material3
- **Navigation 3** - навигация между экранами
- **Metro DI** - Dependency Injection framework
- **KSP** - Kotlin Symbol Processing для кодогенерации
- **Ktorfit** - типизированный HTTP клиент

## Утилитные модули

### core-navigation2
Модуль для навигации с поддержкой:
- Regular destinations (обычные экраны)
- Dialog destinations (диалоги)
- Bottom Sheet destinations (нижние панели)
- Интеграция с Metro DI

### di-common
Модуль для Dependency Injection:
- Интеграция с Metro DI framework
- Base классы для Activity и Application
- ViewModel factory с DI поддержкой

### destination-collector
KSP процессор для автоматического сбора destination классов.

### ktorfit-di
KSP процессор для генерации DI модулей для Ktorfit API клиентов.

### metro-android-ksp
KSP процессор для генерации кода Metro DI для Android компонентов.

### uikit
Модуль дизайн-системы с темой приложения и общими компонентами.

## Начало работы

### Требования
- JDK 17 или выше
- Android Studio Ladybug или выше
- Android SDK 24+

### Сборка проекта

```bash
./gradlew :android:app:assembleDebug
```

## Лицензия

MIT License
