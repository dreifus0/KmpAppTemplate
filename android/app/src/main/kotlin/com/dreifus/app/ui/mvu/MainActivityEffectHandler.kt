package com.dreifus.app.ui.mvu

import com.dreifus.helpers.Toaster
import dev.zacsweers.metro.Inject

class MainActivityEffectHandler @Inject constructor(
    private val toaster: Toaster,
) {
    fun handleEffect(effect: MainActivityEffect) {
        when (effect) {
            is MainActivityEffect.ShowToast -> {
                toaster.showMessage(effect.toastText)
            }
        }
    }
}
