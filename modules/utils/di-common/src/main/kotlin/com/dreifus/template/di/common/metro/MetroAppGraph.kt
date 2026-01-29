package com.dreifus.template.di.common.metro

import android.app.Application
import android.content.Context
import com.dreifus.template.di.common.ApplicationContext
import com.dreifus.template.di.common.metro.activity.ActivityRetainedGraph
import dev.zacsweers.metro.MembersInjector
import dev.zacsweers.metro.Multibinds
import dev.zacsweers.metro.Provider
import dev.zacsweers.metro.Provides
import kotlin.reflect.KClass

interface MetroAppGraph : ActivityRetainedGraph.Factory {

    @Multibinds(allowEmpty = true)
    val appMembersInjectorsMap: Map<KClass<out MembersInjector<*>>, Provider<MembersInjector<*>>>

    @Provides
    @ApplicationContext
    fun provideContext(app: Application): Context = app
}
