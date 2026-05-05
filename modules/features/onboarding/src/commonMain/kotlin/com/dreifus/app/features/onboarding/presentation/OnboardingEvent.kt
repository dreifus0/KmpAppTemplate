package com.dreifus.app.features.onboarding.presentation

sealed interface OnboardingEvent {
    data object WelcomeContinueClicked : OnboardingEvent
    data object DoneFinishClicked : OnboardingEvent
    data object OnboardingSaved : OnboardingEvent
}
