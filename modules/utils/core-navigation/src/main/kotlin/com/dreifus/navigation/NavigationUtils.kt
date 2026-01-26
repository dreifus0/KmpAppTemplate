package com.dreifus.navigation

import androidx.compose.runtime.Composable
import androidx.core.os.bundleOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.dreifus.navigation.screen.BaseDestination
import com.dreifus.template.di.common.metro.viewmodel.metroViewModel
import kotlin.reflect.KProperty1

fun screenTrackingInfo(screenName: String, valueTeam: String): Map<String, String> {
    return mapOf("measuredScreen" to screenName, "valueTeam" to valueTeam)
}

const val KEY_DESTINATION = "KEY_DESTINATION"

/**
 * Позволяет во вью модельке получать параметры из потомков BaseDestination через SavedStateHandle.
 *
 * Пример: private val email: String? = savedStateHandle.fromDestination(RegistrationDestination::email)
 */
@Composable
inline fun <reified VM : ViewModel> BaseDestination.destinationViewModel(
    vararg arguments: Pair<String, Any?>,
): VM = metroViewModel(
    defaultArguments = bundleOf(*arguments).also {
        it.putParcelable(KEY_DESTINATION, this)
    }
)

inline fun <reified D : BaseDestination, T : Any?> SavedStateHandle.fromDestination(
    property: KProperty1<D, T>,
): T = destination<D>().let(property)

inline fun <reified D : BaseDestination> SavedStateHandle.destination(): D =
    requireNotNull(get<D>(KEY_DESTINATION))
