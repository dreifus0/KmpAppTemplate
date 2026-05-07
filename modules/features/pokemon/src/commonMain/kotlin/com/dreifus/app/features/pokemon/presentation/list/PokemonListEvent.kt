package com.dreifus.app.features.pokemon.presentation.list

import com.dreifus.app.data.pokemon.PokemonListItem

sealed interface PokemonListEvent {
    data object ScreenOpened : PokemonListEvent
    data object Refresh : PokemonListEvent
    data class ItemClicked(val name: String) : PokemonListEvent
    data class ListLoaded(val items: List<PokemonListItem>) : PokemonListEvent
    data class LoadFailed(val error: Throwable) : PokemonListEvent
    data class SearchQueryChanged(val query: String) : PokemonListEvent
    data class FilteredListReady(val items: List<PokemonListItem>) : PokemonListEvent
}
