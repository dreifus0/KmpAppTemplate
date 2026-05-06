package com.dreifus.app.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dreifus.app.app.commandhandlers.checkOnboardingStatusHandler
import com.dreifus.app.di.AppGraph
import com.dreifus.app.di.PlatformDependencies
import com.dreifus.arch.di.IsDebug
import com.dreifus.arch.di.PlatformName
import com.dreifus.network.ApiConfig
import com.dreifus.network.createHttpClient
import com.yavorcool.mvucore.impl.Store
import dev.zacsweers.metro.createGraphFactory
import dev.zacsweers.metrox.viewmodel.MetroViewModelFactory
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch

class AppViewModel(platformDeps: PlatformDependencies) : ViewModel() {

    private val graph = createGraphFactory<AppGraph.Factory>().create(
        settings = platformDeps.settings,
        isDebug = IsDebug(platformDeps.isDebug),
        platformName = PlatformName(platformDeps.platformName),
        httpClient = createHttpClient(
            ApiConfig(
                baseUrl = ApiConstants.BASE_URL,
                isDebug = platformDeps.isDebug,
            ),
        ),
    )

    val factory: MetroViewModelFactory = graph.metroViewModelFactory

    private val store = Store<AppState, AppEvent, AppEvent, AppCommand, AppEffect>(
        initialState = AppState(themeMode = graph.themeRepository.themeMode.value),
        update = AppUpdate(),
        commandHandlers = listOf(
            checkOnboardingStatusHandler(graph.onboardingRepository),
        ),
    )

    val state = store.state

    init {
        if (platformDeps.isDebug) Napier.base(DebugAntilog())

        store.launch(viewModelScope)
        dispatch(AppEvent.Init)

        viewModelScope.launch {
            graph.onboardingRepository.onboardingReset.collect {
                dispatch(AppEvent.ShowOnboarding)
            }
        }

        viewModelScope.launch {
            graph.themeRepository.themeMode.collect { mode ->
                dispatch(AppEvent.ThemeModeChanged(mode))
            }
        }
    }

    fun dispatch(event: AppEvent) = store.dispatch(event)
}
