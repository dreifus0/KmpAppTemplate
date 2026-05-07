package com.dreifus.app.navigation

import com.dreifus.app.features.pokemon.presentation.list.ui.PokemonListScreen
import com.dreifus.app.features.settings.presentation.ui.SettingsScreen
import com.dreifus.navigation.screen.regular.RegularScreen
import com.dreifus.navigation.ui.RootScreenWithTabs
import com.dreifus.template.uikit.icon.List24
import com.dreifus.template.uikit.icon.Settings24
import com.dreifus.template.uikit.style.AppIcons
import com.dreifus.template.uikit.tabs.TabInfo
import kmptemplateapp.composeapp.generated.resources.Res
import kmptemplateapp.composeapp.generated.resources.tab_pokemon
import kmptemplateapp.composeapp.generated.resources.tab_settings
import org.jetbrains.compose.resources.stringResource
import kotlin.reflect.KClass

enum class HomeTabs(
    override val screenFactory: () -> RegularScreen,
    override val screenClass: KClass<out RegularScreen>,
) : RootScreenWithTabs.TabData {
    Pokemon(screenFactory = ::PokemonListScreen, screenClass = PokemonListScreen::class),
    Settings(screenFactory = ::SettingsScreen, screenClass = SettingsScreen::class),
}

val mainTabs: List<TabInfo<RootScreenWithTabs.TabData>> = listOf(
    TabInfo(
        icon = AppIcons.List24,
        title = { stringResource(Res.string.tab_pokemon) },
        data = HomeTabs.Pokemon,
    ),
    TabInfo(
        icon = AppIcons.Settings24,
        title = { stringResource(Res.string.tab_settings) },
        data = HomeTabs.Settings,
    ),
)
