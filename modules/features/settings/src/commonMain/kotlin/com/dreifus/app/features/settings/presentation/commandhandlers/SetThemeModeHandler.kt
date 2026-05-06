package com.dreifus.app.features.settings.presentation.commandhandlers

import com.dreifus.app.features.settings.data.ThemeRepository
import com.dreifus.app.features.settings.presentation.SettingsCommand
import com.dreifus.app.features.settings.presentation.SettingsEvent
import com.yavorcool.mvucore.filteringHandler

fun setThemeModeHandler(themeRepo: ThemeRepository) =
    filteringHandler<SettingsCommand.SetThemeMode, SettingsCommand, SettingsEvent> { command ->
        themeRepo.setThemeMode(command.mode)
        SettingsEvent.ThemeModeLoaded(command.mode)
    }
