package com.rokt.roktdemo.ui.settings

import androidx.lifecycle.ViewModel
import com.rokt.roktdemo.data.settings.DEBUG_LOGS_ENABLED
import com.rokt.roktdemo.data.settings.STAGE_ENV_ENABLED
import com.rokt.roktdemo.data.settings.SettingsRepository
import com.rokt.roktdemo.ui.state.UiState
import com.rokt.roktsdk.Rokt
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _state: MutableStateFlow<UiState<SettingsViewState>> =
        MutableStateFlow(UiState(loading = true))
    val state: StateFlow<UiState<SettingsViewState>>
        get() = _state

    init {
        _state.value = UiState(
            data = SettingsViewState(
                stageEnvironment = settingsRepository.getBooleanSettingsValue(STAGE_ENV_ENABLED),
                debugLogsEnabled = settingsRepository.getBooleanSettingsValue(DEBUG_LOGS_ENABLED),
            )
        )
    }

    fun onStageEnvironmentChange(value: Boolean) {
        if (value) {
            Rokt.setEnvironment(Rokt.Environment.Stage)
        } else {
            Rokt.setEnvironment(Rokt.Environment.Prod)
        }
        settingsRepository.setBooleanSettingsValue(STAGE_ENV_ENABLED, value)
        _state.update { it.copy(data = it.data?.copy(stageEnvironment = value)) }
    }

    fun onDebugLogsChange(value: Boolean) {
        if (value) {
            Rokt.setLoggingEnabled(true)
        } else {
            Rokt.setLoggingEnabled(false)
        }
        settingsRepository.setBooleanSettingsValue(DEBUG_LOGS_ENABLED, value)
        _state.update { it.copy(data = it.data?.copy(debugLogsEnabled = value)) }
    }
}

data class SettingsViewState(
    val stageEnvironment: Boolean,
    val debugLogsEnabled: Boolean,
)