package com.rokt.roktdemo.ui.demo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rokt.roktdemo.MainActivityViewModel
import com.rokt.roktdemo.ui.demo.summary.SummaryPage
import com.rokt.roktdemo.ui.demo.walkthrough.WalkthroughHome

@Composable
fun DemoPage(backPressed: () -> Unit, mainActivityViewModel: MainActivityViewModel, viewModel: DemoViewModel = hiltNavGraphViewModel()) {
    val demoPage = viewModel.getDemoPage()
    val navController = rememberNavController()
    val actions = remember(navController) { DemoActions(navController) }

    NavHost(navController = navController, startDestination = DemoDestinations.DEMO_HOME) {
        composable(DemoDestinations.DEMO_HOME) {
            DemoHome(demoPage, backPressed, actions)
        }

        DestinationType
            .values().forEach { type ->
                composable(DemoDestinations.SUMMARY + type) {
                    SummaryPage(actions.backPressed,
                        { actions.navigateToDemoDestination(type) }, type, mainActivityViewModel)
                }
            }

        composable(DemoDestinations.DEMO_DESTINATION + DestinationType.FEATURE_WALKTHROUGH.value) {
            WalkthroughHome(actions.backPressed)
        }
    }
}