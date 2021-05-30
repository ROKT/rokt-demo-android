package com.rokt.roktdemo.ui.demo.custom

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import com.rokt.roktdemo.ui.demo.RoktExecutor
import com.rokt.roktsdk.Widget
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltViewModel
class CustomCheckoutViewModel @Inject constructor(private val roktExecutor: RoktExecutor) :
    ViewModel() {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal var state = CustomCheckoutPageState()
        set(value) {
            field = value
            Timber.d("Custom Checkout State Changed$value")
        }

    fun onAccountDetailsSubmitted(
        accountId: String,
        viewName: String,
        placementLocation1: String,
        placementLocation2: String,
    ) {
        state = state.copy(
            accountId = accountId,
            viewName = viewName,
            placementLocation1 = placementLocation1,
            placementLocation2 = placementLocation2
        )
    }

    fun onCustomerDetailsSubmitted(attributes: Map<String, String>) {
        state = state.copy(attributes = attributes)
    }

    fun onEmbeddedWidgetAddedToView(widget: WeakReference<Widget>) {
        roktExecutor.executeRokt(
            viewName = state.viewName,
            state.attributes,
            hashMapOf(state.placementLocation1 to widget)
        )
    }
}

data class CustomCheckoutPageState(
    val accountId: String = "",
    val viewName: String = "",
    val placementLocation1: String = "",
    val placementLocation2: String = "",
    val attributes: Map<String, String> = hashMapOf(),
)
