package com.dreifus.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.dreifus.app.counter.ui.CounterScreen
import com.dreifus.app.navigation.mainTabs
import com.dreifus.navigation.NavigationSetup
import com.dreifus.navigation.controller.NavControllersHolder
import com.dreifus.navigation.ui.LocalTabs
import com.dreifus.app.di.AppGraph
import com.dreifus.template.di.common.metro.viewmodel.LocalMetroViewModelFactory
import com.dreifus.template.di.common.metro.viewmodel.MetroViewModelFactory
import dev.zacsweers.metro.createGraph
import com.dreifus.template.uikit.style.AppTheme
import com.dreifus.template.uikit.style.app.DefaultAppTheme

@Composable
fun App(
    navControllersHolder: NavControllersHolder = NavControllersHolder(CounterScreen()),
) {
    val graph = remember { createGraph<AppGraph>().createActivityRetainedGraph() }
    val factory = remember { MetroViewModelFactory(graph) }

    DefaultAppTheme(darkTheme = isSystemInDarkTheme()) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = AppTheme.colors.backgroundBase
        ) {
            CompositionLocalProvider(
                LocalTabs provides mainTabs,
                LocalMetroViewModelFactory provides factory,
            ) {
                NavigationSetup(
                    navControllersHolder = navControllersHolder,
                    listener = { screen ->
                        println("Navigation: $screen")
                    },
                )
            }
        }
    }
}
