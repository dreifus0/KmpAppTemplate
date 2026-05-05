package com.dreifus.app.features.settings.presentation

import com.yavorcool.mvucore.Next
import com.yavorcool.mvucore.Update

class SettingsUpdate : Update<SettingsState, SettingsEvent, SettingsCommand, SettingsEffect> {

    override fun update(
        state: SettingsState,
        event: SettingsEvent,
    ): Next<SettingsState, SettingsCommand, SettingsEffect> = when (event) {
        SettingsEvent.ResetOnboardingClicked -> Next(
            state = state,
            command = SettingsCommand.ResetOnboarding,
        )

        is SettingsEvent.ThemeSelected -> Next(
            state = state,
            command = SettingsCommand.SetThemeMode(event.mode),
        )

        is SettingsEvent.ThemeModeLoaded -> Next(
            state = state.copy(themeMode = event.mode),
        )

        SettingsEvent.OnboardingWasReset -> Next(state)
    }
}
