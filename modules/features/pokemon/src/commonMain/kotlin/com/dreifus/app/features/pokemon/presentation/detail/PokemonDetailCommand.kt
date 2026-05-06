package com.dreifus.app.features.pokemon.presentation.detail

sealed interface PokemonDetailCommand {
    data class LoadDetail(val name: String) : PokemonDetailCommand
}
