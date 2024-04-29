package com.rokt.roktdemo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import com.rokt.roktdemo.data.settings.DEBUG_LOGS_ENABLED
import com.rokt.roktdemo.data.settings.STAGE_ENV_ENABLED
import com.rokt.roktdemo.data.settings.SettingsRepository
import com.rokt.roktsdk.Rokt
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(settingsRepository: SettingsRepository) : ViewModel() {
    private val _selectedTagId = MutableLiveData<String>()
    val selectedTagId = _selectedTagId.distinctUntilChanged()

    init {
        if (settingsRepository.getBooleanSettingsValue(STAGE_ENV_ENABLED)) {
            Rokt.setEnvironment(Rokt.Environment.Stage)
        }
        if (settingsRepository.getBooleanSettingsValue(DEBUG_LOGS_ENABLED)) {
            Rokt.setLoggingEnabled(true)
        }
    }

    fun updateSelectedTagId(tagId: String) {
        _selectedTagId.value = tagId
    }
}
