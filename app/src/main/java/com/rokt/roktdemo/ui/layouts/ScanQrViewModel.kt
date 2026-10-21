package com.rokt.roktdemo.ui.layouts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.rokt.roktdemo.ui.demo.RoktExecutor
import com.rokt.roktdemo.ui.layouts.model.DemoLayoutConfig
import com.rokt.roktdemo.ui.layouts.model.DemoLayoutSlotConfig
import com.rokt.roktdemo.ui.layouts.model.DemoModeConfig
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
import java.util.HashMap
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

    @Volatile
    private var scanCount = 0

    fun qrCodeScanned(rawData: String?, embeddedWidget: WeakReference<Widget>?) {
        scanCount++
        if (rawData == null) {
            _state.update { UiState(data = ScanQrState(false)) }
        } else {
            try {
                val qrData = Gson().fromJson(rawData, PreviewData::class.java)
                Timber.d(rawData)
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
        val scanId = scanCount
        scannedData?.let { data ->
            val attributes = buildMap {
                this[ATTRIBUTE_IS_DEMO] = true.toString()
                data.language?.let { this[ATTRIBUTE_LANGUAGE] = it }
                getDemoConfig(data)?.let { this[ATTRIBUTE_DEMO_CONFIG] = it }
            }
            val placeholders = embeddedWidget?.let { hashMapOf(PREVIEW_PLACEHOLDER to it) }
            roktExecutor.executeRokt(
                "",
                HashMap(attributes),
                placeholders,
                viewModelScope,
                onPlacementFailure = { onPlacementFailed(scanId) }
            )
        }
    }

    private fun onPlacementFailed(scanId: Int) {
        // Ignore a failure from a scan that a newer scan has already replaced.
        if (scanId != scanCount) return
        _state.update { current ->
            current.data?.let { UiState(data = it.copy(previewFailed = true)) } ?: current
        }
    }
}

private fun getDemoConfig(previewData: PreviewData): String? {
    previewData.layoutVariantIds?.let { layoutVariantIds ->
        val slots = mutableListOf<DemoLayoutSlotConfig>()
        previewData.creativeIds.forEachIndexed { index, creativeId ->
            val slot = DemoLayoutSlotConfig(
                layoutVariantId = layoutVariantIds[index % layoutVariantIds.count()],
                creativeId = creativeId
            )
            slots.add(slot)
        }
        val demoConfig = DemoModeConfig(
            layouts = listOf(
                DemoLayoutConfig(
                    layoutId = previewData.previewId,
                    versionId = previewData.versionId,
                    targetElementSelector = PREVIEW_PLACEHOLDER,
                    slots = slots,
                )
            )
        )
        return Gson().toJson(demoConfig)
    }
    return null
}

data class ScanQrState(
    val scanning: Boolean = true,
    val scannedData: PreviewData? = null,
    val previewFailed: Boolean = false
)

private const val ATTRIBUTE_IS_DEMO = "isDemo"
private const val ATTRIBUTE_LANGUAGE = "rokt.language"
private const val ATTRIBUTE_DEMO_CONFIG = "demo_config"
private const val PREVIEW_PLACEHOLDER = "#rokt-placeholder"
private const val EXECUTE_DELAY_SECONDS = 3L
