package com.dreifus.template.di.common.metro.activity

import com.dreifus.template.di.common.metro.viewmodel.ViewModelGraph
import dev.zacsweers.metro.GraphExtension

@GraphExtension(ActivityRetainedScope::class)
interface ActivityRetainedGraph : ViewModelGraph.Factory {

    @GraphExtension.Factory
    fun interface Factory {
        fun createActivityRetainedGraph(): ActivityRetainedGraph
    }
}
