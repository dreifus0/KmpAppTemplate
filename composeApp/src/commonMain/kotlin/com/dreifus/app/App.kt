package com.dreifus.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dreifus.app.app.AppEvent
import com.dreifus.app.app.AppState
import com.dreifus.app.app.AppViewModel
import com.dreifus.app.di.PlatformDependencies
import com.dreifus.app.features.onboarding.presentation.ui.OnboardingScreen
import com.dreifus.app.root.RootScreen
import com.dreifus.template.uikit.style.AppTheme
import com.dreifus.template.uikit.style.app.DefaultAppTheme
import dev.zacsweers.metrox.viewmodel.LocalMetroViewModelFactory

@Composable
fun App(platformDependencies: PlatformDependencies) {
    val appViewModel = viewModel { AppViewModel(platformDependencies) }
    val appState by appViewModel.state.collectAsState()

    DefaultAppTheme(darkTheme = isSystemInDarkTheme()) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = AppTheme.colors.backgroundBase,
        ) {
            CompositionLocalProvider(LocalMetroViewModelFactory provides appViewModel.factory) {
                when (appState.screen) {
                    AppState.Screen.Loading -> Unit

                    AppState.Screen.Onboarding -> OnboardingScreen(
                        onComplete = { appViewModel.dispatch(AppEvent.OnboardingCompleted) },
                    )

                    AppState.Screen.Root -> RootScreen()
                }
            }
        }
    }
}
