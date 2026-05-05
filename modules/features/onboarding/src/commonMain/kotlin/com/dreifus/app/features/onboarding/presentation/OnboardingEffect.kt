package com.dreifus.app.features.onboarding.presentation

sealed interface OnboardingEffect {
    data object Completed : OnboardingEffect
}
