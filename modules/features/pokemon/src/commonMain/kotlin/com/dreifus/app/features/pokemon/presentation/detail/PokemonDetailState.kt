package com.dreifus.app.features.pokemon.presentation.detail

import com.dreifus.app.data.pokemon.PokemonDetail
import com.dreifus.arch.lce.LceState

data class PokemonDetailState(
    val name: String = "",
    val detail: LceState<PokemonDetail> = LceState.Loading.Initial,
)
