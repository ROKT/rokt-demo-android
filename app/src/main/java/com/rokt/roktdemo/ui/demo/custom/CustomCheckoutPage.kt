package com.rokt.roktdemo.ui.demo.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rokt.roktdemo.MainActivityViewModel
import com.rokt.roktdemo.model.DemoLibrary
import com.rokt.roktdemo.ui.common.BackButton
import com.rokt.roktdemo.ui.common.HeaderTextButton
import com.rokt.roktdemo.ui.common.RoktHeader
import com.rokt.roktdemo.ui.demo.custom.screen.account.AccountDetailsScreen
import com.rokt.roktdemo.ui.demo.custom.screen.confirmation.CustomCheckoutConfirmationScreen
import com.rokt.roktdemo.ui.demo.custom.screen.customer.CustomerDetailsScreen

@Composable
fun CustomCheckoutPage(exitCheckoutPage: () -> Unit, demoLibrary: DemoLibrary, mainActivityViewModel: MainActivityViewModel) {
    val navController = rememberNavController()
    val actions = CustomCheckoutActions(navController, exitCheckoutPage)
    val customCheckoutViewModel: CustomCheckoutViewModel = hiltViewModel()
    Column(
        Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.surface)
    ) {
        RoktHeader {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BackButton(backPressed = actions.backPressed)
                HeaderTextButton("EXIT", { exitCheckoutPage() })
            }
        }

        NavHost(
            navController = navController,
            startDestination = CustomCheckoutDestinations.AccountDetails
        ) {

            composable(CustomCheckoutDestinations.AccountDetails) {
                AccountDetailsScreen(
                    mainActivityViewModel,
                    customCheckoutViewModel,
                    demoLibrary,
                    actions.navigateToCustomerDetails
                )
            }
            composable(CustomCheckoutDestinations.CustomerDetails) {
                CustomerDetailsScreen(
                    customCheckoutViewModel,
                    demoLibrary,
                    actions.navigateToConfirmationScreen
                )
            }
            composable(CustomCheckoutDestinations.ConfirmationScreen) {
                CustomCheckoutConfirmationScreen(customCheckoutViewModel)
            }
        }
    }
}
