package com.dreifus.app.features.settings.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dreifus.navigation.ui.RootScreenWithTabs
import com.dreifus.template.uikit.style.AppTheme

class SettingsScreen : RootScreenWithTabs {

    @Composable
    override fun Content() {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "Settings",
                style = AppTheme.typography.headlineLarge,
                color = AppTheme.colors.contentPrimary,
            )
        }
    }
}
