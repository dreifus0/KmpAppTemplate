package com.dreifus.template.uikit.style.app

import androidx.compose.ui.graphics.Color
import com.dreifus.template.uikit.style.AppColors

object AndroidAppTheme : SpecificAppTheme() {
    override val lightColors = AppColors(
        contentPrimary = Color(0xFF030A05),
        contentSecondary = Color(0x99030A05),
        contentTertiary = Color(0x33030A05),
        contentDividers = Color(0x33808080),
        contentBorder = Color(0x33FFFFFF),
        contentShadow = Color(0x26000000),
        backgroundBase = Color(0xFFFFFFFF),
        backgroundSecondary = Color(0xFFF4F4F4),
        backgroundPositive = Color(0x1A00AF3B),
        backgroundAttention = Color(0x1AE5902E),
        backgroundNegative = Color(0x1AE03636),
        backgroundDisabled = Color(0x1A808080),
        backgroundNeutral = Color(0x211083FF),
        backgroundActive = Color(0x1A000000),
        accentPrimary = Color(0xFF26914A),
        accentSecondary = Color(0xFF26914A),
        accentPositive = Color(0xFF00AF3B),
        accentAttention = Color(0xFFE5902E),
        accentNegative = Color(0xFFE03636),
        accentLink = Color(0xFF1083FF),
        accentOnPrimary = Color(0xFFFFFFFF),
        accentOnSecondary = Color(0xFFFFFFFF),
        extraBonusContainer = Color(0x66FFE1B4),
        extraOnBonusContainer = Color(0xFFB46204),
    )

    override val darkColors = AppColors(
        contentPrimary = Color(0xFFF4F4F4),
        contentSecondary = Color(0x99F4F4F4),
        contentTertiary = Color(0x33F4F4F4),
        contentDividers = Color(0x33808080),
        contentBorder = Color(0x33FFFFFF),
        contentShadow = Color(0x26000000),
        backgroundBase = Color(0xFF000000),
        backgroundSecondary = Color(0xFF141414),
        backgroundPositive = Color(0x1A00AF3B),
        backgroundAttention = Color(0x1AE5902E),
        backgroundNegative = Color(0x1AE03636),
        backgroundDisabled = Color(0x1A808080),
        backgroundNeutral = Color(0x211083FF),
        backgroundActive = Color(0x47000000),
        accentPrimary = Color(0xFF095B2F),
        accentSecondary = Color(0xFF095B2F),
        accentPositive = Color(0xFF00AF3B),
        accentAttention = Color(0xFFE5902E),
        accentNegative = Color(0xFFE03636),
        accentLink = Color(0xFF1083FF),
        accentOnPrimary = Color(0xFFDBDBDB),
        accentOnSecondary = Color(0xFFDBDBDB),
        extraBonusContainer = Color(0x26FFE1B4),
        extraOnBonusContainer = Color(0xFFCC8D45),
    )
}
