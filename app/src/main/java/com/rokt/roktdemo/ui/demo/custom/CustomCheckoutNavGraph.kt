package com.rokt.roktdemo.ui.demo.custom

import androidx.navigation.NavController
import androidx.navigation.compose.navigate

/**
 * Destinations in the Custom Checkout page.
 */
object CustomCheckoutDestinations {
    const val AccountDetails = "AccountDetails"
    const val CustomerDetails = "CustomerDetails"
    const val ConfirmationScreen = "ConfirmationScreen"
}

/**
 * Models the navigation actions in the Custom Checkout Page.
 */
class CustomCheckoutActions(
    navController: NavController,
    exitCheckoutPage: () -> Unit,
) {

    val navigateToCustomerDetails: () -> Unit = {
        navController.navigate(CustomCheckoutDestinations.CustomerDetails)
    }

    val navigateToConfirmationScreen: () -> Unit = {
        navController.navigate(CustomCheckoutDestinations.ConfirmationScreen)
    }

    val backPressed: () -> Unit = {
        if (navController.navigateUp().not()) {
            exitCheckoutPage.invoke()
        }
    }
}
