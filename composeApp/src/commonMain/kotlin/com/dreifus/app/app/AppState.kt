package com.dreifus.app.app

data class AppState(
    val screen: Screen = Screen.Root,
) {
    enum class Screen { Root }
}
