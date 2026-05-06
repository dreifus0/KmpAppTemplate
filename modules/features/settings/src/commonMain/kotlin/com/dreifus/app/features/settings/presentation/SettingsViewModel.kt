package com.dreifus.app.features.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dreifus.app.features.onboarding.data.OnboardingRepository
import com.dreifus.app.features.settings.data.ThemeRepository
import com.dreifus.app.features.settings.presentation.commandhandlers.resetOnboardingHandler
import com.dreifus.app.features.settings.presentation.commandhandlers.setThemeModeHandler
import com.yavorcool.mvucore.impl.Store
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import kotlinx.coroutines.launch

@Inject
@ViewModelKey(SettingsViewModel::class)
@ContributesIntoMap(AppScope::class)
class SettingsViewModel(
    private val themeRepo: ThemeRepository,
    onboardingRepo: OnboardingRepository,
) : ViewModel() {

    private val store = Store<SettingsState, SettingsEvent, SettingsEvent, SettingsCommand, SettingsEffect>(
        initialState = SettingsState(themeMode = themeRepo.themeMode.value),
        update = SettingsUpdate(),
        commandHandlers = listOf(
            resetOnboardingHandler(onboardingRepo),
            setThemeModeHandler(themeRepo),
        ),
    )

    val state = store.state

    init {
        store.launch(viewModelScope)

        viewModelScope.launch {
            themeRepo.themeMode.collect { mode ->
                store.dispatch(SettingsEvent.ThemeModeLoaded(mode))
            }
        }
    }

    fun dispatch(event: SettingsEvent) = store.dispatch(event)
}
