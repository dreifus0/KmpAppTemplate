package com.dreifus.app.features.settings.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.dreifus.app.features.settings.presentation.SettingsEvent
import com.dreifus.app.features.settings.presentation.SettingsViewModel
import com.dreifus.navigation.ui.RootScreenWithTabs
import dev.zacsweers.metrox.viewmodel.metroViewModel

class SettingsScreen : RootScreenWithTabs {

    @Composable
    override fun Content() {
        val viewModel = metroViewModel<SettingsViewModel>()
        val state by viewModel.state.collectAsState()

        SettingsContent(
            state = state,
            onThemeSelected = { mode -> viewModel.dispatch(SettingsEvent.ThemeSelected(mode)) },
            onResetOnboarding = { viewModel.dispatch(SettingsEvent.ResetOnboardingClicked) },
        )
    }
}
