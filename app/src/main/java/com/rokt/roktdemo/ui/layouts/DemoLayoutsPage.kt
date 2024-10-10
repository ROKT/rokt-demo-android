package com.rokt.roktdemo.ui.layouts

import android.widget.Toast
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import com.rokt.roktdemo.MainActivityViewModel
import com.rokt.roktdemo.R
import com.rokt.roktdemo.ui.common.BackButton
import com.rokt.roktdemo.ui.common.ButtonLight
import com.rokt.roktdemo.ui.common.ContentText
import com.rokt.roktdemo.ui.common.Heading
import com.rokt.roktdemo.ui.common.LoadingPage
import com.rokt.roktdemo.ui.common.RoktEmbeddedWidget
import com.rokt.roktdemo.ui.common.SmallSpace
import com.rokt.roktdemo.ui.common.SubHeading
import com.rokt.roktdemo.ui.demo.error.RoktError
import com.rokt.roktsdk.Widget
import com.rokt.roktux.FontMap
import com.rokt.roktux.RoktLayout
import com.rokt.roktux.imagehandler.NetworkStrategy

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

    fun handleQRCodeData(data: String?) {
        viewModel.qrCodeScanned(data)
    }

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
                    data = state.value.data,
                    backPressed = backPressed,
                    onWidgetAdded = { widget ->
                        embeddedWidget = widget
                        mainActivityViewModel.previewParameterString.value.let { handleQRCodeData(it) }
                    },
                    onQrCodeScanned = {
                        handleQRCodeData(it)
                    }
                )

                if (state.value.data?.previewState is PreviewState.ScannedState) {
                    val context = LocalContext.current
                    ShowSdkSelectionPopup { legacySdk ->
                        if (legacySdk) {
                            viewModel.legacySdkSelected(embeddedWidget = embeddedWidget)
                            state.value.data?.scannedData?.tagId?.let {
                                mainActivityViewModel.updateSelectedTagId(it)
                            }
                        } else {
                            viewModel.uxHelperSdkSelected(context)
                        }
                    }
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
    onWidgetAdded: (WeakReference<Widget>) -> Unit,
    onQrCodeScanned: (data: String?) -> Unit,
    data: ScanQrState?,
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
            ScannerView(onQrCodeScanned)
            SmallSpace()
            RoktEmbeddedWidget(
                onWidgetAdded = onWidgetAdded,
            )
            if (data?.previewState is PreviewState.UxHelperSdkData) {
                RoktUxPlaceHolder(data.previewState.response)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScannerView(onQrCodeScanned: (data: String?) -> Unit) {
    TooltipBox(
        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
        tooltip = {
            PlainTooltip {
                Text(
                    text = stringResource(id = R.string.text_scan_description),
                    color = MaterialTheme.colors.surface,
                )
            }
        },
        state = rememberTooltipState(),
    ) {
        val context = LocalContext.current
        ButtonLight(
            text = stringResource(id = R.string.button_scan)
        ) {
            val options = GmsBarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
                .enableAutoZoom()
                .allowManualInput()
                .build()
            val scanner = GmsBarcodeScanning.getClient(context, options)
            scanner.startScan()
                .addOnSuccessListener { barcode ->
                    onQrCodeScanned(barcode.rawValue)
                }
                .addOnCanceledListener {
                    // Do nothing
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }
        }
    }
}

@Composable
private fun ShowSdkSelectionPopup(dismissListener: (legacySDK: Boolean) -> Unit) {
    AlertDialog(
        onDismissRequest = { dismissListener(true) },
        title = { SubHeading(text = stringResource(R.string.text_select_sdk_title)) },
        text = { ContentText(text = stringResource(R.string.text_select_sdk_description)) },
        confirmButton = {
            ButtonLight(text = stringResource(R.string.button_uxhelper), onClick = {
                dismissListener(false)
            })
        },
        dismissButton = {
            ButtonLight(text = stringResource(R.string.button_legacysdk), onClick = {
                dismissListener(true)
            })
        }
    )
}

@Composable
private fun RoktUxPlaceHolder(data: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        RoktLayout(
            experienceResponse = data,
            onUxEvent = { println("UxEvent Received $it") },
            onPlatformEvent = { println("onPlatformEvent received $it") },
            fontMap = FontMap(),
            imageHandlingStrategy = NetworkStrategy(),
        )
    }
}
