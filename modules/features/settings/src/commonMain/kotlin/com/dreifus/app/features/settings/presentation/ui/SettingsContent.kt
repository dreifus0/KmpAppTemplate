package com.dreifus.app.features.settings.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dreifus.app.features.settings.data.ThemeMode
import com.dreifus.app.features.settings.presentation.SettingsState
import com.dreifus.template.uikit.button.AppButton
import com.dreifus.template.uikit.preview.AppPreview
import com.dreifus.template.uikit.style.AppTheme
import com.dreifus.template.uikit.toolbar.AppToolbar
import kmptemplateapp.modules.features.settings.generated.resources.Res
import kmptemplateapp.modules.features.settings.generated.resources.settings_onboarding_section
import kmptemplateapp.modules.features.settings.generated.resources.settings_reset_onboarding
import kmptemplateapp.modules.features.settings.generated.resources.settings_theme_dark
import kmptemplateapp.modules.features.settings.generated.resources.settings_theme_dark_description
import kmptemplateapp.modules.features.settings.generated.resources.settings_theme_light
import kmptemplateapp.modules.features.settings.generated.resources.settings_theme_light_description
import kmptemplateapp.modules.features.settings.generated.resources.settings_theme_section
import kmptemplateapp.modules.features.settings.generated.resources.settings_theme_system
import kmptemplateapp.modules.features.settings.generated.resources.settings_theme_system_description
import kmptemplateapp.modules.features.settings.generated.resources.settings_title
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SettingsContent(
    state: SettingsState,
    onThemeSelected: (ThemeMode) -> Unit,
    onResetOnboarding: () -> Unit,
) {
    Scaffold(
        containerColor = AppTheme.colors.backgroundBase,
        topBar = {
            AppToolbar(
                title = stringResource(Res.string.settings_title),
                button = null,
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp, vertical = 16.dp),
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
            Spacer(modifier = Modifier.height(12.dp))
            AppButton(
                text = stringResource(Res.string.settings_reset_onboarding),
                onClick = onResetOnboarding,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun ThemePicker(
    current: ThemeMode,
    onSelect: (ThemeMode) -> Unit,
) {
    val shape = RoundedCornerShape(12.dp)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(AppTheme.colors.backgroundTabBar),
    ) {
        val orderedModes = listOf(ThemeMode.System, ThemeMode.Light, ThemeMode.Dark)
        orderedModes.forEachIndexed { index, mode ->
            if (index > 0) {
                HorizontalDivider(
                    modifier = Modifier.padding(start = 16.dp),
                    color = AppTheme.colors.contentDividers,
                )
            }
            ThemeModeRow(
                mode = mode,
                isSelected = mode == current,
                onSelect = { onSelect(mode) },
            )
        }
    }
}

@Composable
private fun ThemeModeRow(
    mode: ThemeMode,
    isSelected: Boolean,
    onSelect: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onSelect)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ThemeThumbnail(mode = mode)
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(mode.labelRes),
                style = AppTheme.typography.bodyMedium,
                color = AppTheme.colors.contentPrimary,
            )
            Text(
                text = stringResource(mode.descriptionRes),
                style = AppTheme.typography.bodySmall,
                color = AppTheme.colors.contentSecondary,
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        SelectionIndicator(isSelected = isSelected)
    }
}

@Composable
private fun ThemeThumbnail(mode: ThemeMode) {
    val shape = RoundedCornerShape(6.dp)
    Box(
        modifier = Modifier
            .size(width = 56.dp, height = 72.dp)
            .clip(shape)
            .border(1.dp, AppTheme.colors.contentDividers, shape),
    ) {
        when (mode) {
            ThemeMode.System -> {
                Row(modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .background(Color(0xFFFFFFFF)),
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .background(Color(0xFF1C1C1E)),
                    )
                }
            }
            ThemeMode.Light -> {
                Box(modifier = Modifier.fillMaxSize().background(Color(0xFFFFFFFF)))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(11.dp)
                        .background(Color(0xFFF4F4F4)),
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 6.dp, bottom = 7.dp)
                        .width(28.dp)
                        .height(5.dp)
                        .background(Color(0xFF26914A), RoundedCornerShape(3.dp)),
                )
            }
            ThemeMode.Dark -> {
                Box(modifier = Modifier.fillMaxSize().background(Color(0xFF000000)))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(11.dp)
                        .background(Color(0xFF141414)),
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 6.dp, bottom = 7.dp)
                        .width(28.dp)
                        .height(5.dp)
                        .background(Color(0xFF095B2F), RoundedCornerShape(3.dp)),
                )
            }
        }
    }
}

@Composable
private fun SelectionIndicator(isSelected: Boolean) {
    if (isSelected) {
        Box(
            modifier = Modifier
                .size(22.dp)
                .background(AppTheme.colors.accentPrimary, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "✓",
                color = AppTheme.colors.accentOnPrimary,
                style = AppTheme.typography.bodySmall,
            )
        }
    } else {
        Box(
            modifier = Modifier
                .size(22.dp)
                .border(1.5.dp, AppTheme.colors.contentDividers, CircleShape),
        )
    }
}

private val ThemeMode.labelRes: StringResource
    get() = when (this) {
        ThemeMode.Light -> Res.string.settings_theme_light
        ThemeMode.Dark -> Res.string.settings_theme_dark
        ThemeMode.System -> Res.string.settings_theme_system
    }

private val ThemeMode.descriptionRes: StringResource
    get() = when (this) {
        ThemeMode.Light -> Res.string.settings_theme_light_description
        ThemeMode.Dark -> Res.string.settings_theme_dark_description
        ThemeMode.System -> Res.string.settings_theme_system_description
    }

@Preview
@Composable
private fun SettingsSystemPreview() {
    AppPreview {
        SettingsContent(
            state = SettingsState(ThemeMode.System),
            onThemeSelected = {},
            onResetOnboarding = {},
        )
    }
}

@Preview
@Composable
private fun SettingsLightPreview() {
    AppPreview {
        SettingsContent(
            state = SettingsState(ThemeMode.Light),
            onThemeSelected = {},
            onResetOnboarding = {},
        )
    }
}

@Preview
@Composable
private fun SettingsDarkPreview() {
    AppPreview {
        SettingsContent(
            state = SettingsState(ThemeMode.Dark),
            onThemeSelected = {},
            onResetOnboarding = {},
        )
    }
}
