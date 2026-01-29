package com.dreifus.app.ui

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dreifus.core.extensions.observeWithLifecycle
import com.dreifus.navigation.NavigationSetup
import com.dreifus.navigation.controller.NavControllersHolder
import com.dreifus.template.di.common.metro.AndroidEntryPoint
import com.dreifus.template.di.common.metro.activity.MetroActivity
import com.dreifus.template.di.common.metro.getInjectorClass
import com.dreifus.template.di.common.metro.viewmodel.metroViewModel
import com.dreifus.template.uikit.appTheme.AppThemeManager
import com.dreifus.template.uikit.style.AppTheme
import com.dreifus.template.uikit.style.app.AndroidAppTheme
import com.dreifus.template.uikit.style.app.SpecificAppTheme
import dev.zacsweers.metro.HasMemberInjections
import dev.zacsweers.metro.Inject

@HasMemberInjections
@AndroidEntryPoint
class MainActivity : MetroActivity() {

    private val appTheme: SpecificAppTheme = AndroidAppTheme

    override val membersInjectorClass = getInjectorClass()

    @Inject
    lateinit var navControllersHolder: NavControllersHolder

    private val isThemeDark = mutableStateOf(false)

    override fun onStart() {
        super.onStart()
        AppThemeManager.loadAppTheme(this)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(newBase)
        AppThemeManager.setCurrentConfigurationUiMode(newBase.resources.configuration.uiMode)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        AppThemeManager.setCurrentConfigurationUiMode(newConfig.uiMode)
        isThemeDark.value = AppThemeManager.isAppInDarkTheme(this)
    }

    @Suppress("LongMethod")
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        isThemeDark.value = AppThemeManager.isAppInDarkTheme(this)
        setContent {
            AppContent()
        }
    }

    @Composable
    private fun AppContent() {
        val viewModel = metroViewModel<MainActivityViewModel>()
        viewModel.store.effects.observeWithLifecycle(action = viewModel.effectHandler::handleEffect)
        val state by viewModel.store.state.collectAsStateWithLifecycle()

        appTheme.invoke(isThemeDark.value) {
            Surface(
                modifier = Modifier.Companion
                    .fillMaxSize(),
                color = AppTheme.colors.backgroundBase
            ) {
                NavigationSetup(
                    navControllersHolder = navControllersHolder,
                    listener = { screen ->
                        Log.d("Navigation", screen.toString())
                    },
                )
            }
        }
    }
}