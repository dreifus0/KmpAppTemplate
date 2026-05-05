package com.dreifus.app.app

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class AppUpdateTest {

    private val update = AppUpdate()

    private fun update(state: AppState, event: AppEvent) = update.update(state, event)

    @Test
    fun `Init emits CheckOnboardingStatus command`() {
        val next = update(AppState(), AppEvent.Init)
        assertEquals(AppState.Screen.Loading, next.state.screen)
        assertIs<AppCommand.CheckOnboardingStatus>(next.commands.single())
    }

    @Test
    fun `ShowOnboarding switches screen to Onboarding`() {
        val next = update(AppState(), AppEvent.ShowOnboarding)
        assertEquals(AppState.Screen.Onboarding, next.state.screen)
    }

    @Test
    fun `ShowRoot switches screen to Root`() {
        val next = update(AppState(), AppEvent.ShowRoot)
        assertEquals(AppState.Screen.Root, next.state.screen)
    }

    @Test
    fun `OnboardingCompleted switches screen to Root`() {
        val state = AppState(screen = AppState.Screen.Onboarding)
        val next = update(state, AppEvent.OnboardingCompleted)
        assertEquals(AppState.Screen.Root, next.state.screen)
    }
}
