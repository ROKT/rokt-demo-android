package com.rokt.roktdemo.ui.demo.walkthrough.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rokt.roktdemo.data.data
import com.rokt.roktdemo.data.library.DemoLibraryRepository
import com.rokt.roktdemo.data.succeeded
import com.rokt.roktdemo.model.ScreenType
import com.rokt.roktdemo.ui.demo.RoktExecutor
import com.rokt.roktdemo.ui.state.RoktDemoErrorTypes
import com.rokt.roktdemo.ui.state.UiState
import com.rokt.roktsdk.Widget
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import java.util.HashMap
import javax.inject.Inject

@HiltViewModel
class WalkthroughScreenViewModel @Inject constructor(
    demoLibraryRepository: DemoLibraryRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(UiState<WalkthroughScreenState>())
    val state: MutableStateFlow<UiState<WalkthroughScreenState>>
        get() = _state

    private val screenIndex: MutableStateFlow<Int> = MutableStateFlow(0)

    init {
        viewModelScope.launch {
            combine(screenIndex, demoLibraryRepository.getDemoLibrary()) { screenIndex, library ->
                if (library.succeeded) {
                    val screens = library.data().defaultPlacementsExamples.screens
                    screens[screenIndex].let { screen ->
                        UiState(
                            data = WalkthroughScreenState(
                                screen.title,
                                screen.description,
                                screen.placeholderName,
                                screen.attributes,
                                screen.viewName,
                                screen.type == ScreenType.Embedded
                            )
                        )
                    }
                } else {
                    UiState(error = RoktDemoErrorTypes.GENERAL)
                }
            }.collect {
                _state.value = it
            }
        }
    }

    fun setScreenIndex(index: Int) {
        screenIndex.value = index
    }

    fun onEmbeddedWidgetAddedToView(widget: WeakReference<Widget>) {
        state.value.data?.let {
            executeRokt(hashMapOf(it.placeholderName to widget))
        }
    }

    fun onViewExampleButtonClicked() = executeRokt()

    private fun executeRokt(placeholders: HashMap<String, WeakReference<Widget>>? = null) {
        state.value.data?.let {
            RoktExecutor.executeRokt(it.viewName, it.attributes, placeholders)
        }
    }
}

data class WalkthroughScreenState(
    val title: String = "",
    val description: String = "",
    val placeholderName: String = "",
    val attributes: HashMap<String, String> = hashMapOf(),
    val viewName: String = "",
    val isEmbedded: Boolean = false,
)
