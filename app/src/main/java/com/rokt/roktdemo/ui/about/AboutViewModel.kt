package com.rokt.roktdemo.ui.about

import android.content.Context
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rokt.roktdemo.data.about.AboutRoktRepository
import com.rokt.roktdemo.data.data
import com.rokt.roktdemo.data.exception
import com.rokt.roktdemo.data.succeeded
import com.rokt.roktdemo.model.AboutRokt
import com.rokt.roktdemo.ui.demo.getErrorType
import com.rokt.roktdemo.ui.state.UiState
import com.rokt.roktdemo.utils.openInBrowser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor(
    private val aboutRepository: AboutRoktRepository,
) : ViewModel() {

    private val _state: MutableStateFlow<UiState<AboutRokt>> =
        MutableStateFlow(UiState(loading = true))

    val state: StateFlow<UiState<AboutRokt>>
        get() = _state

    init {
        viewModelScope.launch {
            getAboutPage()
        }
    }

    private suspend fun getAboutPage() {
        aboutRepository.getAboutRokt().collect {
            if (it.succeeded) {
                _state.value =
                    UiState(data = it.data())
            } else {
                _state.value = UiState(error = it.exception().getErrorType())
            }
        }
    }

    fun linkClicked(context: Context, url: String) {
        url.toUri().openInBrowser(context = context)
    }

}