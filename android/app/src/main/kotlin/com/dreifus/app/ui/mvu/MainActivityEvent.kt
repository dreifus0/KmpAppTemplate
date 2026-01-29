package com.dreifus.app.ui.mvu

sealed interface MainActivityEvent {
    object Init : MainActivityEvent
    class ShowToast(val toastText: String) : MainActivityEvent
}
