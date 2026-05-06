package com.dreifus.app.features.settings.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dreifus.app.features.settings.data.ThemeMode
import com.dreifus.app.features.settings.presentation.SettingsState
import com.dreifus.template.uikit.style.AppTheme

@Composable
fun SettingsContent(
    state: SettingsState,
    onThemeSelected: (ThemeMode) -> Unit,
    onResetOnboarding: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp),
    ) {
        Text(
            text = "Theme",
            style = AppTheme.typography.headlineMedium,
            color = AppTheme.colors.contentPrimary,
        )
        Spacer(modifier = Modifier.height(12.dp))
        ThemePicker(
            current = state.themeMode,
            onSelect = onThemeSelected,
        )

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "Onboarding",
            style = AppTheme.typography.headlineMedium,
            color = AppTheme.colors.contentPrimary,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = onResetOnboarding,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Reset onboarding")
        }
    }
}

@Composable
private fun ThemePicker(
    current: ThemeMode,
    onSelect: (ThemeMode) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        ThemeMode.entries.forEach { mode ->
            val isSelected = mode == current
            if (isSelected) {
                Button(
                    onClick = { onSelect(mode) },
                    modifier = Modifier.weight(1f),
                ) {
                    Text(mode.label)
                }
            } else {
                OutlinedButton(
                    onClick = { onSelect(mode) },
                    modifier = Modifier.weight(1f),
                ) {
                    Text(mode.label)
                }
            }
        }
    }
}

private val ThemeMode.label: String
    get() = when (this) {
        ThemeMode.Light -> "Light"
        ThemeMode.Dark -> "Dark"
        ThemeMode.System -> "System"
    }
