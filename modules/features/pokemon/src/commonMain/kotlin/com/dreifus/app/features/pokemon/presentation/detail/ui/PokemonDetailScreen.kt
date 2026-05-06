package com.dreifus.app.features.pokemon.presentation.detail.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.dreifus.app.features.pokemon.presentation.detail.PokemonDetailEvent
import com.dreifus.app.features.pokemon.presentation.detail.PokemonDetailViewModel
import com.dreifus.navigation.screen.regular.RegularScreen
import dev.zacsweers.metrox.viewmodel.metroViewModel

class PokemonDetailScreen(val name: String) : RegularScreen {

    @Composable
    override fun Content() {
        val viewModel = metroViewModel<PokemonDetailViewModel>(key = name)
        val state by viewModel.state.collectAsState()

        LaunchedEffect(viewModel, name) {
            viewModel.init(name)
        }

        PokemonDetailContent(
            state = state,
            fallbackName = name,
            onRetry = { viewModel.dispatch(PokemonDetailEvent.Retry) },
        )
    }
}
