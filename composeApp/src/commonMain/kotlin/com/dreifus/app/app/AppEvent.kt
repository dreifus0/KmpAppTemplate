package com.dreifus.app.app

sealed interface AppEvent {
    data object Init : AppEvent
}
