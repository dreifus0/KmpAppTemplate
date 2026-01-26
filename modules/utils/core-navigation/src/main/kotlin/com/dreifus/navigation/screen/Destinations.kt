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
interface BaseDestination : Parcelable {

    @Composable
    fun Content()

    fun getScreenTrackInfo(): DestinationTrackInfo? = null

    fun <T : BaseDestination> navEntry(
        metadata: Map<String, Any> = emptyMap(),
        content: @Composable (T) -> Unit,
    ) = NavEntry(
        key = this as T,
        contentKey = this,
        metadata = metadata,
        content = content,
    )

    fun <T : BaseDestination> navEntry(): NavEntry<T>
}

interface IDestinationWithDynamicParams {
    val dynamicParamsMap: MutableMap<String, Any?>
}

/**
 * Для обмена данными между экранами через стек навигации.
 */
abstract class DestinationWithDynamicParams : Parcelable, IDestinationWithDynamicParams {
    @IgnoredOnParcel
    override val dynamicParamsMap: MutableMap<String, Any?> = mutableMapOf()
}

abstract class DestinationWithResult : DestinationWithDynamicParams() {
    @Suppress("UNCHECKED_CAST")
    inline fun <reified T> resultState(): MutableState<T?> =
        dynamicParamsMap.getOrPut("result_" + T::class.java.name) {
            mutableStateOf(null)
        } as MutableState<T?>
}

@Serializable
data class DestinationTrackInfo(
    val name: String? = null, // default - class name without suffix "Destination"
    val valueTeam: String? = null,
    val others: Map<String, String> = emptyMap(),
)

/**
 * Для переопределения дефолтного описания информации о экране для аналитики.
 */
annotation class DestinationInfo(
    val name: String = "", // default - class name without suffix "Destination"
    val valueTeam: String = "", // default - value from gradle plugin trackingInfo { valueTeam = ... }
)

typealias DestinationsMap = Map<KClass<out BaseDestination>, DestinationInfo>

@MapKey
annotation class DestinationClassKey(
    val value: KClass<out BaseDestination>,
)
