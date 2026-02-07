package com.dreifus.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dreifus.app.counter.ui.CounterScreen
import com.dreifus.app.di.RootViewModel
import com.dreifus.app.navigation.mainTabs
import com.dreifus.app.stub.ui.StubScreen
import com.dreifus.navigation.NavigationSetup
import com.dreifus.navigation.controller.NavControllersHolder
import com.dreifus.navigation.controller.TabNavState
import com.dreifus.navigation.ui.LocalTabs
import com.dreifus.template.uikit.style.AppTheme
import com.dreifus.template.uikit.style.app.DefaultAppTheme
import dev.zacsweers.metrox.viewmodel.LocalMetroViewModelFactory

@Composable
fun App(
    navControllersHolder: NavControllersHolder = remember {
        val tabNavState = TabNavState(
            tabRoots = listOf(CounterScreen(), StubScreen()),
            initialActiveIndex = 0,
        )
        NavControllersHolder(tabNavState = tabNavState)
    },
) {
    val rootViewModel = viewModel { RootViewModel() }

    DefaultAppTheme(darkTheme = isSystemInDarkTheme()) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = AppTheme.colors.backgroundBase
        ) {
            CompositionLocalProvider(
                LocalTabs provides mainTabs,
                LocalMetroViewModelFactory provides rootViewModel.factory,
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
