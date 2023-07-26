package com.rokt.roktdemo.ui.layouts

import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.journeyapps.barcodescanner.ScanIntentResult
import com.rokt.roktdemo.ui.demo.RoktExecutor
import com.rokt.roktdemo.ui.layouts.model.PreviewData
import com.rokt.roktdemo.ui.state.RoktDemoErrorTypes
import com.rokt.roktdemo.ui.state.UiState
import com.rokt.roktsdk.Widget
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber
import java.lang.ref.WeakReference
import java.util.Timer
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.concurrent.timerTask

@HiltViewModel
class ScanQrViewModel @Inject constructor(
    private val roktExecutor: RoktExecutor
) : ViewModel() {

    private val _state: MutableStateFlow<UiState<ScanQrState>> =
        MutableStateFlow(UiState(loading = true))
    val state: StateFlow<UiState<ScanQrState>>
        get() = _state

    init {
        _state.value = UiState(data = ScanQrState(true))
    }

    fun qrCodeScanned(success: ScanIntentResult?, embeddedWidget: WeakReference<Widget>?) {
        if (success == null) {
            _state.update { UiState(data = ScanQrState(false)) }
        } else {
            try {
                val qrData = Gson().fromJson(success.contents, PreviewData::class.java)
                Timber.d(success.contents)
                _state.update { UiState(data = ScanQrState(scannedData = qrData)) }
                Timer().schedule(
                    timerTask { executePreview(embeddedWidget) },
                    TimeUnit.SECONDS.toMillis(EXECUTE_DELAY_SECONDS)
                )
            } catch (e: Exception) {
                Timber.d(e, "Scan QR code error")
                _state.update { UiState(error = RoktDemoErrorTypes.QRCODE) }
            }
        }
    }

    fun executePreview(embeddedWidget: WeakReference<Widget>?) {
        val scannedData = _state.value.data?.scannedData
        scannedData?.let { data ->
            val attributes = hashMapOf(
                ATTRIBUTE_IS_DEMO to true.toString(),
                ATTRIBUTE_LAYOUT_ID to data.previewId,
                ATTRIBUTE_CREATIVE_ID to data.creativeIds.joinToString(separator = ",")
            )
            val placeholders = embeddedWidget?.let { hashMapOf(PREVIEW_PLACEHOLDER to it) }
            roktExecutor.executeRokt("", attributes, placeholders)
        }
    }
}

data class ScanQrState(
    val scanning: Boolean = true,
    val scannedData: PreviewData? = null
)

private const val ATTRIBUTE_IS_DEMO = "isDemo"
private const val ATTRIBUTE_LAYOUT_ID = "layoutId"
private const val ATTRIBUTE_CREATIVE_ID = "creativeId"
private const val PREVIEW_PLACEHOLDER = "#rokt-placeholder"
private const val EXECUTE_DELAY_SECONDS = 3L
