package com.dreifus.navigation.screen

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.navigation3.runtime.NavEntry
import dev.zacsweers.metro.MapKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.serialization.Serializable
import kotlin.reflect.KClass

@Stable
interface BaseScreen : Parcelable {

    @Composable
    fun Content()

    fun getScreenTrackInfo(): ScreenTrackInfo? = null

    fun <T : BaseScreen> navEntry(
        metadata: Map<String, Any> = emptyMap(),
        content: @Composable (T) -> Unit,
    ) = NavEntry(
        key = this as T,
        contentKey = this,
        metadata = metadata,
        content = content,
    )

    fun <T : BaseScreen> navEntry(): NavEntry<T>
}

interface IScreenWithDynamicParams {
    val dynamicParamsMap: MutableMap<String, Any?>
}

/**
 * For exchanging data between screens via navigation stack.
 */
abstract class ScreenWithDynamicParams : Parcelable, IScreenWithDynamicParams {
    @IgnoredOnParcel
    override val dynamicParamsMap: MutableMap<String, Any?> = mutableMapOf()
}

abstract class ScreenWithResult : ScreenWithDynamicParams() {
    @Suppress("UNCHECKED_CAST")
    inline fun <reified T> resultState(): MutableState<T?> =
        dynamicParamsMap.getOrPut("result_" + T::class.java.name) {
            mutableStateOf(null)
        } as MutableState<T?>
}

@Serializable
data class ScreenTrackInfo(
    val name: String? = null, // default - class name without suffix "Screen"
    val valueTeam: String? = null,
    val others: Map<String, String> = emptyMap(),
)

/**
 * For overriding default screen info description for analytics.
 */
annotation class ScreenInfo(
    val name: String = "", // default - class name without suffix "Screen"
    val valueTeam: String = "", // default - value from gradle plugin trackingInfo { valueTeam = ... }
)

typealias ScreensMap = Map<KClass<out BaseScreen>, ScreenInfo>

@MapKey
annotation class ScreenClassKey(
    val value: KClass<out BaseScreen>,
)
