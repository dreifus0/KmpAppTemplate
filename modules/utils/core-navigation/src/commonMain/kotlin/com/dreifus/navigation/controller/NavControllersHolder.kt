package com.dreifus.navigation.controller

import com.dreifus.navigation.screen.bottomsheet.BottomSheetScreen
import com.dreifus.navigation.screen.dialog.DialogScreen
import com.dreifus.navigation.screen.regular.RegularScreen

class NavControllersHolder(
    rootScreen: RegularScreen,
) {
    val regular = NavController(rootScreen)
    val dialog = NavController<DialogScreen>(filterNavigationToSameClass = true)
    val bottomSheet = NavController<BottomSheetScreen>(filterNavigationToSameClass = true)
}
