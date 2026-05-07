package com.dreifus.app.features.pokemon.presentation.list.commandhandlers

import com.dreifus.app.features.pokemon.presentation.list.PokemonListCommand
import com.dreifus.app.features.pokemon.presentation.list.PokemonListEvent
import com.yavorcool.mvucore.filteringHandler

fun filterPokemonListHandler() =
    filteringHandler<PokemonListCommand.FilterList, PokemonListCommand, PokemonListEvent> { command ->
        PokemonListEvent.FilteredListReady(
            command.allItems.filter { it.name.contains(command.query, ignoreCase = true) },
        )
    }
