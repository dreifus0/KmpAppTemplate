package com.dreifus.app.di

import androidx.lifecycle.ViewModel
import com.dreifus.app.counter.ui.CounterScreen
import com.dreifus.app.stub.ui.StubScreen
import com.dreifus.navigation.controller.NavControllersHolder
import com.dreifus.navigation.controller.TabNavState
import dev.zacsweers.metro.createGraph
import dev.zacsweers.metrox.viewmodel.MetroViewModelFactory

class RootViewModel : ViewModel() {
    private val graph = createGraph<AppGraph>()
    val factory: MetroViewModelFactory = graph.metroViewModelFactory

    val navControllersHolder = NavControllersHolder(
        tabNavState = TabNavState(
            tabRoots = listOf(CounterScreen(), StubScreen()),
            initialActiveIndex = 0,
        ),
    )
}
