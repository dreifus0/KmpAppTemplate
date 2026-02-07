package com.dreifus.template.uikit.appTheme

enum class AppTheme {
    SYSTEM,
    LIGHT,
    DARK;

    companion object {
        fun fromString(value: String?): AppTheme = value?.let { valueOf(it) } ?: SYSTEM
    }
}
