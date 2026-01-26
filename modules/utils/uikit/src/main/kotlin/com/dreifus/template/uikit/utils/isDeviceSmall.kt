package com.dreifus.template.uikit.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun isDeviceSmall(): Boolean {
    return LocalConfiguration.current.smallestScreenWidthDp <= 360
}

@Composable
fun isDeviceSmallerThan380dp(): Boolean {
    return LocalConfiguration.current.smallestScreenWidthDp < 380
}

@Composable
fun isDeviceSmallerThanOrEqual320dp(): Boolean {
    return LocalConfiguration.current.smallestScreenWidthDp <= 320
}
