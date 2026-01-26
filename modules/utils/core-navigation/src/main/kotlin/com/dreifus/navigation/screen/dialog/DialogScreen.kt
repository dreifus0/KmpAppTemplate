package com.dreifus.navigation.screen.dialog

import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import androidx.navigation3.scene.DialogSceneStrategy
import com.dreifus.navigation.IInsetsConsumer
import com.dreifus.navigation.imePaddingIfNeeded
import com.dreifus.navigation.navigationBarsPaddingIfNeeded
import com.dreifus.navigation.screen.BaseDestination
import com.dreifus.navigation.statusBarsPaddingIfNeeded
import com.dreifus.template.uikit.style.AppTheme

interface DialogScreen : BaseDestination {
    override fun <T : BaseDestination> navEntry() = navEntry<T>(
        metadata = DialogSceneStrategy.dialog(
            DialogProperties(
                usePlatformDefaultWidth = true,
                decorFitsSystemWindows = false
            )
        ),
    ) { destination ->
        // Далее вся логика с атрибутами окон нужна для того, чтобы статус и нав бары тоже затенялись
        // https://www.droidcon.com/2024/01/15/camouflage-the-status-bar-with-edge-to-edge-jetpack-compose-screens-and-dialogs/
        val activityWindow = LocalActivity.current!!.window
        val dialogWindow = (LocalView.current.parent as? DialogWindowProvider)?.window
        val parentView = LocalView.current.parent as View
        SideEffect {
            if (activityWindow != null && dialogWindow != null) {
                // Step 2: Get and apply the activity attributes to the dialog parent view
                val attributes = WindowManager.LayoutParams()
                attributes.copyFrom(activityWindow.attributes)
                attributes.type = dialogWindow.attributes.type
                dialogWindow.attributes = attributes
                parentView.layoutParams = FrameLayout.LayoutParams(
                    activityWindow.decorView.width,
                    activityWindow.decorView.height
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colors.contentPrimary.copy(alpha = 0.3f))
                .semantics { testTagsAsResourceId = true }
                .testTag(destination::class.simpleName!!)
                .statusBarsPaddingIfNeeded(destination as? IInsetsConsumer)
                .imePaddingIfNeeded(destination as? IInsetsConsumer)
                .navigationBarsPaddingIfNeeded(destination as? IInsetsConsumer),
            contentAlignment = Alignment.Companion.Center,
        ) {
            destination.Content()
        }
    }
}
