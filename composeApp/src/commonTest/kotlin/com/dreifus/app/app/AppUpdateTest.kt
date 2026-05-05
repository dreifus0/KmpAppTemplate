package com.dreifus.app.app

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AppUpdateTest {

    private val update = AppUpdate()

    @Test
    fun `Init keeps state unchanged`() {
        val state = AppState()
        val next = update.update(state, AppEvent.Init)
        assertEquals(state, next.state)
        assertTrue(next.commands.isEmpty())
    }

    @Test
    fun `Init from non-default state preserves screen`() {
        val state = AppState(screen = AppState.Screen.Root)
        val next = update.update(state, AppEvent.Init)
        assertEquals(AppState.Screen.Root, next.state.screen)
    }
}
