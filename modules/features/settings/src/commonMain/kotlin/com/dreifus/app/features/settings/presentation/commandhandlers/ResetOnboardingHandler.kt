package com.dreifus.app.features.settings.presentation.commandhandlers

import com.dreifus.app.features.onboarding.data.OnboardingRepository
import com.dreifus.app.features.settings.presentation.SettingsCommand
import com.dreifus.app.features.settings.presentation.SettingsEvent
import com.yavorcool.mvucore.filteringHandler

fun resetOnboardingHandler(onboardingRepo: OnboardingRepository) =
    filteringHandler<SettingsCommand.ResetOnboarding, SettingsCommand, SettingsEvent> {
        onboardingRepo.resetOnboarding()
        SettingsEvent.OnboardingWasReset
    }
