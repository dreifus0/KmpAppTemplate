package com.dreifus.navigation.screen.regular

import androidx.activity.compose.BackHandler
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import com.dreifus.navigation.IInsetsConsumer
import com.dreifus.navigation.controller.Navigation
import com.dreifus.navigation.imePaddingIfNeeded
import com.dreifus.navigation.navigationBarsPaddingIfNeeded
import com.dreifus.navigation.screen.BaseDestination
import com.dreifus.navigation.statusBarsPaddingIfNeeded

interface RegularScreen : BaseDestination {
    override fun <T : BaseDestination> navEntry() = navEntry<T> { destination ->
        val navController = Navigation.regular
        BackHandler(enabled = navController.backstack.size > 1) {
            navController.pop()
        }
        Surface(
            modifier = Modifier
                .semantics { testTagsAsResourceId = true }
                .testTag(destination::class.simpleName!!)
                .statusBarsPaddingIfNeeded(destination as? IInsetsConsumer)
                .imePaddingIfNeeded(destination as? IInsetsConsumer)
                .navigationBarsPaddingIfNeeded(destination as? IInsetsConsumer)
        ) {
            destination.Content()
        }
    }
}
