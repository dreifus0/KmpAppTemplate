package com.dreifus.app.app

import com.dreifus.app.features.settings.data.ThemeMode

sealed interface AppEvent {
    data object Init : AppEvent
    data object ShowOnboarding : AppEvent
    data object ShowRoot : AppEvent
    data object OnboardingCompleted : AppEvent
    data class ThemeModeChanged(val mode: ThemeMode) : AppEvent
}
