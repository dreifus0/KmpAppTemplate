package com.dreifus.app.app

sealed interface AppCommand {
    data object CheckOnboardingStatus : AppCommand
}
