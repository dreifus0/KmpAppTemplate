package com.dreifus.app.app

import com.dreifus.app.features.settings.data.ThemeMode

data class AppState(
    val screen: Screen = Screen.Loading,
    val themeMode: ThemeMode = ThemeMode.System,
) {
    enum class Screen { Loading, Onboarding, Root }
}
