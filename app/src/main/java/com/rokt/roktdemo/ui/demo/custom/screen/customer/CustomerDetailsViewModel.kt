package com.rokt.roktdemo.ui.demo.custom.screen.customer

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rokt.roktdemo.data.library.DemoLibraryRepository
import com.rokt.roktdemo.ui.demo.custom.screen.common.EditableField
import com.rokt.roktdemo.ui.demo.custom.screen.common.EditableFieldSet
import com.rokt.roktdemo.ui.demo.custom.screen.common.createEditableField
import com.rokt.roktdemo.utils.updateKeyAtIndex
import com.rokt.roktdemo.utils.updateValueAtIndex
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.util.ArrayList
import java.util.HashMap
import javax.inject.Inject

@HiltViewModel
class CustomerDetailsViewModel @Inject constructor(
    repository: DemoLibraryRepository,
) : ViewModel() {

    private val customerDetails =
        repository.getDemoLibrary().customCustomConfigurationPage
    private val _state = MutableStateFlow(CustomerDetailsScreenState())

    private val showAdvancedOptions = MutableStateFlow(false)
    private val selectedCountry =
        MutableStateFlow(customerDetails.customerDetails.country)

    private val selectedState =
        MutableStateFlow(customerDetails.customerDetails.state)

    private val selectedPostcode =
        MutableStateFlow(customerDetails.customerDetails.postcode)

    private val advancedDetails: MutableStateFlow<List<Pair<String, String>>> =
        MutableStateFlow(customerDetails.advancedDetails.toList())

    val state: StateFlow<CustomerDetailsScreenState>
        get() = _state

    init {
        viewModelScope.launch {
            combine(selectedCountry,
                advancedDetails,
                selectedState,
                selectedPostcode,
                showAdvancedOptions) { country, advanced, state, postcode, showAdvanced ->
                val advancedFields = advanced.mapIndexed { index, item ->
                    EditableFieldSet(index, key = item.first, value = item.second,
                        onKeyChanged = { newKey ->
                            onKeyChanged(newKey, index)
                        },
                        onValueChanged = { newValue ->
                            onValueChanged(newValue, index)
                        })
                }

                CustomerDetailsScreenState(
                    showAdvanced,
                    country,
                    createEditableField(state, {
                        selectedState.value = it
                    }), createEditableField(postcode, {
                        selectedPostcode.value = it
                    }), advancedFields)
            }.collect {
                _state.value = it
            }
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun onKeyChanged(newKey: String, index: Int) {
        advancedDetails.value =
            ArrayList(advancedDetails.value).updateKeyAtIndex(index, newKey)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun onValueChanged(newValue: String, index: Int) {
        advancedDetails.value =
            ArrayList(advancedDetails.value).updateValueAtIndex(index, newValue)
    }

    fun onToggleAdvancedOptions() {
        showAdvancedOptions.value = showAdvancedOptions.value.not()
    }

    fun onCountrySelected(text: String) {
        selectedCountry.value = text
    }

    fun getCustomerDetails(): HashMap<String, String> {
        return hashMapOf<String, String>().apply {
            if (state.value.selectedCountry.isNotBlank()) {
                this["country"] = state.value.selectedCountry
            }
            state.value.advancedOptions.forEach {
                if (it.key.isNotEmpty() && it.value.isNotEmpty()) {
                    this[it.key] = it.value
                }
            }
        }
    }
}

data class CustomerDetailsScreenState(
    val showAdvancedOptions: Boolean = false,
    val selectedCountry: String = "",
    val selectedState: EditableField = EditableField(),
    val postcode: EditableField = EditableField(),
    val advancedOptions: List<EditableFieldSet> = listOf(),
)