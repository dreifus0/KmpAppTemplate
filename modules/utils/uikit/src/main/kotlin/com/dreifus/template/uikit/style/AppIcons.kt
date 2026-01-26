package com.dreifus.template.uikit.style

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty0

object AppIcons

@Stable
class AppIcon(
    private val iconName: String,
    private val provider: @Composable () -> Painter,
) {

    val painter @Composable get() = LocalIcons.current.getOrPut(iconName) { provider() }

    @Composable
    operator fun invoke(
        modifier: Modifier = Modifier,
        tint: Color = Color.Unspecified,
    ) {
        Icon(
            painter = painter,
            contentDescription = null,
            modifier = modifier,
            tint = tint,
        )
    }

    companion object {
        @Suppress("NOTHING_TO_INLINE")
        inline operator fun invoke(drawableProperty: KProperty0<Int>) = AppIcon(drawableProperty.name) {
            painterResource(drawableProperty.get())
        }
    }
}

class IconsProvider(
    private val overrides: Map<String, @Composable () -> Painter> = emptyMap(),
) {

    @Composable
    fun getOrPut(iconName: String, builder: @Composable () -> Painter): Painter {
        return overrides[iconName]?.invoke() ?: builder()
    }
}

val LocalIcons = staticCompositionLocalOf {
    IconsProvider()
}

fun cachedIcon(
    builder: @Composable () -> Painter,
) = ReadOnlyProperty<AppIcons, AppIcon> { _, property ->
    AppIcon(property.name) {
        builder()
    }
}

@Composable
fun rememberIcon(drawableProperty: KProperty0<Int>) = remember(drawableProperty) { AppIcon(drawableProperty) }
