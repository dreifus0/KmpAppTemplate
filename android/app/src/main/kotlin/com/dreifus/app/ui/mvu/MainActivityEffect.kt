package com.dreifus.app.ui.mvu

sealed interface MainActivityEffect {
    class ShowToast(val toastText: String) : MainActivityEffect
}
