package com.dreifus.app.di

import android.content.Context
import android.content.pm.ApplicationInfo
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings

actual class PlatformDependencies(context: Context) {
    actual val settings: Settings = SharedPreferencesSettings(
        context.applicationContext.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    )
    actual val platformName: String = "android"
    actual val isDebug: Boolean =
        (context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0
}
