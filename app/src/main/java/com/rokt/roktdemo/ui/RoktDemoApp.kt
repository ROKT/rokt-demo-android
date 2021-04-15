package com.rokt.roktdemo.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.rokt.roktdemo.ui.about.AboutPage
import com.rokt.roktdemo.ui.demo.DemoPage
import com.rokt.roktdemo.ui.home.HomePage
import com.rokt.roktdemo.ui.theme.RoktColors.LightColors

@Composable
fun RoktDemoApp() {
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
                    DemoPage(actions.backPressed)
                }

                composable(MainDestinations.ABOUT_ROKT) {
                    AboutPage(actions.backPressed)
                }
            }
        }
    }
}
