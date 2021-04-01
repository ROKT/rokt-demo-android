package com.rokt.roktdemo.ui

import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import dagger.Module
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Destinations used in RoktDemo app.
 */
object MainDestinations {
    const val HOME = "home"
    const val DEMO = "demo"
    const val ABOUT_ROKT = "about"
    const val CONTACT = "contact"
}

/**
 * Models the navigation actions in the app.
 */

class MainActions constructor(navController: NavController) {
    val demoLibraryClicked: () -> Unit = {
    }

    val aboutRoktClicked: () -> Unit = {
        navController.navigate(MainDestinations.ABOUT_ROKT)
    }

    val backPressed: () -> Unit = {
        navController.navigateUp()
    }
}