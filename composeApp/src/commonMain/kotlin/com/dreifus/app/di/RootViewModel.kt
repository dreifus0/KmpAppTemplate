package com.dreifus.app.di

import androidx.lifecycle.ViewModel
import dev.zacsweers.metro.createGraph
import dev.zacsweers.metrox.viewmodel.MetroViewModelFactory

class RootViewModel : ViewModel() {
    private val graph = createGraph<AppGraph>()
    val factory: MetroViewModelFactory = graph.metroViewModelFactory
}
