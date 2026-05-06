package com.dreifus.app.features.onboarding.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dreifus.app.features.onboarding.data.OnboardingRepository
import com.dreifus.app.features.onboarding.presentation.commandhandlers.saveOnboardingHandler
import com.yavorcool.mvucore.impl.Store
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metrox.viewmodel.ViewModelKey

@Inject
@ViewModelKey(OnboardingViewModel::class)
@ContributesIntoMap(AppScope::class)
class OnboardingViewModel(
    onboardingRepo: OnboardingRepository,
) : ViewModel() {

    private val store = Store<OnboardingState, OnboardingEvent, OnboardingEvent, OnboardingCommand, OnboardingEffect>(
        initialState = OnboardingState(),
        update = OnboardingUpdate(),
        commandHandlers = listOf(
            saveOnboardingHandler(onboardingRepo),
        ),
    )

    val state = store.state
    val effects = store.effects

    init {
        store.launch(viewModelScope)
    }

    fun dispatch(event: OnboardingEvent) = store.dispatch(event)
}
