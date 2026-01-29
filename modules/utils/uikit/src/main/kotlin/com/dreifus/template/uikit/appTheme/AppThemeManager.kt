package com.dreifus.template.uikit.appTheme

import android.app.UiModeManager
import android.content.Context
import android.content.Context.UI_MODE_SERVICE
import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_UNDEFINED
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate


object AppThemeManager {
    private const val SELECTED_APP_THEME = "selected_app_theme"
    private const val APP_THEME_PREFS = "app_theme_prefs"
    private var currentUiMode: Int = UI_MODE_NIGHT_UNDEFINED

    fun setCurrentConfigurationUiMode(uiMode: Int) {
        currentUiMode = uiMode
    }

    fun getStoredAppTheme(context: Context): AppTheme {
        val preferences = context.getSharedPreferences(APP_THEME_PREFS, Context.MODE_PRIVATE)
        return AppTheme.fromString(preferences.getString(SELECTED_APP_THEME, AppTheme.SYSTEM.name))
    }

    fun isAppInDarkTheme(context: Context): Boolean {
        return when (getStoredAppTheme(context)) {
            AppTheme.SYSTEM -> (currentUiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
            AppTheme.DARK -> true
            AppTheme.LIGHT -> false
        }
    }

    fun changeAppTheme(appTheme: AppTheme, context: Context) {
        val preferences = context.getSharedPreferences(APP_THEME_PREFS, Context.MODE_PRIVATE)
        preferences.edit().putString(SELECTED_APP_THEME, appTheme.name).commit()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            setAppThemeUiModeManager(appTheme, context)
        } else {
            setAppThemeAppCompatDelegate(appTheme)
        }
    }

    fun loadAppTheme(context: Context) {
        // AppCompatDelegate does not persist the previously selected theme
        // when activity restarts, theme resets to AppCompatDelegate.MODE_NIGHT_UNSPECIFIED
        // set it manually
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            setAppThemeAppCompatDelegate(getStoredAppTheme(context))
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun setAppThemeUiModeManager(appTheme: AppTheme, context: Context) {
        val uiModeManager = context.getSystemService(UI_MODE_SERVICE) as UiModeManager
        val nightMode = when (appTheme) {
            AppTheme.SYSTEM -> UiModeManager.MODE_NIGHT_CUSTOM
            AppTheme.LIGHT -> UiModeManager.MODE_NIGHT_NO
            AppTheme.DARK -> UiModeManager.MODE_NIGHT_YES
        }
        uiModeManager.setApplicationNightMode(nightMode)
    }

    private fun setAppThemeAppCompatDelegate(appTheme: AppTheme) {
        val nightMode = when (appTheme) {
            AppTheme.SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            AppTheme.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
            AppTheme.DARK -> AppCompatDelegate.MODE_NIGHT_YES
        }
        AppCompatDelegate.setDefaultNightMode(nightMode)
    }
}
