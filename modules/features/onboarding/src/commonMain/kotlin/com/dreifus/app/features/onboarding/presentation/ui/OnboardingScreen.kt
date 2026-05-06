package com.dreifus.app.features.onboarding.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.dreifus.app.features.onboarding.presentation.OnboardingEffect
import com.dreifus.app.features.onboarding.presentation.OnboardingEvent
import com.dreifus.app.features.onboarding.presentation.OnboardingState
import com.dreifus.app.features.onboarding.presentation.OnboardingViewModel
import dev.zacsweers.metrox.viewmodel.metroViewModel

@Composable
fun OnboardingScreen(onComplete: () -> Unit) {
    val viewModel = metroViewModel<OnboardingViewModel>()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.effects.collect { effect ->
            when (effect) {
                OnboardingEffect.Completed -> onComplete()
            }
        }
    }

    when (state.step) {
        OnboardingState.Step.Welcome -> WelcomeContent(
            onContinue = { viewModel.dispatch(OnboardingEvent.WelcomeContinueClicked) },
        )

        OnboardingState.Step.Done -> DoneContent(
            onFinish = { viewModel.dispatch(OnboardingEvent.DoneFinishClicked) },
        )
    }
}
