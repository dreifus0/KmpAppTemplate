package com.dreifus.template.di.common.metro.activity

import com.dreifus.template.di.common.metro.viewmodel.ViewModelGraph
import dev.zacsweers.metro.GraphExtension
import dev.zacsweers.metro.MembersInjector
import dev.zacsweers.metro.Multibinds
import dev.zacsweers.metro.Provider
import kotlin.reflect.KClass

@GraphExtension(ActivityRetainedScope::class)
interface ActivityRetainedGraph : ViewModelGraph.Factory {

    @Multibinds
    val activityMembersInjectorsMap: Map<KClass<out MembersInjector<*>>, Provider<MembersInjector<*>>>

    @GraphExtension.Factory
    fun interface Factory {
        fun createActivityRetainedGraph(): ActivityRetainedGraph
    }
}
