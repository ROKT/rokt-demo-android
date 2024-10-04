package com.rokt.roktdemo.ui.layouts

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.rokt.networkhelper.data.RoktNetwork
import com.rokt.roktdemo.ui.demo.RoktExecutor
import com.rokt.roktdemo.ui.layouts.model.DemoLayoutConfig
import com.rokt.roktdemo.ui.layouts.model.DemoLayoutSlotConfig
import com.rokt.roktdemo.ui.layouts.model.DemoModeConfig
import com.rokt.roktdemo.ui.layouts.model.PreviewData
import com.rokt.roktdemo.ui.state.RoktDemoErrorTypes
import com.rokt.roktdemo.ui.state.UiState
import com.rokt.roktsdk.BuildConfig
import com.rokt.roktsdk.Widget
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
        _state.value = UiState(data = ScanQrState())
    }

    fun qrCodeScanned(rawData: String?) {
        if (rawData == null) {
            _state.update { UiState(data = ScanQrState()) }
        } else {
            try {
                val qrData = Gson().fromJson(rawData, PreviewData::class.java)
                Timber.d(rawData)
                _state.update {
                    UiState(
                        data = ScanQrState(
                            scannedData = qrData,
                            previewState = PreviewState.ScannedState(qrData)
                        )
                    )
                }
            } catch (e: Exception) {
                Timber.d(e, "Scan QR code error")
                _state.update { UiState(error = RoktDemoErrorTypes.QRCODE) }
            }
        }
    }

    fun legacySdkSelected(embeddedWidget: WeakReference<Widget>?) {
        Timer().schedule(
            timerTask { executePreview(embeddedWidget) },
            TimeUnit.SECONDS.toMillis(EXECUTE_DELAY_SECONDS)
        )
    }

    fun uxHelperSdkSelected(context: Context) {
        viewModelScope.launch {
            _state.value.data?.scannedData?.let { data ->
                RoktNetwork.init(data.tagId, BuildConfig.VERSION_NAME, context)
                RoktNetwork.experience("", buildAttributes(data))
                    .onSuccess { response ->
                        Timber.d(response)
                        _state.update {
                            it.copy(
                                data = _state.value.data!!.copy(
                                    previewState = PreviewState.UxHelperSdkData(
                                        response
                                    )
                                )
                            )
                        }
                    }
                    .onFailure { Timber.e(it) }
            }
        }

    }

    fun executePreview(embeddedWidget: WeakReference<Widget>?) {
        val scannedData = _state.value.data?.scannedData
        scannedData?.let { data ->
            val attributes = buildAttributes(data)
            val placeholders = embeddedWidget?.let { hashMapOf(PREVIEW_PLACEHOLDER to it) }
            roktExecutor.executeRokt("", HashMap(attributes), placeholders)
            _state.update {
                it.copy(
                    data = _state.value.data!!.copy(
                        previewState = PreviewState.LegacySdkData(
                            data.tagId
                        )
                    )
                )
            }
        }
    }

    private fun buildAttributes(data: PreviewData): Map<String, String> {
        val attributes = mutableMapOf<String, String>()
        attributes[ATTRIBUTE_IS_DEMO] = true.toString()
        data.language?.let { attributes[ATTRIBUTE_LANGUAGE] = it }
        getDemoConfig(data)?.let { attributes[ATTRIBUTE_DEMO_CONFIG] = it }
        return attributes
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
    val scannedData: PreviewData? = null,
    val previewState: PreviewState? = null,
)

sealed class PreviewState {
    data class ScannedState(val data: PreviewData) : PreviewState()
    data class LegacySdkData(val tagId: String) : PreviewState()
    data class UxHelperSdkData(val response: String) : PreviewState()
}

private const val ATTRIBUTE_IS_DEMO = "isDemo"
private const val ATTRIBUTE_LANGUAGE = "rokt.language"
private const val ATTRIBUTE_DEMO_CONFIG = "demoConfig"
private const val PREVIEW_PLACEHOLDER = "#rokt-placeholder"
private const val EXECUTE_DELAY_SECONDS = 3L
