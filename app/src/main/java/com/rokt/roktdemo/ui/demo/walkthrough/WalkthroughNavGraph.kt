package com.rokt.roktdemo.ui.demo.walkthrough

import androidx.navigation.NavController
import com.rokt.roktdemo.ui.demo.walkthrough.WalkthroughDestination.WALKTHROUGH_SCREEN

/**
 * Destinations in the Walkthrough page.
 */
object WalkthroughDestination {
    const val WALKTHROUGH_SCREEN = "WalkthroughScreen"
}

/**
 * Models the navigation actions in the Walkthrough Page.
 */
class WalkthroughActions constructor(
    navController: NavController,
    pageCount: Int,
    exitWalkthrough: () -> Unit,
) {
    val forwardPressed: (index: Int) -> Unit = {
        if (it < pageCount - 1) {
            navController.navigate(WALKTHROUGH_SCREEN + (it + 1))
        } else {
            exitWalkthrough.invoke()
        }
    }

    val backPressed: (index: Int) -> Unit = {
        if (it == 0) {
            exitWalkthrough.invoke()
        } else {
            navController.navigateUp()
        }
    }
}
