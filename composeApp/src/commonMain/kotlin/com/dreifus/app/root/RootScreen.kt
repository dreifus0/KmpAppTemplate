package com.dreifus.app.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.dreifus.app.navigation.mainTabs
import com.dreifus.navigation.NavigationSetup
import com.dreifus.navigation.ui.LocalTabs
import dev.zacsweers.metrox.viewmodel.metroViewModel

@Composable
fun RootScreen() {
    val rootViewModel = metroViewModel<RootViewModel>()

    CompositionLocalProvider(LocalTabs provides mainTabs) {
        NavigationSetup(
            navControllersHolder = rootViewModel.navControllersHolder,
            listener = { screen -> println("Navigation: $screen") },
        )
    }
}
