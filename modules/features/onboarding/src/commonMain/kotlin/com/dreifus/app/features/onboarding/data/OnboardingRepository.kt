package com.dreifus.app.features.onboarding.data

import com.russhwolf.settings.Settings
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

@Inject
@SingleIn(AppScope::class)
class OnboardingRepository(private val settings: Settings) {

    private val _onboardingReset = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val onboardingReset: SharedFlow<Unit> = _onboardingReset

    fun isOnboardingCompleted(): Boolean = settings.getBoolean(KEY_COMPLETED, false)

    fun setOnboardingCompleted() {
        settings.putBoolean(KEY_COMPLETED, true)
    }

    fun resetOnboarding() {
        settings.remove(KEY_COMPLETED)
        _onboardingReset.tryEmit(Unit)
    }

    private companion object {
        const val KEY_COMPLETED = "onboarding_completed"
    }
}
