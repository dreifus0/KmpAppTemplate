package com.dreifus.template.di.common.metro.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.dreifus.template.di.common.metro.activity.ActivityRetainedGraph
import com.dreifus.template.di.common.metro.activity.ActivityRetainedScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import kotlin.reflect.KClass

@ContributesBinding(ActivityRetainedScope::class)
@Inject
class MetroViewModelFactory(
    val graph: ActivityRetainedGraph,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
        val viewModelGraph = viewModelGraph(extras)
        val provider = viewModelGraph.viewModelProviders[modelClass]
            ?: throw IllegalArgumentException("Unknown view model class $modelClass")
        @Suppress("UNCHECKED_CAST")
        return provider() as T
    }

    fun viewModelGraph(extras: CreationExtras): ViewModelGraph =
        graph.createViewModelGraph(extras)
}
