package com.dreifus.app.features.settings.presentation

import com.dreifus.app.features.settings.data.ThemeMode

data class SettingsState(
    val themeMode: ThemeMode = ThemeMode.System,
)
