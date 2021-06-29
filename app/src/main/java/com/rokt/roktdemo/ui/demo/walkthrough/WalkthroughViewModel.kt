package com.rokt.roktdemo.ui.demo.walkthrough

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rokt.roktdemo.model.DemoLibrary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalkthroughViewModel @Inject constructor() :
    ViewModel() {

    private var selectedIndex = MutableStateFlow(0)
    private val _state = MutableStateFlow(WalkThroughPageState())
    val state: StateFlow<WalkThroughPageState>
        get() = _state

    fun initWithLibrary(demoLibrary: DemoLibrary) {
        viewModelScope.launch {
            selectedIndex.collect { index ->
                _state.value = getWalkthroughPage(
                    demoLibrary.defaultPlacementsExamples.screens.count(),
                    index
                )
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
        if (selectedIndex.value < state.value.screenCount) {
            selectedIndex.value = selectedIndex.value + 1
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
