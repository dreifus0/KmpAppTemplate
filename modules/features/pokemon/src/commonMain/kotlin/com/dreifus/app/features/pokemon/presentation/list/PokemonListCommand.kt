package com.dreifus.app.features.pokemon.presentation.list

import com.dreifus.app.data.pokemon.PokemonListItem

sealed interface PokemonListCommand {
    data object LoadList : PokemonListCommand
    data class FilterList(val query: String, val allItems: List<PokemonListItem>) : PokemonListCommand
}
