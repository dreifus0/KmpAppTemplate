package com.dreifus.template.di.common.metro.viewmodel

import android.os.Bundle
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dreifus.template.di.common.metro.activity.MetroActivity

@Composable
inline fun <reified VM : ViewModel> metroViewModel(
    viewModelStoreOwner: ViewModelStoreOwner =
        checkNotNull(LocalViewModelStoreOwner.current) {
            "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
        },
    key: String? = null,
    defaultArguments: Bundle? = null,
): VM {
    return viewModel(viewModelStoreOwner, key, factory = metroViewModelProviderFactory(defaultArguments))
}

@Composable
inline fun <reified VM : ViewModel> metroViewModel(
    viewModelStoreOwner: ViewModelStoreOwner =
        checkNotNull(LocalViewModelStoreOwner.current) {
            "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
        },
    key: String? = null,
    defaultArguments: Bundle? = null,
    crossinline factory: ViewModelGraph.() -> VM,
): VM {
    val metroViewModelProviderFactory = metroViewModelProviderFactory(defaultArguments)
    return viewModel(
        viewModelStoreOwner,
        key,
        factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                    val viewModelGraph = metroViewModelProviderFactory.viewModelGraph(extras)
                    return modelClass.cast(viewModelGraph.factory())!!
                }
            },
    )
}

@Composable
fun metroViewModelProviderFactory(
    defaultArguments: Bundle? = null,
): MetroViewModelFactory {
    val factory = (LocalActivity.current as MetroActivity).metroViewModelFactory
    return factory.withDefaultArguments(defaultArguments)
}
