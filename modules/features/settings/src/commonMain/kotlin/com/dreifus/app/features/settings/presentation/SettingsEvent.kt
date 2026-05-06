package com.dreifus.app.features.settings.presentation

import com.dreifus.app.features.settings.data.ThemeMode

sealed interface SettingsEvent {
    data object ResetOnboardingClicked : SettingsEvent
    data class ThemeSelected(val mode: ThemeMode) : SettingsEvent
    data class ThemeModeLoaded(val mode: ThemeMode) : SettingsEvent
    data object OnboardingWasReset : SettingsEvent
}
