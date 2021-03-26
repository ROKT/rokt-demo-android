package com.rokt.roktdemo.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.rokt.roktdemo.ui.home.Home
import com.rokt.roktdemo.ui.theme.LightColors

@Composable
fun RoktDemoApp() {
    val context = LocalContext.current
    // TODO: Dark theme
    MaterialTheme(colors = LightColors) {
        Home()
    }
}
