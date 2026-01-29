package com.dreifus.navigation.ui.toolbar

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import com.dreifus.template.uikit.style.AppIcon
import com.dreifus.template.uikit.toolbar.ToolbarButton
import com.dreifus.template.uikit.toolbar.ToolbarPosition

open class BackToolbarButton(
    override val position: ToolbarPosition,
    override val icon: AppIcon,
) : ToolbarButton {

    @Composable
    override fun onClick(): () -> Unit {
        val activity: Activity? = LocalActivity.current
        return {
            // Don't trigger nav.pop() directly as behavior may be overridden
            activity?.onBackPressed()
        }
    }
}
