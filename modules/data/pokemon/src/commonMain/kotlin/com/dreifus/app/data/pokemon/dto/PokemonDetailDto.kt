package com.dreifus.app.data.pokemon.dto

import com.dreifus.app.data.pokemon.PokemonDetail
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonDetailDto(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val sprites: SpritesDto,
    val types: List<PokemonTypeSlotDto>,
)

@Serializable
data class SpritesDto(
    @SerialName("front_default") val frontDefault: String? = null,
)

@Serializable
data class PokemonTypeSlotDto(
    val type: PokemonTypeDto,
)

@Serializable
data class PokemonTypeDto(
    val name: String,
)

fun PokemonDetailDto.toDomain(): PokemonDetail = PokemonDetail(
    id = id,
    name = name.replaceFirstChar { it.uppercase() },
    heightDecimeters = height,
    weightHectograms = weight,
    imageUrl = sprites.frontDefault,
    types = types.map { it.type.name.replaceFirstChar { c -> c.uppercase() } },
)
