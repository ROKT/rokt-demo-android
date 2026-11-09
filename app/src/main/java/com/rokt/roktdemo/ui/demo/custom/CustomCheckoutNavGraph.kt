package com.rokt.roktdemo.ui.demo.custom

import androidx.navigation.NavController

/**
 * Destinations in the Custom Checkout page.
 */
object CustomCheckoutDestinations {
    const val ACCOUNT_DETAILS = "AccountDetails"
    const val CUSTOMER_DETAILS = "CustomerDetails"
    const val CONFIRMATION_SCREEN = "ConfirmationScreen"
}

/**
 * Models the navigation actions in the Custom Checkout Page.
 */
class CustomCheckoutActions(
    navController: NavController,
    exitCheckoutPage: () -> Unit,
) {
    val navigateToCustomerDetails: () -> Unit = {
        navController.navigate(CustomCheckoutDestinations.CUSTOMER_DETAILS)
    }

    val navigateToConfirmationScreen: () -> Unit = {
        navController.navigate(CustomCheckoutDestinations.CONFIRMATION_SCREEN)
    }

    val backPressed: () -> Unit = {
        if (navController.navigateUp().not()) {
            exitCheckoutPage.invoke()
        }
    }
}
