package com.dreifus.template.uikit.icon

import androidx.compose.ui.res.painterResource
import com.dreifus.template.uikit.R
import com.dreifus.template.uikit.style.AppIcons
import com.dreifus.template.uikit.style.AppTheme
import com.dreifus.template.uikit.style.cachedIcon
import com.dreifus.template.uikit.utils.wrapWith

//region:24px

val AppIcons.ArrowLeft24 by cachedIcon {
    painterResource(R.drawable.ic_shevron_left_24).wrapWith(tint = AppTheme.colors.contentPrimary)
}

val AppIcons.Close24 by cachedIcon {
    painterResource(R.drawable.ic_close).wrapWith(tint = AppTheme.colors.contentPrimary)
}
//endregion
