package com.dreifus.navigation.controller

import android.util.Log
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import com.dreifus.navigation.screen.BaseDestination

@Stable
class NavController<T : BaseDestination>(
    vararg destinations: T,
    /** Предотвращает переход на экран с тем же классом, что и текущий. Актуально для оверлеев.*/
    private val filterNavigationToSameClass: Boolean = false,
) {
    val backstack = mutableStateListOf(*destinations)

    fun navigate(destination: T) {
        if (!filterNavigationToSameClass || backstack.lastOrNull()
                ?.let { it::class } != destination::class
        ) {
            backstack.add(destination)
        } else {
            Log.e(
                "NavController",
                "Attempted to navigate to the same screen class as the current one: ${destination::class} "
            )
        }
    }

    fun replaceLast(destination: T): Boolean {
        if (backstack.isNotEmpty()) {
            backstack[backstack.size - 1] = destination
            return true
        }
        return false
    }

    fun replaceAll(destinations: List<T>) {
        backstack.clear()
        backstack.addAll(destinations)
    }

    fun replaceAll(destination: T) {
        replaceAll(listOf(destination))
    }

    fun pop(): Boolean {
        if (backstack.isNotEmpty()) {
            backstack.removeLastOrNull()
            return true
        }
        return false
    }

    fun popAll(): Boolean {
        if (backstack.isNotEmpty()) {
            backstack.removeRange(1, backstack.size)
            return true
        }
        return false
    }

    /**
     * Pop up to the first destination that matches the [predicate].
     * If [inclusive] is true, the matching destination will also be popped.
     * If [toFirst] is true, the backstack will be cleared to the first destination in the backstack.
     */
    fun popUpTo(
        inclusive: Boolean = false,
        toFirst: Boolean = false,
        predicate: (T) -> Boolean,
    ): Boolean {
        val index =
            if (toFirst) backstack.indexOfFirst(predicate) else backstack.indexOfLast(predicate)
        if (index != -1) {
            val correctedIndex = if (inclusive) index else index + 1
            backstack.removeRange(correctedIndex, backstack.size)
            return true
        }
        return false
    }

    /**
     * Pops all destinations off the backstack up to the destination that meets the condition
     * of the [predicate] and replaces them with [newDestinations].
     *
     * The order of the items in the list is interpreted as going from the bottom of the backstack
     * to the top. It means that the last item of the list will become the currently displayed item.
     *
     * The [newDestinations] list may be empty.
     *
     * @param [inclusive] whether the item itself should be popped or not, default value is `false`
     *
     * @param [toFirst] specifies the policy of selecting the target item in case of multiple matching
     * items. By default, the last matching item from the start of the backstack will be considered
     * the point up to which to replace.
     *
     * @return `true` - if the item matching the predicate was found, `false` - otherwise
     */
    fun replaceUpTo(
        newDestinations: List<T>,
        inclusive: Boolean = false,
        toFirst: Boolean = false,
        predicate: (T) -> Boolean,
    ): Boolean {
        if (popUpTo(inclusive, toFirst, predicate)) {
            backstack.addAll(newDestinations)
            return true
        }
        return false
    }

    /**
     * Pops all destinations off the backstack up to the destination that meets the condition
     * of the [predicate] and replaces them with a [newDestination].
     *
     * @param [inclusive] whether the item itself should be popped or not, default value is `false`
     *
     * @param [toFirst] specifies the policy of selecting the target item in case of multiple matching
     * items. By default, the last matching item from the start of the backstack will be considered
     * the point up to which to replace.
     *
     * @return `true` - if the item matching the predicate was found, `false` - otherwise
     */
    fun replaceUpTo(
        newDestination: T,
        inclusive: Boolean = false,
        toFirst: Boolean = false,
        predicate: (T) -> Boolean,
    ): Boolean = replaceUpTo(listOf(newDestination), inclusive, toFirst, predicate)
}