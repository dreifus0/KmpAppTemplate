package com.dreifus.template.di.common.metro.viewmodel

import android.os.Bundle
import androidx.lifecycle.DEFAULT_ARGS_KEY
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.MutableCreationExtras
import com.dreifus.template.di.common.metro.activity.ActivityRetainedGraph
import com.dreifus.template.di.common.metro.activity.ActivityRetainedScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.Provider
import kotlin.reflect.KClass

/**
 * A [androidx.lifecycle.ViewModelProvider.Factory] that uses an injected map of [KClass] to [Provider] of [androidx.lifecycle.ViewModel]
 * to create ViewModels.
 */
@ContributesBinding(ActivityRetainedScope::class)
@Inject
class MetroViewModelFactory(
    val graph: ActivityRetainedGraph,
    private val defaultArguments: Bundle? = null,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val viewModelGraph = viewModelGraph(extras)
        val provider = viewModelGraph.viewModelProviders[modelClass.kotlin]
            ?: throw IllegalArgumentException("Unknown view model class $modelClass")

        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        return modelClass.cast(provider())
    }

    fun viewModelGraph(extras: CreationExtras): ViewModelGraph =
        graph.createViewModelGraph(
            if (defaultArguments != null) {
                MutableCreationExtras(extras).apply {
                    set(DEFAULT_ARGS_KEY, defaultArguments)
                }
            } else {
                extras
            }
        )

    fun withDefaultArguments(defaultArguments: Bundle?) = if (defaultArguments != null) {
        MetroViewModelFactory(graph, defaultArguments)
    } else {
        this
    }
}
