package com.dreifus.template.di.common.metro.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.reflect.KClass

val LocalMetroViewModelFactory = staticCompositionLocalOf<MetroViewModelFactory> {
    error("LocalMetroViewModelFactory is not provided. Provide it at the App level.")
}

@Composable
inline fun <reified VM : ViewModel> metroViewModel(
    viewModelStoreOwner: ViewModelStoreOwner =
        checkNotNull(LocalViewModelStoreOwner.current) {
            "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
        },
    key: String? = null,
): VM {
    val factory = LocalMetroViewModelFactory.current
    return viewModel(viewModelStoreOwner, key, factory = factory)
}

@Composable
inline fun <reified VM : ViewModel> metroViewModel(
    viewModelStoreOwner: ViewModelStoreOwner =
        checkNotNull(LocalViewModelStoreOwner.current) {
            "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
        },
    key: String? = null,
    crossinline factory: ViewModelGraph.() -> VM,
): VM {
    val metroFactory = LocalMetroViewModelFactory.current
    return viewModel(
        viewModelStoreOwner,
        key,
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
                val viewModelGraph = metroFactory.viewModelGraph(extras)
                @Suppress("UNCHECKED_CAST")
                return viewModelGraph.factory() as T
            }
        },
    )
}
