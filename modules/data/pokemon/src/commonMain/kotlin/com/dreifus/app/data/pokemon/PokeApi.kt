package com.dreifus.app.data.pokemon

import com.dreifus.app.data.pokemon.dto.PokemonDetailDto
import com.dreifus.app.data.pokemon.dto.PokemonListResponseDto
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

@Inject
@SingleIn(AppScope::class)
class PokeApi(private val client: HttpClient) {

    suspend fun getList(limit: Int): PokemonListResponseDto =
        client.get("pokemon") { parameter("limit", limit) }.body()

    suspend fun getByName(name: String): PokemonDetailDto =
        client.get("pokemon/${name.lowercase()}").body()
}
