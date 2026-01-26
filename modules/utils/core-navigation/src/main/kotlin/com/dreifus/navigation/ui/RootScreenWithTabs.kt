package com.dreifus.navigation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import com.dreifus.navigation.IInsetsConsumer
import com.dreifus.navigation.controller.Navigation
import com.dreifus.navigation.navigationBarsPaddingIfNeeded
import com.dreifus.navigation.screen.BaseDestination
import com.dreifus.navigation.screen.regular.RegularScreen
import com.dreifus.navigation.statusBarsPaddingIfNeeded
import com.dreifus.template.uikit.tabs.TabBar
import com.dreifus.template.uikit.tabs.TabInfo
import kotlin.reflect.KClass

interface RootScreenWithTabs : RegularScreen {
    override fun <T : BaseDestination> navEntry() = navEntry<T> { destination ->
        val tabs = LocalTabs.current
        val currentTab = remember(tabs, destination) {
            tabs.find { it.data.destinationClass == destination::class }
        }
        val localDensity = LocalDensity.current
        var consumePaddingDp by remember {
            mutableStateOf(0.dp)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPaddingIfNeeded(destination as? IInsetsConsumer)
                .navigationBarsPaddingIfNeeded(destination as? IInsetsConsumer)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .semantics { testTagsAsResourceId = true }
                    .testTag(destination::class.simpleName!!)
                    .consumeWindowInsets(PaddingValues(bottom = consumePaddingDp))
                    .imePadding()
            ) {
                destination.Content()
            }
            val nav = Navigation.regular
            TabBar(
                modifier = Modifier.onGloballyPositioned { coordinates ->
                    consumePaddingDp = with(localDensity) { coordinates.size.height.toDp() }
                },
                tabs = tabs,
                currentTab = currentTab,
                onTabClick = {
                    nav.replaceAll(it.data.destinationFactory())
                },
            )
        }
    }

    interface TabData {
        val destinationFactory: () -> RegularScreen
        val destinationClass: KClass<out RegularScreen>
    }
}

val LocalTabs: ProvidableCompositionLocal<List<TabInfo<RootScreenWithTabs.TabData>>> =
    staticCompositionLocalOf { error("LocalTabs is not provided") }
