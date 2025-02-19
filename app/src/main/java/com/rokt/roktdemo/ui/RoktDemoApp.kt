package com.rokt.roktdemo.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.rokt.roktdemo.MainActivityViewModel
import com.rokt.roktdemo.ui.about.AboutPage
import com.rokt.roktdemo.ui.demo.DemoPage
import com.rokt.roktdemo.ui.home.HomePage
import com.rokt.roktdemo.ui.layouts.DemoLayoutsPage
import com.rokt.roktdemo.ui.settings.SettingsPage
import com.rokt.roktdemo.ui.theme.RoktColors.LightColors

@Composable
fun RoktDemoApp(viewModel: MainActivityViewModel) {
    // TODO: Dark theme
    MaterialTheme(colors = LightColors) {
        ProvideWindowInsets {
            val navController = rememberNavController()
            val actions = remember(navController) { MainActions(navController) }
            NavHost(navController = navController, startDestination = MainDestinations.HOME) {
                composable(MainDestinations.HOME) {
                    HomePage(actions)
                }

                composable(MainDestinations.DEMO) {
                    DemoPage(actions.backPressed, viewModel)
                }

                composable(MainDestinations.LAYOUTS) {
                    DemoLayoutsPage(actions.backPressed, viewModel)
                }

                composable(MainDestinations.ABOUT_ROKT) {
                    AboutPage(actions.backPressed)
                }

                composable(MainDestinations.SETTINGS) {
                    SettingsPage(actions.backPressed)
                }
            }
            viewModel.previewParameterString.value?.let {
                actions.demoLayoutsClicked()
            }
        }
    }
}
