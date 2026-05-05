package com.dreifus.app.features.pokemon.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dreifus.app.data.pokemon.PokemonRepository
import com.dreifus.app.features.pokemon.presentation.list.commandhandlers.loadPokemonListHandler
import com.yavorcool.mvucore.impl.Store
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metrox.viewmodel.ViewModelKey

@Inject
@ViewModelKey(PokemonListViewModel::class)
@ContributesIntoMap(AppScope::class)
class PokemonListViewModel(
    repo: PokemonRepository,
) : ViewModel() {

    private val store = Store<PokemonListState, PokemonListEvent, PokemonListEvent, PokemonListCommand, PokemonListEffect>(
        initialState = PokemonListState(),
        update = PokemonListUpdate(),
        commandHandlers = listOf(
            loadPokemonListHandler(repo),
        ),
    )

    val state = store.state
    val effects = store.effects

    init {
        store.launch(viewModelScope)
        store.dispatch(PokemonListEvent.ScreenOpened)
    }

    fun dispatch(event: PokemonListEvent) = store.dispatch(event)
}
