package com.rokt.roktdemo.ui.demo

import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.rokt.roktdemo.ui.demo.DemoDestinations.SUMMARY

/**
 * Destinations used the Demo page.
 */
object DemoDestinations {
    const val DEMO_HOME = "library"
    const val SUMMARY = "summary"
}

/**
 * Models the navigation actions in the Demo Page.
 */

class DemoActions constructor(navController: NavController) {
    val navigateToDemoDestination: (destination: String) -> Unit = {
        navController.navigate(it)
    }

    val navigateToSummary: (destinationType: DestinationType) -> Unit = {
        navController.navigate(SUMMARY + it)
    }
    val backPressed: () -> Unit = {
        navController.navigateUp()
    }
}
