package com.dreifus.app.features.pokemon.presentation.list.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.dreifus.app.features.pokemon.presentation.detail.ui.PokemonDetailScreen
import com.dreifus.app.features.pokemon.presentation.list.PokemonListEffect
import com.dreifus.app.features.pokemon.presentation.list.PokemonListEvent
import com.dreifus.app.features.pokemon.presentation.list.PokemonListViewModel
import com.dreifus.navigation.controller.Navigation
import com.dreifus.navigation.ui.RootScreenWithTabs
import dev.zacsweers.metrox.viewmodel.metroViewModel

class PokemonListScreen : RootScreenWithTabs {

    @Composable
    override fun Content() {
        val viewModel = metroViewModel<PokemonListViewModel>()
        val state by viewModel.state.collectAsState()
        val nav = Navigation.regular

        LaunchedEffect(viewModel) {
            viewModel.effects.collect { effect ->
                when (effect) {
                    is PokemonListEffect.NavigateToDetail -> nav.navigate(PokemonDetailScreen(effect.name))
                }
            }
        }

        PokemonListContent(
            state = state,
            onItemClick = { name -> viewModel.dispatch(PokemonListEvent.ItemClicked(name)) },
            onRefresh = { viewModel.dispatch(PokemonListEvent.Refresh) },
            onSearchQueryChanged = { query -> viewModel.dispatch(PokemonListEvent.SearchQueryChanged(query)) },
        )
    }
}
