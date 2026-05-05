package com.dreifus.app.app

import com.yavorcool.mvucore.Next
import com.yavorcool.mvucore.Update

class AppUpdate : Update<AppState, AppEvent, AppCommand, AppEffect> {

    override fun update(
        state: AppState,
        event: AppEvent,
    ): Next<AppState, AppCommand, AppEffect> = when (event) {
        AppEvent.Init -> Next(
            state = state,
            command = AppCommand.CheckOnboardingStatus,
        )

        AppEvent.ShowOnboarding -> Next(
            state = state.copy(screen = AppState.Screen.Onboarding),
        )

        AppEvent.ShowRoot -> Next(
            state = state.copy(screen = AppState.Screen.Root),
        )

        AppEvent.OnboardingCompleted -> Next(
            state = state.copy(screen = AppState.Screen.Root),
        )
    }
}
