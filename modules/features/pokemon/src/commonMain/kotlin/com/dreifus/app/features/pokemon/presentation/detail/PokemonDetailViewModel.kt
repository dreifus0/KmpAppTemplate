package com.dreifus.app.features.pokemon.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dreifus.app.data.pokemon.PokemonRepository
import com.dreifus.app.features.pokemon.presentation.detail.commandhandlers.loadPokemonDetailHandler
import com.yavorcool.mvucore.impl.Store
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metrox.viewmodel.ViewModelKey

@Inject
@ViewModelKey(PokemonDetailViewModel::class)
@ContributesIntoMap(AppScope::class)
class PokemonDetailViewModel(
    repo: PokemonRepository,
) : ViewModel() {

    private val store = Store<PokemonDetailState, PokemonDetailEvent, PokemonDetailEvent, PokemonDetailCommand, PokemonDetailEffect>(
        initialState = PokemonDetailState(),
        update = PokemonDetailUpdate(),
        commandHandlers = listOf(
            loadPokemonDetailHandler(repo),
        ),
    )

    val state = store.state

    init {
        store.launch(viewModelScope)
    }

    fun init(name: String) {
        store.dispatch(PokemonDetailEvent.NameProvided(name))
    }

    fun dispatch(event: PokemonDetailEvent) = store.dispatch(event)
}
