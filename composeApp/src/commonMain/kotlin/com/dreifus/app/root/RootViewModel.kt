package com.dreifus.app.root

import androidx.lifecycle.ViewModel
import com.dreifus.app.features.pokemon.presentation.list.ui.PokemonListScreen
import com.dreifus.app.features.settings.presentation.ui.SettingsScreen
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
            tabRoots = listOf(PokemonListScreen(), SettingsScreen()),
            initialActiveIndex = 0,
        ),
    )
}
