package com.dreifus.app.features.pokemon.presentation.detail

import com.dreifus.app.data.pokemon.PokemonDetail
import com.dreifus.arch.lce.LceState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class PokemonDetailUpdateTest {

    private val update = PokemonDetailUpdate()

    private val pikachu = PokemonDetail(
        id = 25,
        name = "Pikachu",
        heightDecimeters = 4,
        weightHectograms = 60,
        imageUrl = "https://example.com/25.png",
        types = listOf("Electric"),
    )

    private fun update(state: PokemonDetailState, event: PokemonDetailEvent) =
        update.update(state, event)

    @Test
    fun `NameProvided sets name and emits LoadDetail`() {
        val next = update(PokemonDetailState(), PokemonDetailEvent.NameProvided("Pikachu"))
        assertEquals("Pikachu", next.state.name)
        assertIs<LceState.Loading<*>>(next.state.detail)
        val cmd = next.commands.single() as PokemonDetailCommand.LoadDetail
        assertEquals("Pikachu", cmd.name)
    }

    @Test
    fun `NameProvided with same name and Content state is a no-op`() {
        val state = PokemonDetailState(name = "Pikachu", detail = LceState.Content(pikachu))
        val next = update(state, PokemonDetailEvent.NameProvided("Pikachu"))
        assertEquals(state, next.state)
        assertTrue(next.commands.isEmpty())
    }

    @Test
    fun `DetailLoaded transitions state to Content`() {
        val state = PokemonDetailState(name = "Pikachu")
        val next = update(state, PokemonDetailEvent.DetailLoaded(pikachu))
        assertEquals(LceState.Content(pikachu), next.state.detail)
    }

    @Test
    fun `LoadFailed transitions state to Error`() {
        val cause = RuntimeException("boom")
        val state = PokemonDetailState(name = "Pikachu")
        val next = update(state, PokemonDetailEvent.LoadFailed(cause))
        val error = next.state.detail as LceState.Error
        assertEquals(cause, error.error)
    }

    @Test
    fun `Retry resets to Loading and dispatches LoadDetail`() {
        val state = PokemonDetailState(name = "Pikachu", detail = LceState.Error(RuntimeException()))
        val next = update(state, PokemonDetailEvent.Retry)
        assertIs<LceState.Loading<*>>(next.state.detail)
        val cmd = next.commands.single() as PokemonDetailCommand.LoadDetail
        assertEquals("Pikachu", cmd.name)
    }
}
