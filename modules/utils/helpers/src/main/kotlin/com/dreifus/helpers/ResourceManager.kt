package com.dreifus.helpers

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.core.content.ContextCompat
import com.dreifus.template.di.common.ApplicationContext
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn

@SingleIn(AppScope::class)
class ResourceManager @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    fun getDimension(id: Int) =
        context.resources.getDimension(id)

    fun getDrawable(drawableId: Int): Drawable? =
        ContextCompat.getDrawable(context, drawableId)

    fun getColor(color: Int): Int =
        ContextCompat.getColor(context, color)

    fun getString(stringId: Int): String =
        context.resources.getString(stringId)

    fun getString(
        stringId: Int,
        vararg formatArgs: Any?,
    ): String = context.resources.getString(stringId, *formatArgs)

    fun getStringArray(stringId: Int): Array<String> =
        context.resources.getStringArray(stringId)

    fun getQuantityString(
        stringId: Int,
        count: Int,
        vararg args: Any,
    ): String = context.resources.getQuantityString(stringId, count, *args)

    fun getCurrentLocaleCountryCode(): String =
        context.resources.configuration
            .let { if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) it.locales[0] else it.locale }
            ?.country ?: "US"

    fun getIdentifier(code: String, type: String?) =
        context.resources.getIdentifier(code, type, context.packageName).takeIf { it != -0 }
}
