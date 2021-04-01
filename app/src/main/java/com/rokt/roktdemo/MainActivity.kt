package com.rokt.roktdemo

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.rokt.roktdemo.ui.RoktDemoApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : androidx.activity.ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // Draw behind status bar
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent { RoktDemoApp() }
    }
}
