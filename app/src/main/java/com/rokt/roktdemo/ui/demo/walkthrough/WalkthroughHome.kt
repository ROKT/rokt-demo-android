package com.rokt.roktdemo.ui.demo.walkthrough

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.statusBarsPadding
import com.rokt.roktdemo.ui.common.BackButton
import com.rokt.roktdemo.ui.demo.walkthrough.screen.WalkthroughScreen
import com.rokt.roktdemo.ui.theme.RoktColors
import com.rokt.roktdemo.ui.theme.RoktFonts

@Composable
fun WalkthroughHome(
    onBackPressed: () -> Unit,
    viewModel: WalkthroughViewModel = hiltNavGraphViewModel()
) {
    val state by viewModel.state.collectAsState()
    val navController = rememberNavController()
    val actions = remember(navController) {
        WalkthroughActions(navController,
            state.screenCount,
            onBackPressed)
    }

    Column(Modifier
        .fillMaxSize()
        .background(color = MaterialTheme.colors.surface)
    ) {
        WalkthroughHeader(
            state.currentIndex,
            counterText = state.screenCounterText,
            navButtonText = state.navButtonText,
            onBackPressed = {
                actions.backPressed(it)
                viewModel.backButtonPressed()
            },
            onNextPressed = {
                actions.forwardPressed(it)
                viewModel.nextButtonPressed()
            })

        NavHost(navController = navController,
            startDestination = WalkthroughDestination.WalkthroughScreen + 0) {
            for (index in 0 until state.screenCount) {
                composable(WalkthroughDestination.WalkthroughScreen + index) {
                    WalkthroughScreen(index)
                }
            }
        }
    }
}

@Composable
fun WalkthroughHeader(
    currentIndex: Int,
    counterText: String,
    navButtonText: String,
    onBackPressed: (index: Int) -> Unit,
    onNextPressed: (index: Int) -> Unit,
) {
    Box(Modifier
        .fillMaxWidth()
        .height(99.dp)
        .background(RoktColors.LightColors.primaryVariant)) {

        Row(modifier = Modifier
            .statusBarsPadding()
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(end = 16.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier
                .weight(1f)
                .fillMaxWidth()) {
                BackButton(backPressed = { onBackPressed(currentIndex) })
            }
            Box(Modifier
                .weight(1f)
                .fillMaxWidth(),
                contentAlignment = Alignment.Center) {
                Text(
                    text = counterText,
                    color = Color.White,
                    fontFamily = RoktFonts.DefaultFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
            Box(Modifier
                .weight(1f)
                .fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd) {
                ClickableText(AnnotatedString(navButtonText),
                    onClick = { onNextPressed(currentIndex) },
                    style = TextStyle(color = Color.White,
                        fontFamily = RoktFonts.DefaultFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp))
            }
        }
    }
}