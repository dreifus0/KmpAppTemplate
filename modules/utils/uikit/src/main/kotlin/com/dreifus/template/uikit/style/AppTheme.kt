package com.dreifus.template.uikit.style

import android.app.Activity
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RippleConfiguration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

object AppTheme {
    val colors: AppColors
        @Composable
        get() = LocalAppColors.current

    val typography: AppTypography
        @Composable
        get() = LocalAppTypography.current

    val shapes = AppShapes

    val themedParams: ThemedParamsMap
        @Composable
        get() = LocalThemedParams.current
}

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("UnusedPrivateMember")
@Composable
fun AppTheme(
    colors: AppColors,
    typography: AppTypography,
    themedParams: ThemedParamsMap = LocalThemedParams.current,
    icons: IconsProvider = LocalIcons.current,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true, // Оставим для тестов на будущее
    content: @Composable () -> Unit,
) {
    // Оставим для тестов на будущее
//    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }
//
//        darkTheme -> DarkColorScheme
//        else -> LightColorScheme
//    }
    val colorScheme = colors.toComposeColorScheme()
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            // Может не быть активити, если вызывается из-под попапа
            val window = (view.context as? Activity)?.window ?: return@SideEffect
            window.statusBarColor = 0 // transparent
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !colors.isDarkTheme
        }
    }

    CompositionLocalProvider(
        LocalAppColors provides colors,
        LocalAppTypography provides typography,
        LocalIcons provides icons,
        LocalThemedParams provides themedParams,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography.toComposeTypography(),
        ) {
            val rippleColor = colors.backgroundActive
            val rippleConfiguration = RippleConfiguration(
                color = rippleColor,
                rippleAlpha = RippleAlpha(
                    draggedAlpha = rippleColor.alpha,
                    focusedAlpha = rippleColor.alpha,
                    hoveredAlpha = rippleColor.alpha,
                    pressedAlpha = rippleColor.alpha,
                ),
            )
            CompositionLocalProvider(
                LocalRippleConfiguration provides rippleConfiguration,
                content = content,
            )
        }
    }
}
