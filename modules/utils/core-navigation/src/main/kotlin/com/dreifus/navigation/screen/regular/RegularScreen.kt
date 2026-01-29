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
import com.dreifus.navigation.screen.BaseScreen
import com.dreifus.navigation.statusBarsPaddingIfNeeded

interface RegularScreen : BaseScreen {
    override fun <T : BaseScreen> navEntry() = navEntry<T> { screen ->
        val navController = Navigation.regular
        BackHandler(enabled = navController.backstack.size > 1) {
            navController.pop()
        }
        Surface(
            modifier = Modifier
                .semantics { testTagsAsResourceId = true }
                .testTag(screen::class.simpleName!!)
                .statusBarsPaddingIfNeeded(screen as? IInsetsConsumer)
                .imePaddingIfNeeded(screen as? IInsetsConsumer)
                .navigationBarsPaddingIfNeeded(screen as? IInsetsConsumer)
        ) {
            screen.Content()
        }
    }
}
