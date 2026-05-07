package com.dreifus.template.uikit.style.app

import androidx.compose.ui.graphics.Color
import com.dreifus.template.uikit.style.AppColors

object DefaultAppTheme : SpecificAppTheme() {
    override val lightColors = AppColors(
        contentPrimary = Color(0xFF030A05),
        contentSecondary = Color(0x99030A05),
        contentDividers = Color(0x33808080),
        contentBorder = Color(0x33FFFFFF),
        contentShadow = Color(0x26000000),
        backgroundBase = Color(0xFFFFFFFF),
        backgroundSecondary = Color(0xFFF4F4F4),
        backgroundDisabled = Color(0x1A808080),
        backgroundActive = Color(0x1A000000),
        backgroundTabBar = Color(0xFFD0D0D0),
        accentPrimary = Color(0xFF26914A),
        accentSecondary = Color(0xFF26914A),
        accentError = Color(0xFFE03636),
        accentLink = Color(0xFF1083FF),
        accentOnPrimary = Color(0xFFFFFFFF),
        accentOnSecondary = Color(0xFFFFFFFF),
        inputBorderError = Color(0xFFE03636),
        inputBorderFocus = Color(0xFF26914A),
        inputTextDefault = Color(0xFF030A05),
        inputTextError = Color(0xFFE03636),
    )

    override val darkColors = AppColors(
        contentPrimary = Color(0xFFF4F4F4),
        contentSecondary = Color(0x99F4F4F4),
        contentDividers = Color(0x33808080),
        contentBorder = Color(0x33FFFFFF),
        contentShadow = Color(0x26000000),
        backgroundBase = Color(0xFF000000),
        backgroundSecondary = Color(0xFF141414),
        backgroundDisabled = Color(0x1A808080),
        backgroundActive = Color(0x1A000000),
        backgroundTabBar = Color(0xFF1C1C1E),
        accentPrimary = Color(0xFF095B2F),
        accentSecondary = Color(0xFF095B2F),
        accentError = Color(0xFFE03636),
        accentLink = Color(0xFF1083FF),
        accentOnPrimary = Color(0xFFDBDBDB),
        accentOnSecondary = Color(0xFFDBDBDB),
        inputBorderError = Color(0xFFE03636),
        inputBorderFocus = Color(0xFF095B2F),
        inputTextDefault = Color(0xFFF4F4F4),
        inputTextError = Color(0xFFE03636),
    )
}
