package com.dreifus.app.ui.mvu

import com.yavorcool.mvucore.Next
import com.yavorcool.mvucore.Update
import dev.zacsweers.metro.Inject

class MainActivityUpdate @Inject constructor(
) : Update<MainActivityState, MainActivityEvent, MainActivityCommand, MainActivityEffect> {
    override fun update(
        state: MainActivityState,
        event: MainActivityEvent,
    ): Next<MainActivityState, MainActivityCommand, MainActivityEffect> {
        return when (event) {
            MainActivityEvent.Init -> {
                Next(state = state)
            }

            is MainActivityEvent.ShowToast -> {
                Next(
                    state = state,
                    effect = MainActivityEffect.ShowToast(event.toastText)
                )
            }
        }
    }
}
