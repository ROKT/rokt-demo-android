package com.rokt.roktdemo.ui.demo.walkthrough

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rokt.roktdemo.data.data
import com.rokt.roktdemo.data.library.DemoLibraryRepository
import com.rokt.roktdemo.data.succeeded
import com.rokt.roktdemo.ui.state.RoktDemoErrorTypes
import com.rokt.roktdemo.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalkthroughViewModel @Inject constructor(demoLibraryRepository: DemoLibraryRepository) :
    ViewModel() {

    private var selectedIndex = MutableStateFlow(0)

    private val _state = MutableStateFlow(UiState<WalkThroughPageState>(loading = true))
    val state: StateFlow<UiState<WalkThroughPageState>>
        get() = _state

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)

            combine(selectedIndex, demoLibraryRepository.getDemoLibrary()) { index, result ->
                if (result.succeeded) {
                    UiState(
                        data = getWalkthroughPage(
                            result.data().defaultPlacementsExamples.screens.count(),
                            index
                        )
                    )
                } else {
                    UiState(error = RoktDemoErrorTypes.GENERAL)
                }
            }.collect {
                _state.value = it
            }
        }
    }

    private fun getWalkthroughPage(screenCount: Int, index: Int): WalkThroughPageState {
        return WalkThroughPageState(
            index,
            screenCount,
            getCounterText(screenCount, index),
            getNextButtonText(screenCount, index)
        )
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal fun getNextButtonText(screenCount: Int, index: Int): String {
        return when {
            screenCount < 1 -> ""
            index == screenCount - 1 -> "Quit Demo"
            else -> "Next"
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal fun getCounterText(screenCount: Int, index: Int): String {
        return when {
            screenCount < 1 -> ""
            index == screenCount -> "$index/$screenCount"
            else -> "${index + 1}/$screenCount"
        }
    }

    internal fun nextButtonPressed() {
        state.value.data?.let {
            if (selectedIndex.value < it.screenCount) {
                selectedIndex.value = selectedIndex.value + 1
            }
        }
    }

    internal fun backButtonPressed() {
        if (selectedIndex.value > 0) {
            selectedIndex.value = selectedIndex.value - 1
        }
    }
}

data class WalkThroughPageState(
    val currentIndex: Int = 0,
    val screenCount: Int = 0,
    val screenCounterText: String = "",
    val navButtonText: String = "",
)
