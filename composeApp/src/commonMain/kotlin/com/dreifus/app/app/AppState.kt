package com.dreifus.app.app

data class AppState(
    val screen: Screen = Screen.Loading,
) {
    enum class Screen { Loading, Onboarding, Root }
}
