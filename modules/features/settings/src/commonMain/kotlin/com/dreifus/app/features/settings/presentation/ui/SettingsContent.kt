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
import com.dreifus.template.uikit.button.AppButton
import com.dreifus.template.uikit.style.AppTheme
import kmptemplateapp.modules.features.settings.generated.resources.Res
import kmptemplateapp.modules.features.settings.generated.resources.settings_onboarding_section
import kmptemplateapp.modules.features.settings.generated.resources.settings_reset_onboarding
import kmptemplateapp.modules.features.settings.generated.resources.settings_theme_dark
import kmptemplateapp.modules.features.settings.generated.resources.settings_theme_light
import kmptemplateapp.modules.features.settings.generated.resources.settings_theme_section
import kmptemplateapp.modules.features.settings.generated.resources.settings_theme_system
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

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
            text = stringResource(Res.string.settings_theme_section),
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
            text = stringResource(Res.string.settings_onboarding_section),
            style = AppTheme.typography.headlineMedium,
            color = AppTheme.colors.contentPrimary,
        )
        Spacer(12.dp)
        AppButton(
            text = stringResource(Res.string.settings_reset_onboarding),
            onClick = onResetOnboarding,
            modifier = Modifier.fillMaxWidth(),
        )
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
                    Text(stringResource(mode.labelRes))
                }
            } else {
                OutlinedButton(
                    onClick = { onSelect(mode) },
                    modifier = Modifier.weight(1f),
                ) {
                    Text(stringResource(mode.labelRes))
                }
            }
        }
    }
}

private val ThemeMode.labelRes: StringResource
    get() = when (this) {
        ThemeMode.Light -> Res.string.settings_theme_light
        ThemeMode.Dark -> Res.string.settings_theme_dark
        ThemeMode.System -> Res.string.settings_theme_system
    }
