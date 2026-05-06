package com.dreifus.app.features.pokemon.presentation.detail

import com.dreifus.app.data.pokemon.PokemonDetail

sealed interface PokemonDetailEvent {
    data class NameProvided(val name: String) : PokemonDetailEvent
    data object Retry : PokemonDetailEvent
    data class DetailLoaded(val detail: PokemonDetail) : PokemonDetailEvent
    data class LoadFailed(val error: Throwable) : PokemonDetailEvent
}
