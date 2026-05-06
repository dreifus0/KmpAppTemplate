package com.dreifus.app.features.settings.presentation

import com.dreifus.app.features.settings.data.ThemeMode
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class SettingsUpdateTest {

    private val update = SettingsUpdate()

    private fun update(state: SettingsState, event: SettingsEvent) =
        update.update(state, event)

    @Test
    fun `ResetOnboardingClicked emits ResetOnboarding command`() {
        val next = update(SettingsState(), SettingsEvent.ResetOnboardingClicked)
        assertEquals(SettingsState(), next.state)
        assertIs<SettingsCommand.ResetOnboarding>(next.commands.single())
    }

    @Test
    fun `ThemeSelected emits SetThemeMode command`() {
        val next = update(SettingsState(), SettingsEvent.ThemeSelected(ThemeMode.Dark))
        val cmd = next.commands.single() as SettingsCommand.SetThemeMode
        assertEquals(ThemeMode.Dark, cmd.mode)
    }

    @Test
    fun `ThemeModeLoaded updates themeMode without command`() {
        val next = update(SettingsState(), SettingsEvent.ThemeModeLoaded(ThemeMode.Light))
        assertEquals(ThemeMode.Light, next.state.themeMode)
        assertTrue(next.commands.isEmpty())
    }

    @Test
    fun `OnboardingWasReset is a no-op for state`() {
        val state = SettingsState(themeMode = ThemeMode.Dark)
        val next = update(state, SettingsEvent.OnboardingWasReset)
        assertEquals(state, next.state)
        assertTrue(next.commands.isEmpty())
    }
}
