package com.dreifus.app.features.onboarding.presentation.commandhandlers

import com.dreifus.app.features.onboarding.data.OnboardingRepository
import com.dreifus.app.features.onboarding.presentation.OnboardingCommand
import com.dreifus.app.features.onboarding.presentation.OnboardingEvent
import com.yavorcool.mvucore.filteringHandler

fun saveOnboardingHandler(repo: OnboardingRepository) =
    filteringHandler<OnboardingCommand.SaveCompleted, OnboardingCommand, OnboardingEvent> {
        repo.setOnboardingCompleted()
        OnboardingEvent.OnboardingSaved
    }
