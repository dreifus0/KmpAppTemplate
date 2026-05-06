package com.dreifus.app.di

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import kotlin.experimental.ExperimentalNativeApi
import platform.Foundation.NSUserDefaults

actual class PlatformDependencies {
    actual val settings: Settings = NSUserDefaultsSettings(NSUserDefaults.standardUserDefaults)
    actual val platformName: String = "ios"
    @OptIn(ExperimentalNativeApi::class)
    actual val isDebug: Boolean = Platform.isDebugBinary
}
