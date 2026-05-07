package com.dreifus.template.uikit.icon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import com.dreifus.template.uikit.style.AppIcons
import com.dreifus.template.uikit.style.AppTheme
import com.dreifus.template.uikit.style.cachedIcon
import com.dreifus.template.uikit.utils.wrapWith

//region:24px

val AppIcons.ArrowLeft24 by cachedIcon {
    rememberVectorPainter(ArrowBackIcon).wrapWith(tint = AppTheme.colors.contentPrimary)
}

val AppIcons.Close24 by cachedIcon {
    rememberVectorPainter(CloseIcon).wrapWith(tint = AppTheme.colors.contentPrimary)
}

val AppIcons.List24 by cachedIcon {
    rememberVectorPainter(ListIcon).wrapWith(tint = AppTheme.colors.contentPrimary)
}

val AppIcons.Settings24 by cachedIcon {
    rememberVectorPainter(SettingsIcon).wrapWith(tint = AppTheme.colors.contentPrimary)
}

val AppIcons.Search24 by cachedIcon {
    rememberVectorPainter(SearchIcon).wrapWith(tint = AppTheme.colors.contentPrimary)
}
//endregion

// Material Design ArrowBack icon (auto-mirrored)
private val ArrowBackIcon: ImageVector by lazy {
    ImageVector.Builder(
        name = "ArrowBack",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
        autoMirror = true,
    ).apply {
        path(
            fill = SolidColor(Color.Black),
        ) {
            moveTo(20f, 11f)
            horizontalLineTo(7.83f)
            lineToRelative(5.59f, -5.59f)
            lineTo(12f, 4f)
            lineToRelative(-8f, 8f)
            lineToRelative(8f, 8f)
            lineToRelative(1.41f, -1.41f)
            lineTo(7.83f, 13f)
            horizontalLineTo(20f)
            close()
        }
    }.build()
}

// Material Design Close icon
private val CloseIcon: ImageVector by lazy {
    ImageVector.Builder(
        name = "Close",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        path(
            fill = SolidColor(Color.Black),
        ) {
            moveTo(19f, 6.41f)
            lineTo(17.59f, 5f)
            lineTo(12f, 10.59f)
            lineTo(6.41f, 5f)
            lineTo(5f, 6.41f)
            lineTo(10.59f, 12f)
            lineTo(5f, 17.59f)
            lineTo(6.41f, 19f)
            lineTo(12f, 13.41f)
            lineTo(17.59f, 19f)
            lineTo(19f, 17.59f)
            lineTo(13.41f, 12f)
            close()
        }
    }.build()
}

private val ListIcon: ImageVector by lazy {
    ImageVector.Builder(
        name = "List",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        path(fill = SolidColor(Color.Black)) {
            moveTo(3f, 6f); lineTo(21f, 6f); lineTo(21f, 8f); lineTo(3f, 8f); close()
            moveTo(3f, 11f); lineTo(21f, 11f); lineTo(21f, 13f); lineTo(3f, 13f); close()
            moveTo(3f, 16f); lineTo(21f, 16f); lineTo(21f, 18f); lineTo(3f, 18f); close()
        }
    }.build()
}

private val SearchIcon: ImageVector by lazy {
    ImageVector.Builder(
        name = "Search",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        path(fill = SolidColor(Color.Black)) {
            moveTo(15.5f, 14f)
            horizontalLineToRelative(-0.79f)
            lineToRelative(-0.28f, -0.27f)
            curveTo(15.41f, 12.59f, 16f, 11.11f, 16f, 9.5f)
            curveTo(16f, 5.91f, 13.09f, 3f, 9.5f, 3f)
            reflectiveCurveTo(3f, 5.91f, 3f, 9.5f)
            reflectiveCurveTo(5.91f, 16f, 9.5f, 16f)
            curveToRelative(1.61f, 0f, 3.09f, -0.59f, 4.23f, -1.57f)
            lineToRelative(0.27f, 0.28f)
            verticalLineToRelative(0.79f)
            lineToRelative(5f, 4.99f)
            lineTo(20.49f, 19f)
            close()
            moveTo(9.5f, 14f)
            curveTo(7.01f, 14f, 5f, 11.99f, 5f, 9.5f)
            reflectiveCurveTo(7.01f, 5f, 9.5f, 5f)
            reflectiveCurveTo(14f, 7.01f, 14f, 9.5f)
            reflectiveCurveTo(11.99f, 14f, 9.5f, 14f)
            close()
        }
    }.build()
}

private val SettingsIcon: ImageVector by lazy {
    ImageVector.Builder(
        name = "Settings",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        path(fill = SolidColor(Color.Black)) {
            moveTo(12f, 8f)
            arcTo(4f, 4f, 0f, true, true, 12f, 16f)
            arcTo(4f, 4f, 0f, true, true, 12f, 8f)
            close()
            moveTo(11f, 2f); lineTo(13f, 2f); lineTo(13f, 6f); lineTo(11f, 6f); close()
            moveTo(11f, 18f); lineTo(13f, 18f); lineTo(13f, 22f); lineTo(11f, 22f); close()
            moveTo(2f, 11f); lineTo(6f, 11f); lineTo(6f, 13f); lineTo(2f, 13f); close()
            moveTo(18f, 11f); lineTo(22f, 11f); lineTo(22f, 13f); lineTo(18f, 13f); close()
        }
    }.build()
}
