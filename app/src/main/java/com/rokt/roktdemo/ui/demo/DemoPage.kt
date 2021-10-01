package com.rokt.roktdemo.ui.demo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rokt.roktdemo.MainActivityViewModel
import com.rokt.roktdemo.ui.common.LoadingPage
import com.rokt.roktdemo.ui.demo.custom.CustomCheckoutPage
import com.rokt.roktdemo.ui.demo.error.RoktError
import com.rokt.roktdemo.ui.demo.predefined.predefined1.PreDefined1Page
import com.rokt.roktdemo.ui.demo.summary.SummaryPage
import com.rokt.roktdemo.ui.demo.walkthrough.WalkthroughPage

@Composable
fun DemoPage(
    backPressed: () -> Unit,
    mainActivityViewModel: MainActivityViewModel,
    viewModel: DemoViewModel = hiltViewModel(),
) {
    val demoPage = viewModel.state.collectAsState().value
    val navController = rememberNavController()
    val actions = remember(navController) { DemoActions(navController) }

    when {
        demoPage.loading -> {
            LoadingPage()
        }
        demoPage.hasData -> {
            DemoPageContent(
                navController,
                demoPage.data!!,
                actions,
                backPressed,
                mainActivityViewModel
            )
        }
        else -> {
            RoktError(errorType = demoPage.error)
        }
    }
}

@Composable
fun DemoPageContent(
    navController: NavHostController,
    demoScreenState: DemoScreenState,
    actions: DemoActions,
    backPressed: () -> Unit,
    mainActivityViewModel: MainActivityViewModel,
) {
    NavHost(navController = navController, startDestination = DemoDestinations.DEMO_HOME) {
        composable(DemoDestinations.DEMO_HOME) {
            DemoHome(demoScreenState, backPressed, actions)
        }

        demoScreenState.items.forEach { item ->
            composable(DemoDestinations.SUMMARY + item.navAction) {
                SummaryPage(
                    actions.backPressed,
                    { actions.navigateToDemoDestination(item.navAction) },
                    mainActivityViewModel,
                    item.summaryViewModel
                )
            }
        }

        composable(DemoDestinations.DEMO_DESTINATION + DestinationType.FEATURE_WALKTHROUGH.value) {
            WalkthroughPage(actions.backPressed, demoScreenState.library)
        }

        composable(DemoDestinations.DEMO_DESTINATION + DestinationType.CUSTOM_CHECKOUT.value) {
            CustomCheckoutPage(actions.backPressed, demoScreenState.library, mainActivityViewModel)
        }

        composable(DemoDestinations.DEMO_DESTINATION + DestinationType.CONFIRMATION_PREDEFINED1.value) {
            PreDefined1Page(actions.backPressed, demoScreenState.library.preDefinedScreen1)
        }
    }
}
