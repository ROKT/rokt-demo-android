package com.rokt.roktdemo.ui

import androidx.navigation.NavController

/**
 * Destinations used in RoktDemo app.
 */
object MainDestinations {
    const val HOME = "home"
    const val DEMO = "demo"
    const val LAYOUTS = "layouts"
    const val ABOUT_ROKT = "about"
    const val SETTINGS = "settings"
}

/**
 * Models the navigation actions in the app.
 */

class MainActions constructor(navController: NavController) {
    val demoLibraryClicked: () -> Unit = {
        navController.navigate(MainDestinations.DEMO)
    }

    val demoLayoutsClicked: () -> Unit = {
        navController.navigate(MainDestinations.LAYOUTS)
    }

    val aboutRoktClicked: () -> Unit = {
        navController.navigate(MainDestinations.ABOUT_ROKT)
    }

    val settingsClicked: () -> Unit = {
        navController.navigate(MainDestinations.SETTINGS)
    }

    val backPressed: () -> Unit = {
        navController.navigateUp()
    }
}
