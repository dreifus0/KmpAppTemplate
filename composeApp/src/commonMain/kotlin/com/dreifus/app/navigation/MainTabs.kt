package com.dreifus.app.navigation

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import com.dreifus.app.features.pokemon.presentation.list.ui.PokemonListScreen
import com.dreifus.app.features.settings.presentation.ui.SettingsScreen
import com.dreifus.navigation.screen.regular.RegularScreen
import com.dreifus.navigation.ui.RootScreenWithTabs
import com.dreifus.template.uikit.tabs.TabInfo
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
        icon = { rememberVectorPainter(ListIcon) },
        title = { "Pokémon" },
        data = HomeTabs.Pokemon,
    ),
    TabInfo(
        icon = { rememberVectorPainter(SettingsIcon) },
        title = { "Settings" },
        data = HomeTabs.Settings,
    ),
)

private val ListIcon: ImageVector by lazy {
    ImageVector.Builder(
        name = "List",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        path(fill = SolidColor(Color.Black)) {
            moveTo(3f, 6f); lineTo(21f, 6f); lineTo(21f, 8f); lineTo(3f, 8f); close()
            moveTo(3f, 11f); lineTo(21f, 11f); lineTo(21f, 13f); lineTo(3f, 13f); close()
            moveTo(3f, 16f); lineTo(21f, 16f); lineTo(21f, 18f); lineTo(3f, 18f); close()
        }
    }.build()
}

private val SettingsIcon: ImageVector by lazy {
    ImageVector.Builder(
        name = "Settings",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        path(fill = SolidColor(Color.Black)) {
            moveTo(12f, 8f)
            arcTo(4f, 4f, 0f, true, true, 12f, 16f)
            arcTo(4f, 4f, 0f, true, true, 12f, 8f)
            close()
            moveTo(11f, 2f); lineTo(13f, 2f); lineTo(13f, 6f); lineTo(11f, 6f); close()
            moveTo(11f, 18f); lineTo(13f, 18f); lineTo(13f, 22f); lineTo(11f, 22f); close()
            moveTo(2f, 11f); lineTo(6f, 11f); lineTo(6f, 13f); lineTo(2f, 13f); close()
            moveTo(18f, 11f); lineTo(22f, 11f); lineTo(22f, 13f); lineTo(18f, 13f); close()
        }
    }.build()
}
