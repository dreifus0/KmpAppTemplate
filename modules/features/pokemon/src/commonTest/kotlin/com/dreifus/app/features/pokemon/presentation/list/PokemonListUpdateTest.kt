package com.dreifus.app.features.pokemon.presentation.list

import com.dreifus.app.data.pokemon.PokemonListItem
import com.dreifus.arch.lce.LceState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class PokemonListUpdateTest {

    private val update = PokemonListUpdate()

    private val sampleItems = listOf(
        PokemonListItem(1, "Bulbasaur", "https://example.com/1.png"),
        PokemonListItem(2, "Ivysaur", "https://example.com/2.png"),
    )

    private fun update(state: PokemonListState, event: PokemonListEvent) =
        update.update(state, event)

    @Test
    fun `ScreenOpened with Loading state emits LoadList`() {
        val next = update(PokemonListState(), PokemonListEvent.ScreenOpened)
        assertIs<PokemonListCommand.LoadList>(next.commands.single())
        assertIs<LceState.Loading<*>>(next.state.items)
    }

    @Test
    fun `ScreenOpened with Content state does not reload`() {
        val state = PokemonListState(items = LceState.Content(sampleItems))
        val next = update(state, PokemonListEvent.ScreenOpened)
        assertTrue(next.commands.isEmpty())
        assertEquals(state, next.state)
    }

    @Test
    fun `ListLoaded transitions state to Content`() {
        val next = update(PokemonListState(), PokemonListEvent.ListLoaded(sampleItems))
        assertEquals(LceState.Content(sampleItems), next.state.items)
    }

    @Test
    fun `LoadFailed transitions state to Error`() {
        val cause = RuntimeException("boom")
        val next = update(PokemonListState(), PokemonListEvent.LoadFailed(cause))
        val error = next.state.items as LceState.Error
        assertEquals(cause, error.error)
    }

    @Test
    fun `ItemClicked emits NavigateToDetail without state change`() {
        val state = PokemonListState(items = LceState.Content(sampleItems))
        val next = update(state, PokemonListEvent.ItemClicked("Pikachu"))
        assertEquals(state, next.state)
        val effect = next.effects.single() as PokemonListEffect.NavigateToDetail
        assertEquals("Pikachu", effect.name)
    }

    @Test
    fun `Refresh from Content marks isRefreshing and reloads`() {
        val state = PokemonListState(items = LceState.Content(sampleItems))
        val next = update(state, PokemonListEvent.Refresh)
        val content = next.state.items as LceState.Content
        assertTrue(content.isRefreshing)
        assertIs<PokemonListCommand.LoadList>(next.commands.single())
    }
}
