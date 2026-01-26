package com.dreifus.navigation

import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.ui.Modifier

interface IInsetsConsumer {
    val statusBarConsumed: Boolean get() = false
    val imeConsumed: Boolean get() = false
    val navigationBarsConsumed: Boolean get() = false
}

internal fun Modifier.statusBarsPaddingIfNeeded(destination: IInsetsConsumer?): Modifier {
    return this.then(
        Modifier.run {
            if (destination?.statusBarConsumed != true) {
                this.statusBarsPadding()
            } else {
                this
            }
        }
    )
}

internal fun Modifier.imePaddingIfNeeded(destination: IInsetsConsumer?): Modifier {
    return this.then(
        Modifier.run {
            if (destination?.imeConsumed != true) {
                this.imePadding()
            } else {
                this
            }
        }
    )
}

internal fun Modifier.navigationBarsPaddingIfNeeded(destination: IInsetsConsumer?): Modifier {
    return this.then(
        Modifier.run {
            if (destination?.navigationBarsConsumed != true) {
                this.navigationBarsPadding()
            } else {
                this
            }
        }
    )
}
