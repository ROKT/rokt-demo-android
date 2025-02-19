package com.rokt.roktdemo.ui.demo.predefined

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rokt.roktdemo.model.PredefinedScreen
import com.rokt.roktdemo.ui.demo.RoktExecutor
import com.rokt.roktdemo.utils.randomiseEmail
import com.rokt.roktsdk.Widget
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class PreDefinedViewModel @Inject constructor(private val roktExecutor: RoktExecutor) :
    ViewModel() {
    private val _state = MutableStateFlow(PreDefinedState())
    val state: MutableStateFlow<PreDefinedState>
        get() = _state

    fun initWithModel(preDefinedScreenModel: PredefinedScreen) {
        viewModelScope.launch {
            _state.value = PreDefinedState(
                preDefinedScreenModel.attributes,
                preDefinedScreenModel.viewName,
                preDefinedScreenModel.placeholderName,
                preDefinedScreenModel.isBranded
            )
        }
    }

    fun onEmbeddedWidgetAddedToView(widget: WeakReference<Widget>) {
        roktExecutor.executeRokt(
            viewName = state.value.viewName,
            getAttributes(),
            hashMapOf(state.value.placeholderName to widget)
        )
    }

    private fun getAttributes(): HashMap<String, String> {
        val attributes = state.value.attributes
        attributes.put("email", Random.randomiseEmail(attributes.get("email")))
        return attributes
    }
}

data class PreDefinedState(
    val attributes: HashMap<String, String> = hashMapOf(),
    val viewName: String = "",
    val placeholderName: String = "",
    val isBranded: Boolean = false
)
