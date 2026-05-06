package com.dreifus.app.features.pokemon.presentation.list.commandhandlers

import com.dreifus.app.data.pokemon.PokemonRepository
import com.dreifus.app.features.pokemon.presentation.list.PokemonListCommand
import com.dreifus.app.features.pokemon.presentation.list.PokemonListEvent
import com.yavorcool.mvucore.filteringHandler
import kotlinx.coroutines.CancellationException

fun loadPokemonListHandler(repo: PokemonRepository) =
    filteringHandler<PokemonListCommand.LoadList, PokemonListCommand, PokemonListEvent> {
        try {
            PokemonListEvent.ListLoaded(repo.getList())
        } catch (e: CancellationException) {
            throw e
        } catch (e: Throwable) {
            PokemonListEvent.LoadFailed(e)
        }
    }
