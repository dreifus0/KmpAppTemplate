package com.dreifus.app.ui.mvu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dreifus.navigation.screen.regular.RegularScreen
import kotlinx.parcelize.Parcelize

@Parcelize
class MainScreen : RegularScreen {

    @Composable
    override fun Content() {
        Box(
            modifier = Modifier.Companion.fillMaxSize(),
            contentAlignment = Alignment.Companion.Center
        ) {
            Text("Template app")
        }
    }
}
