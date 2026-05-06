package com.dreifus.app.features.onboarding.presentation

import com.yavorcool.mvucore.Next
import com.yavorcool.mvucore.Update

class OnboardingUpdate : Update<OnboardingState, OnboardingEvent, OnboardingCommand, OnboardingEffect> {

    override fun update(
        state: OnboardingState,
        event: OnboardingEvent,
    ): Next<OnboardingState, OnboardingCommand, OnboardingEffect> = when (event) {
        OnboardingEvent.WelcomeContinueClicked -> Next(
            state = state.copy(step = OnboardingState.Step.Done),
        )

        OnboardingEvent.DoneFinishClicked -> Next(
            state = state,
            command = OnboardingCommand.SaveCompleted,
        )

        OnboardingEvent.OnboardingSaved -> Next(
            state = state,
            effect = OnboardingEffect.Completed,
        )
    }
}
