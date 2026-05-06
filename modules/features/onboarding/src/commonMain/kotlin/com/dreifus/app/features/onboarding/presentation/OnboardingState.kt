package com.dreifus.app.features.onboarding.presentation

data class OnboardingState(
    val step: Step = Step.Welcome,
) {
    enum class Step { Welcome, Done }
}
