package com.dreifus.navigation.controller

import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import com.dreifus.navigation.screen.BaseScreen

@Stable
class NavController<T : BaseScreen>(
    vararg screens: T,
    /** Prevents navigation to a screen with the same class as the current one. Relevant for overlays. */
    private val filterNavigationToSameClass: Boolean = false,
) {
    val backstack = mutableStateListOf(*screens)

    fun navigate(screen: T) {
        if (!filterNavigationToSameClass || backstack.lastOrNull()
                ?.let { it::class } != screen::class
        ) {
            backstack.add(screen)
        } else {
            println("NavController: Attempted to navigate to the same screen class as the current one: ${screen::class}")
        }
    }

    fun replaceLast(screen: T): Boolean {
        if (backstack.isNotEmpty()) {
            backstack[backstack.size - 1] = screen
            return true
        }
        return false
    }

    fun replaceAll(screens: List<T>) {
        backstack.clear()
        backstack.addAll(screens)
    }

    fun replaceAll(screen: T) {
        replaceAll(listOf(screen))
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
     * Pop up to the first screen that matches the [predicate].
     * If [inclusive] is true, the matching screen will also be popped.
     * If [toFirst] is true, the backstack will be cleared to the first screen in the backstack.
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
     * Pops all screens off the backstack up to the screen that meets the condition
     * of the [predicate] and replaces them with [newScreens].
     *
     * The order of the items in the list is interpreted as going from the bottom of the backstack
     * to the top. It means that the last item of the list will become the currently displayed item.
     *
     * The [newScreens] list may be empty.
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
        newScreens: List<T>,
        inclusive: Boolean = false,
        toFirst: Boolean = false,
        predicate: (T) -> Boolean,
    ): Boolean {
        if (popUpTo(inclusive, toFirst, predicate)) {
            backstack.addAll(newScreens)
            return true
        }
        return false
    }

    /**
     * Pops all screens off the backstack up to the screen that meets the condition
     * of the [predicate] and replaces them with a [newScreen].
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
        newScreen: T,
        inclusive: Boolean = false,
        toFirst: Boolean = false,
        predicate: (T) -> Boolean,
    ): Boolean = replaceUpTo(listOf(newScreen), inclusive, toFirst, predicate)
}
