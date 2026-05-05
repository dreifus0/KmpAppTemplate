package com.dreifus.app.di

import com.dreifus.app.features.onboarding.data.OnboardingRepository
import com.dreifus.arch.di.IsDebug
import com.dreifus.arch.di.PlatformName
import com.russhwolf.settings.Settings
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import dev.zacsweers.metrox.viewmodel.ViewModelGraph

@DependencyGraph(AppScope::class)
interface AppGraph : ViewModelGraph {

    val onboardingRepository: OnboardingRepository

    @DependencyGraph.Factory
    fun interface Factory {
        fun create(
            @Provides settings: Settings,
            @Provides isDebug: IsDebug,
            @Provides platformName: PlatformName,
        ): AppGraph
    }
}
