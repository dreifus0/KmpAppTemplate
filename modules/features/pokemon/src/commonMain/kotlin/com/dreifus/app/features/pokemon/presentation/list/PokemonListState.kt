package com.dreifus.app.features.pokemon.presentation.list

import com.dreifus.app.data.pokemon.PokemonListItem
import com.dreifus.arch.lce.LceState

data class PokemonListState(
    val allItems: List<PokemonListItem> = emptyList(),
    val items: LceState<List<PokemonListItem>> = LceState.Loading.Initial,
    val searchQuery: String = "",
)
