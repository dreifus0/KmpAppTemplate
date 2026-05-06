package com.dreifus.app.data.pokemon

import com.dreifus.app.data.pokemon.dto.toDomain
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn

@Inject
@SingleIn(AppScope::class)
class PokemonRepository(private val api: PokeApi) {

    suspend fun getList(): List<PokemonListItem> =
        api.getList(LIST_LIMIT).results.map { it.toDomain() }

    suspend fun getDetail(name: String): PokemonDetail =
        api.getByName(name).toDomain()

    private companion object {
        const val LIST_LIMIT = 30
    }
}
