package com.dreifus.app.features.onboarding.presentation

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class OnboardingUpdateTest {

    private val update = OnboardingUpdate()

    private fun update(state: OnboardingState, event: OnboardingEvent) =
        update.update(state, event)

    @Test
    fun `WelcomeContinueClicked moves step to Done`() {
        val next = update(OnboardingState(), OnboardingEvent.WelcomeContinueClicked)
        assertEquals(OnboardingState.Step.Done, next.state.step)
        assertTrue(next.commands.isEmpty())
    }

    @Test
    fun `DoneFinishClicked emits SaveCompleted command without state change`() {
        val state = OnboardingState(step = OnboardingState.Step.Done)
        val next = update(state, OnboardingEvent.DoneFinishClicked)
        assertEquals(state, next.state)
        assertIs<OnboardingCommand.SaveCompleted>(next.commands.single())
    }

    @Test
    fun `OnboardingSaved emits Completed effect`() {
        val state = OnboardingState(step = OnboardingState.Step.Done)
        val next = update(state, OnboardingEvent.OnboardingSaved)
        assertEquals(state, next.state)
        assertIs<OnboardingEffect.Completed>(next.effects.single())
    }
}
