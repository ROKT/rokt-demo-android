package com.rokt.roktdemo.ui.demo.walkthrough

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rokt.roktdemo.model.DemoLibrary
import com.rokt.roktdemo.ui.common.BackButton
import com.rokt.roktdemo.ui.common.HeaderTextButton
import com.rokt.roktdemo.ui.common.RoktHeader
import com.rokt.roktdemo.ui.demo.walkthrough.screen.WalkthroughScreen

@Composable
fun WalkthroughPage(
    onBackPressed: () -> Unit,
    demoLibrary: DemoLibrary,
    viewModel: WalkthroughViewModel = hiltViewModel(),
) {
    viewModel.init(demoLibrary.defaultPlacementsExamples.screens.count())
    val state by viewModel.state.collectAsState()
    val navController = rememberNavController()
    val actions = remember(navController) {
        WalkthroughActions(
            navController,
            state.screenCount,
            onBackPressed
        )
    }

    WalkthroughPageContent(
        screenCount = state.screenCount,
        currentIndex = state.currentIndex,
        screenCounterText = state.screenCounterText,
        navButtonText = state.navButtonText,
        backPressed = {
            viewModel.backButtonPressed()
            actions.backPressed.invoke(it)
        },
        onNextPressed = {
            viewModel.nextButtonPressed()
            actions.forwardPressed.invoke(it)
        },
        navController = navController,
        demoLibrary
    )
}

@Composable
private fun WalkthroughPageContent(
    screenCount: Int,
    currentIndex: Int,
    screenCounterText: String,
    navButtonText: String,
    backPressed: (Int) -> Unit,
    onNextPressed: (index: Int) -> Unit,
    navController: NavHostController,
    demoLibrary: DemoLibrary,
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.surface)
    ) {
        WalkthroughHeader(
            currentIndex,
            counterText = screenCounterText,
            navButtonText = navButtonText,
            onBackPressed = {
                backPressed(it)
            },
            onNextPressed = {
                onNextPressed(it)
            }
        )
        NavHost(
            navController = navController,
            startDestination = WalkthroughDestination.WalkthroughScreen + 0
        ) {
            for (index in 0 until screenCount) {
                composable(WalkthroughDestination.WalkthroughScreen + index) {
                    WalkthroughScreen(index, demoLibrary)
                }
            }
        }
    }
}

@Composable
private fun WalkthroughHeader(
    currentIndex: Int,
    counterText: String,
    navButtonText: String,
    onBackPressed: (index: Int) -> Unit,
    onNextPressed: (index: Int) -> Unit,
) {
    RoktHeader {
        HeaderContent(
            currentIndex = currentIndex,
            counterText = counterText,
            navButtonText = navButtonText,
            onBackPressed = onBackPressed,
            onNextPressed = onNextPressed
        )
    }
}

@Composable
private fun RowScope.HeaderContent(
    currentIndex: Int,
    counterText: String,
    navButtonText: String,
    onBackPressed: (index: Int) -> Unit,
    onNextPressed: (index: Int) -> Unit,
) {

    Box(
        Modifier
            .weight(1f)
            .fillMaxWidth()
    ) {
        BackButton(backPressed = { onBackPressed(currentIndex) })
    }
    Box(
        Modifier
            .weight(1f)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = counterText,
            color = androidx.compose.ui.graphics.Color.White,
            fontFamily = com.rokt.roktdemo.ui.theme.RoktFonts.DefaultFontFamily,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
            fontSize = 16.sp
        )
    }
    Box(
        Modifier
            .weight(1f)
            .fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
    ) {
        HeaderTextButton(navButtonText, { onNextPressed(currentIndex) })
    }
}
