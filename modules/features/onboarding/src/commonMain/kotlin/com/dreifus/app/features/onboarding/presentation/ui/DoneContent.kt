package com.dreifus.app.features.onboarding.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dreifus.template.uikit.button.AppButton
import com.dreifus.template.uikit.preview.AppPreview
import com.dreifus.template.uikit.style.AppTheme
import kmptemplateapp.modules.features.onboarding.generated.resources.Res
import kmptemplateapp.modules.features.onboarding.generated.resources.onboarding_done_description
import kmptemplateapp.modules.features.onboarding.generated.resources.onboarding_done_get_started
import kmptemplateapp.modules.features.onboarding.generated.resources.onboarding_done_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun DoneContent(onFinish: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(Res.string.onboarding_done_title),
            style = AppTheme.typography.headlineLarge,
            color = AppTheme.colors.contentPrimary,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(Res.string.onboarding_done_description),
            style = AppTheme.typography.bodyLarge,
            color = AppTheme.colors.contentSecondary,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(48.dp))
        AppButton(text = stringResource(Res.string.onboarding_done_get_started), onClick = onFinish)
    }
}

@Preview
@Composable
private fun DoneContentPreview() {
    AppPreview {
        DoneContent(onFinish = {})
    }
}
