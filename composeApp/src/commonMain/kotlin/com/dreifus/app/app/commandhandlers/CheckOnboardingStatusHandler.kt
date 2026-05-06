package com.dreifus.app.app.commandhandlers

import com.dreifus.app.app.AppCommand
import com.dreifus.app.app.AppEvent
import com.dreifus.app.features.onboarding.data.OnboardingRepository
import com.yavorcool.mvucore.filteringHandler

fun checkOnboardingStatusHandler(repo: OnboardingRepository) =
    filteringHandler<AppCommand.CheckOnboardingStatus, AppCommand, AppEvent> {
        if (repo.isOnboardingCompleted()) AppEvent.ShowRoot else AppEvent.ShowOnboarding
    }
