package com.dreifus.app.features.pokemon.presentation.detail

import com.dreifus.arch.lce.LceState
import com.yavorcool.mvucore.Next
import com.yavorcool.mvucore.Update

class PokemonDetailUpdate : Update<PokemonDetailState, PokemonDetailEvent, PokemonDetailCommand, PokemonDetailEffect> {

    override fun update(
        state: PokemonDetailState,
        event: PokemonDetailEvent,
    ): Next<PokemonDetailState, PokemonDetailCommand, PokemonDetailEffect> = when (event) {
        is PokemonDetailEvent.NameProvided -> if (state.name == event.name && state.detail !is LceState.Loading) {
            Next(state)
        } else {
            Next(
                state = state.copy(name = event.name, detail = LceState.Loading.Initial),
                command = PokemonDetailCommand.LoadDetail(event.name),
            )
        }

        PokemonDetailEvent.Retry -> Next(
            state = state.copy(detail = LceState.Loading.Initial),
            command = PokemonDetailCommand.LoadDetail(state.name),
        )

        is PokemonDetailEvent.DetailLoaded -> Next(
            state = state.copy(detail = LceState.Content(event.detail)),
        )

        is PokemonDetailEvent.LoadFailed -> Next(
            state = state.copy(detail = LceState.Error(event.error)),
        )
    }
}
