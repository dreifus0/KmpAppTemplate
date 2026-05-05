package com.dreifus.app.features.onboarding.presentation

sealed interface OnboardingCommand {
    data object SaveCompleted : OnboardingCommand
}
