package com.dreifus.app.data.pokemon.dto

import com.dreifus.app.data.pokemon.PokemonListItem
import kotlinx.serialization.Serializable

@Serializable
data class PokemonListResponseDto(
    val results: List<PokemonListItemDto>,
)

@Serializable
data class PokemonListItemDto(
    val name: String,
    val url: String,
)

fun PokemonListItemDto.toDomain(): PokemonListItem {
    val id = url.trimEnd('/').substringAfterLast('/').toInt()
    return PokemonListItem(
        id = id,
        name = name.replaceFirstChar { it.uppercase() },
        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png",
    )
}
