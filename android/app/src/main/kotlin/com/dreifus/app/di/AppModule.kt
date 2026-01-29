package com.dreifus.app.di

import com.dreifus.app.ui.mvu.MainScreen
import com.dreifus.navigation.controller.NavControllersHolder
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn

@ContributesTo(AppScope::class)
interface AppModule {

    @Provides
    @SingleIn(AppScope::class)
    fun provideNavControllersHolder(): NavControllersHolder =
        NavControllersHolder(MainScreen())
}
