package com.rokt.roktdemo.ui.layouts

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import com.rokt.roktdemo.MainActivityViewModel
import com.rokt.roktdemo.R
import com.rokt.roktdemo.ui.common.BackButton
import com.rokt.roktdemo.ui.common.ButtonDark
import com.rokt.roktdemo.ui.common.ButtonLight
import com.rokt.roktdemo.ui.common.Heading
import com.rokt.roktdemo.ui.common.LoadingPage
import com.rokt.roktdemo.ui.common.RoktEmbeddedWidget
import com.rokt.roktdemo.ui.common.SmallSpace
import com.rokt.roktdemo.ui.demo.error.RoktError
import com.rokt.roktsdk.Widget
import java.lang.ref.WeakReference

private const val HEADER_TOP_PADDING = 50 // How far from the top the header items sit

private var embeddedWidget: WeakReference<Widget>? = null

@Composable
fun DemoLayoutsPage(
    backPressed: () -> Unit,
    mainActivityViewModel: MainActivityViewModel,
    viewModel: ScanQrViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White, RectangleShape)
    ) {

        when {
            state.value.loading -> {
                LoadingPage()
            }

            state.value.hasData -> {
                ScannerContent(
                    backPressed = backPressed,
                    viewModel = viewModel,
                    state = requireNotNull(state.value.data)
                )
                state.value.data?.scannedData?.tagId?.let {
                    mainActivityViewModel.updateSelectedTagId(it)
                }
            }

            else -> {
                RoktError(errorType = state.value.error)
            }
        }
    }
}

@Composable
private fun ScannerContent(
    backPressed: () -> Unit,
    viewModel: ScanQrViewModel,
    state: ScanQrState,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(Modifier.padding(PaddingValues(start = 3.dp, top = HEADER_TOP_PADDING.dp))) {
            BackButton(backPressed, MaterialTheme.colors.primaryVariant)
        }
        Column(
            modifier = Modifier
                .padding(30.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Heading(text = stringResource(id = R.string.menu_button_layouts_demo))
            ScannerView(viewModel = viewModel)
            if (state.scannedData != null) {
                SmallSpace()
                ButtonDark(text = stringResource(id = R.string.button_execute)) {
                    viewModel.executePreview(embeddedWidget)
                }
                SmallSpace()
                RoktEmbeddedWidget(onWidgetAdded = { embeddedWidget = it })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScannerView(viewModel: ScanQrViewModel) {
    val qrCodeIntentLauncher = rememberLauncherForActivityResult(
        contract = ScanContract(),
        onResult = { success ->
            viewModel.qrCodeScanned(success, embeddedWidget)
        }
    )
    val scanPrompt = stringResource(id = R.string.text_scan_prompt)
    PlainTooltipBox(
        tooltip = {
            Text(
                text = stringResource(id = R.string.text_scan_description),
                color = MaterialTheme.colors.surface
            )
        }
    ) {
        ButtonLight(
            modifier = Modifier.tooltipAnchor(),
            text = stringResource(id = R.string.button_scan)
        ) {
            qrCodeIntentLauncher.launch(
                ScanOptions().apply {
                    setPrompt(scanPrompt)
                    setBeepEnabled(true)
                    setOrientationLocked(true)
                }
            )
        }
    }
}
