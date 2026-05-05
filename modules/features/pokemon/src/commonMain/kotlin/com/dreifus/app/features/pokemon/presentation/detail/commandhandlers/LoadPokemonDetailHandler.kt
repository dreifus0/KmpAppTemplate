package com.dreifus.app.features.pokemon.presentation.detail.commandhandlers

import com.dreifus.app.data.pokemon.PokemonRepository
import com.dreifus.app.features.pokemon.presentation.detail.PokemonDetailCommand
import com.dreifus.app.features.pokemon.presentation.detail.PokemonDetailEvent
import com.yavorcool.mvucore.filteringHandler
import kotlinx.coroutines.CancellationException

fun loadPokemonDetailHandler(repo: PokemonRepository) =
    filteringHandler<PokemonDetailCommand.LoadDetail, PokemonDetailCommand, PokemonDetailEvent> { command ->
        try {
            PokemonDetailEvent.DetailLoaded(repo.getDetail(command.name))
        } catch (e: CancellationException) {
            throw e
        } catch (e: Throwable) {
            PokemonDetailEvent.LoadFailed(e)
        }
    }
