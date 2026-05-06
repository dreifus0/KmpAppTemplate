package com.dreifus.app.features.settings.presentation

import com.dreifus.app.features.settings.data.ThemeMode

sealed interface SettingsCommand {
    data object ResetOnboarding : SettingsCommand
    data class SetThemeMode(val mode: ThemeMode) : SettingsCommand
}
