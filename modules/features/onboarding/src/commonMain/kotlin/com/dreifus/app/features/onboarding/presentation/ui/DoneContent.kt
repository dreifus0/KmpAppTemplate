package com.dreifus.app.features.onboarding.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dreifus.template.uikit.style.AppTheme

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
            text = "You're all set",
            style = AppTheme.typography.headlineLarge,
            color = AppTheme.colors.contentPrimary,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Tap below to start exploring the sample.",
            style = AppTheme.typography.bodyLarge,
            color = AppTheme.colors.contentSecondary,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(48.dp))
        Button(onClick = onFinish) {
            Text("Get Started")
        }
    }
}
