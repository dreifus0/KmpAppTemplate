package com.dreifus.app.features.pokemon.presentation.list

sealed interface PokemonListCommand {
    data object LoadList : PokemonListCommand
}
