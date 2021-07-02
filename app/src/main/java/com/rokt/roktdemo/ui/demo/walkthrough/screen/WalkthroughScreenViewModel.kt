package com.rokt.roktdemo.ui.demo.walkthrough.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rokt.roktdemo.model.DemoLibrary
import com.rokt.roktdemo.model.ScreenType
import com.rokt.roktdemo.ui.demo.RoktExecutor
import com.rokt.roktsdk.Widget
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import java.util.HashMap
import javax.inject.Inject

@HiltViewModel
class WalkthroughScreenViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(WalkthroughScreenState())
    val state: MutableStateFlow<WalkthroughScreenState>
        get() = _state

    private val screenIndex: MutableStateFlow<Int> = MutableStateFlow(0)

    fun initWithLibrary(demoLibrary: DemoLibrary, index: Int) {
        screenIndex.value = index

        viewModelScope.launch {
            screenIndex.collect { screenIndex ->
                val screens = demoLibrary.defaultPlacementsExamples.screens
                screens[screenIndex].let { screen ->

                    _state.value = WalkthroughScreenState(
                        screen.title,
                        screen.description,
                        screen.placeholderName,
                        screen.attributes,
                        screen.viewName,
                        screen.type == ScreenType.Embedded
                    )
                }
            }
        }
    }

    fun onEmbeddedWidgetAddedToView(widget: WeakReference<Widget>) {
        executeRokt(hashMapOf(state.value.placeholderName to widget))
    }

    fun onViewExampleButtonClicked() = executeRokt()

    private fun executeRokt(placeholders: HashMap<String, WeakReference<Widget>>? = null) {
        RoktExecutor.executeRokt(state.value.viewName, state.value.attributes, placeholders)
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
