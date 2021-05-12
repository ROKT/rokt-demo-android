package com.rokt.roktdemo.ui.demo.walkthrough.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rokt.roktdemo.data.library.DemoLibraryRepository
import com.rokt.roktdemo.model.ScreenType
import com.rokt.roktsdk.Rokt
import com.rokt.roktsdk.Widget
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltViewModel
class WalkthroughScreenViewModel @Inject constructor(
    demoLibraryRepository: DemoLibraryRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(WalkthroughScreenState())
    val state: StateFlow<WalkthroughScreenState>
        get() = _state

    private val screenIndex: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val screens =
        MutableStateFlow(demoLibraryRepository.getDemoLibrary().defaultPlacementsExamples.screens)

    init {
        viewModelScope.launch {
            combine(screenIndex, screens) { screenIndex, screens ->
                screenIndex?.let { index ->
                    screens[index].let { screen ->
                        WalkthroughScreenState(
                            screen.title,
                            screen.description,
                            screen.placeholderName,
                            screen.attributes,
                            screen.viewName,
                            screen.type == ScreenType.Embedded,
                            didLoad = true
                        )
                    }
                }
            }.collect {
                it?.let {
                    _state.value = it
                }
            }
        }
    }

    fun setScreenIndex(index: Int) {
        screenIndex.value = index
    }

    fun onEmbeddedWidgetAddedToView(widget: WeakReference<Widget>) {
        executeRokt(hashMapOf(state.value.placeholderName to widget))
    }

    fun onViewExampleButtonClicked() = executeRokt()

    private fun executeRokt(placeholders: HashMap<String, WeakReference<Widget>>? = null) {
        Rokt.execute(
            state.value.viewName,
            state.value.attributes,
            placeholders = placeholders,
            callback = object : Rokt.RoktCallback {
                override fun onLoad() {
                }

                override fun onShouldHideLoadingIndicator() {
                }

                override fun onShouldShowLoadingIndicator() {
                }

                override fun onUnload(reason: Rokt.UnloadReasons) {
                }
            }
        )
    }
}

data class WalkthroughScreenState(
    val title: String = "",
    val description: String = "",
    val placeholderName: String = "",
    val attributes: Map<String, String> = hashMapOf(),
    val viewName: String = "",
    val isEmbedded: Boolean = true,
    val didLoad: Boolean = false,
)
