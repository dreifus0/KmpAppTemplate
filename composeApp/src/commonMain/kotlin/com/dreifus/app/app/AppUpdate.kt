package com.dreifus.app.app

import com.yavorcool.mvucore.Next
import com.yavorcool.mvucore.Update

class AppUpdate : Update<AppState, AppEvent, AppCommand, AppEffect> {

    override fun update(
        state: AppState,
        event: AppEvent,
    ): Next<AppState, AppCommand, AppEffect> = when (event) {
        AppEvent.Init -> Next(state = state)
    }
}
