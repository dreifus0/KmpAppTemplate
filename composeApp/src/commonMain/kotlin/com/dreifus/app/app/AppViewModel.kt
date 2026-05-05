package com.dreifus.app.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dreifus.app.di.AppGraph
import com.dreifus.app.di.PlatformDependencies
import com.dreifus.arch.di.IsDebug
import com.dreifus.arch.di.PlatformName
import com.yavorcool.mvucore.impl.Store
import dev.zacsweers.metro.createGraphFactory
import dev.zacsweers.metrox.viewmodel.MetroViewModelFactory

class AppViewModel(platformDeps: PlatformDependencies) : ViewModel() {

    private val graph = createGraphFactory<AppGraph.Factory>().create(
        settings = platformDeps.settings,
        isDebug = IsDebug(platformDeps.isDebug),
        platformName = PlatformName(platformDeps.platformName),
    )

    val factory: MetroViewModelFactory = graph.metroViewModelFactory

    private val store = Store<AppState, AppEvent, AppEvent, AppCommand, AppEffect>(
        initialState = AppState(),
        update = AppUpdate(),
        commandHandlers = emptyList(),
    )

    val state = store.state

    init {
        store.launch(viewModelScope)
        dispatch(AppEvent.Init)
    }

    fun dispatch(event: AppEvent) = store.dispatch(event)
}
