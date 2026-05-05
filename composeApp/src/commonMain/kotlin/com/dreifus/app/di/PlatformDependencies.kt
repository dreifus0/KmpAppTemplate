package com.dreifus.app.di

import com.russhwolf.settings.Settings

expect class PlatformDependencies {
    val settings: Settings
    val isDebug: Boolean
    val platformName: String
}
