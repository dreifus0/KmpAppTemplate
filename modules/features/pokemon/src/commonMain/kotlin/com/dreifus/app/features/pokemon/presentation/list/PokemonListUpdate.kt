package com.dreifus.app.features.pokemon.presentation.list

import com.dreifus.arch.lce.LceState
import com.yavorcool.mvucore.Next
import com.yavorcool.mvucore.Update

class PokemonListUpdate : Update<PokemonListState, PokemonListEvent, PokemonListCommand, PokemonListEffect> {

    override fun update(
        state: PokemonListState,
        event: PokemonListEvent,
    ): Next<PokemonListState, PokemonListCommand, PokemonListEffect> = when (event) {
        PokemonListEvent.ScreenOpened -> if (state.items is LceState.Content) {
            Next(state)
        } else {
            Next(
                state = state.copy(items = LceState.Loading.Initial),
                command = PokemonListCommand.LoadList,
            )
        }

        PokemonListEvent.Refresh -> Next(
            state = state.copy(items = state.items.toRefreshing()),
            command = PokemonListCommand.LoadList,
        )

        is PokemonListEvent.ItemClicked -> Next(
            state = state,
            effect = PokemonListEffect.NavigateToDetail(event.name),
        )

        is PokemonListEvent.ListLoaded -> Next(
            state = state.copy(items = LceState.Content(event.items)),
        )

        is PokemonListEvent.LoadFailed -> Next(
            state = state.copy(items = LceState.Error(event.error)),
        )
    }
}

private fun <T : Any> LceState<T>.toRefreshing(): LceState<T> = when (this) {
    is LceState.Content -> copy(isRefreshing = true)
    is LceState.Error -> copy(isRefreshing = true)
    is LceState.Loading -> this
}
