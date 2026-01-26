@file:Suppress("MatchingDeclarationName")

package com.dreifus.template.uikit.debug

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.remember
import com.dreifus.template.uikit.BuildConfig

class RecompositionCounter(var value: Int)

@Suppress("NOTHING_TO_INLINE")
@Composable
inline fun LogCompositions(tag: String, msg: String) {
    if (BuildConfig.DEBUG) {
        val recompositionCounter = remember { RecompositionCounter(0) }

        val scope = currentRecomposeScope.hashCode()
        Log.d(tag, "$msg ${recompositionCounter.value}, scope hash: $scope")
        recompositionCounter.value++
    }
}
