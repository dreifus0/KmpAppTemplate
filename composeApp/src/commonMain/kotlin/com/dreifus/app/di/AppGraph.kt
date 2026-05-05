package com.dreifus.app.di

import com.dreifus.app.features.onboarding.data.OnboardingRepository
import com.dreifus.app.features.settings.data.ThemeRepository
import com.dreifus.arch.di.IsDebug
import com.dreifus.arch.di.PlatformName
import com.russhwolf.settings.Settings
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import dev.zacsweers.metrox.viewmodel.ViewModelGraph
import io.ktor.client.HttpClient

@DependencyGraph(AppScope::class)
interface AppGraph : ViewModelGraph {

    val onboardingRepository: OnboardingRepository
    val themeRepository: ThemeRepository

    @DependencyGraph.Factory
    fun interface Factory {
        fun create(
            @Provides settings: Settings,
            @Provides isDebug: IsDebug,
            @Provides platformName: PlatformName,
            @Provides httpClient: HttpClient,
        ): AppGraph
    }
}
