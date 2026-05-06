package com.dreifus.app

import androidx.compose.ui.window.ComposeUIViewController
import com.dreifus.app.di.PlatformDependencies

fun MainViewController() = ComposeUIViewController { App(PlatformDependencies()) }
