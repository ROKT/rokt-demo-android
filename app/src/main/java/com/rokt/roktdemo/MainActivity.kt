package com.rokt.roktdemo

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import com.rokt.roktdemo.ui.RoktDemoApp
import com.rokt.roktsdk.Rokt
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : androidx.activity.ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        viewModel.selectedTagId.observe(this) {
            Rokt.init(it, BuildConfig.VERSION_NAME, this@MainActivity)
        }

        // Draw behind status bar
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent { RoktDemoApp(viewModel) }
    }
}
