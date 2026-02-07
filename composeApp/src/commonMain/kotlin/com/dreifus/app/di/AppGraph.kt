package com.dreifus.app.di

import com.dreifus.template.di.common.metro.activity.ActivityRetainedGraph
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph

@DependencyGraph(AppScope::class)
interface AppGraph : ActivityRetainedGraph.Factory
