package com.dreifus.navigation

import androidx.compose.runtime.Composable
import androidx.core.os.bundleOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.dreifus.navigation.screen.BaseScreen
import com.dreifus.template.di.common.metro.viewmodel.metroViewModel
import kotlin.reflect.KProperty1

fun screenTrackingInfo(screenName: String, valueTeam: String): Map<String, String> {
    return mapOf("measuredScreen" to screenName, "valueTeam" to valueTeam)
}

const val KEY_SCREEN = "KEY_SCREEN"

/**
 * Allows retrieving parameters from BaseScreen subclasses in ViewModel via SavedStateHandle.
 *
 * Example: private val email: String? = savedStateHandle.fromScreen(RegistrationScreen::email)
 */
@Composable
inline fun <reified VM : ViewModel> BaseScreen.screenViewModel(
    vararg arguments: Pair<String, Any?>,
): VM = metroViewModel(
    defaultArguments = bundleOf(*arguments).also {
        it.putParcelable(KEY_SCREEN, this)
    }
)

inline fun <reified D : BaseScreen, T : Any?> SavedStateHandle.fromScreen(
    property: KProperty1<D, T>,
): T = screen<D>().let(property)

inline fun <reified D : BaseScreen> SavedStateHandle.screen(): D =
    requireNotNull(get<D>(KEY_SCREEN))
