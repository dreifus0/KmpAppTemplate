package com.dreifus.app.features.pokemon.presentation.list

sealed interface PokemonListEffect {
    data class NavigateToDetail(val name: String) : PokemonListEffect
}
