package com.dreifus.app.root

import androidx.lifecycle.ViewModel
import com.dreifus.app.features.counter.ui.CounterScreen
import com.dreifus.app.features.stub.ui.StubScreen
import com.dreifus.navigation.controller.NavControllersHolder
import com.dreifus.navigation.controller.TabNavState
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metrox.viewmodel.ViewModelKey

@Inject
@ViewModelKey(RootViewModel::class)
@ContributesIntoMap(AppScope::class)
class RootViewModel : ViewModel() {

    val navControllersHolder = NavControllersHolder(
        tabNavState = TabNavState(
            tabRoots = listOf(CounterScreen(), StubScreen()),
            initialActiveIndex = 0,
        ),
    )
}
