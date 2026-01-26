package com.dreifus.template.uikit.dialog

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dreifus.template.uikit.style.AppTheme
import com.dreifus.template.uikit.utils.isDeviceSmall

@Composable
fun BottomSheetHeader(
    @StringRes titleRes: Int,
    paddingStart: Dp = 0.dp,
    paddingEnd: Dp = 48.dp,
    paddingTop: Dp = 32.dp,
    paddingBottom: Dp = 8.dp,
) {
    BottomSheetHeader(
        title = stringResource(titleRes),
        paddingStart = paddingStart,
        paddingEnd = paddingEnd,
        paddingTop = paddingTop,
        paddingBottom = paddingBottom,
    )
}

@Composable
fun BottomSheetHeader(
    title: String,
    paddingStart: Dp = 0.dp,
    paddingEnd: Dp = 48.dp,
    paddingTop: Dp = 32.dp,
    paddingBottom: Dp = 8.dp,
) {
    val style =
        if (isDeviceSmall() && LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            AppTheme.typography.headlineLarge
        } else {
            AppTheme.typography.heading5
        }
    Text(
        text = title,
        style = style,
        color = AppTheme.colors.contentPrimary,
        modifier = Modifier.padding(
            start = paddingStart,
            end = paddingEnd,
            top = paddingTop,
            bottom = paddingBottom
        )
    )
}
