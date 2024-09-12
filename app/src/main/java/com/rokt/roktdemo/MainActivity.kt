package com.rokt.roktdemo

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.rokt.roktdemo.ui.RoktDemoApp
import com.rokt.roktsdk.BuildConfig
import com.rokt.roktsdk.Rokt
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

private const val PREVIEW_PARAM_EXTRA = "preview"
private const val URI_QUERY_PARAM = "config"

@AndroidEntryPoint
class MainActivity : androidx.activity.ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.getStringExtra(PREVIEW_PARAM_EXTRA)?.let(viewModel::updatePreviewParameter)
        intent.data?.getQueryParameter(URI_QUERY_PARAM)?.let(viewModel::updatePreviewParameter)

        viewModel.selectedTagId.observe(this) {
            Timber.d("Calling Rokt.Init with new tag Id: $it")
            Rokt.init(it, BuildConfig.VERSION_NAME, this@MainActivity)
        }

        // Draw behind status bar
        WindowCompat.setDecorFitsSystemWindows(window, false)
        if (savedInstanceState != null) {
            installSplashScreen()
        }
        setTheme(R.style.Theme_RoktDemo)
        setContent { RoktDemoApp(viewModel) }
    }
}
