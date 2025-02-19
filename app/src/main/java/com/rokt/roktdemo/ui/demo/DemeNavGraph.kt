package com.rokt.roktdemo.ui.demo

import androidx.navigation.NavController
import com.rokt.roktdemo.ui.demo.DemoDestinations.DEMO_DESTINATION
import com.rokt.roktdemo.ui.demo.DemoDestinations.SUMMARY

/**
 * Destinations used the Demo page.
 */
object DemoDestinations {
    const val DEMO_HOME = "library"
    const val SUMMARY = "summary"
    const val DEMO_DESTINATION = "demo_destination"
}

/**
 * Models the navigation actions in the Demo Page.
 */

class DemoActions constructor(navController: NavController) {
    val navigateToDemoDestination: (destination: DestinationType) -> Unit = {
        navController.navigate("$DEMO_DESTINATION${it.value}")
    }

    val navigateToSummary: (destinationType: DestinationType) -> Unit = {
        navController.navigate(SUMMARY + it)
    }
    val backPressed: () -> Unit = {
        navController.navigateUp()
    }
}
