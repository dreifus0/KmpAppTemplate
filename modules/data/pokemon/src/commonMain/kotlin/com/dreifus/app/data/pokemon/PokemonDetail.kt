package com.dreifus.app.data.pokemon

data class PokemonDetail(
    val id: Int,
    val name: String,
    val heightDecimeters: Int,
    val weightHectograms: Int,
    val imageUrl: String?,
    val types: List<String>,
)
