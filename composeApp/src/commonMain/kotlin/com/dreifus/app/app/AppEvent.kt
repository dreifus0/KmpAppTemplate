package com.dreifus.app.app

sealed interface AppEvent {
    data object Init : AppEvent
    data object ShowOnboarding : AppEvent
    data object ShowRoot : AppEvent
    data object OnboardingCompleted : AppEvent
}
