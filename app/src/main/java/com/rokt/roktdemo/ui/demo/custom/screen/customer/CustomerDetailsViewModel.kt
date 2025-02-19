package com.rokt.roktdemo.ui.demo.custom.screen.customer

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rokt.roktdemo.model.CustomerDetails
import com.rokt.roktdemo.ui.demo.custom.screen.common.EditableField
import com.rokt.roktdemo.ui.demo.custom.screen.common.EditableFieldSet
import com.rokt.roktdemo.ui.demo.custom.screen.common.createEditableField
import com.rokt.roktdemo.utils.updateKeyAtIndex
import com.rokt.roktdemo.utils.updateValueAtIndex
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.util.ArrayList
import java.util.HashMap
import javax.inject.Inject

@HiltViewModel
class CustomerDetailsViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(CustomerDetailsScreenState(initialized = false))
    private val showAdvancedOptions = MutableStateFlow(false)
    private val selectedCountry = MutableStateFlow(DEFAULT_COUNTRY)
    private val selectedState = MutableStateFlow("")
    private val selectedPostcode = MutableStateFlow("")
    private var countryList = listOf<String>()
    private val advancedDetailsList: MutableStateFlow<List<Pair<String, String>>> =
        MutableStateFlow(listOf())

    val state: MutableStateFlow<CustomerDetailsScreenState>
        get() = _state

    fun init(customerDetails: CustomerDetails, advancedDetails: HashMap<String, String>) {
        if (state.value.initialized.not()) {
            // Set default values
            selectedPostcode.value = customerDetails.postcode
            selectedState.value = customerDetails.state
            advancedDetailsList.value = advancedDetails.toList()
            countryList = customerDetails.country

            // Observe changes to default values
            initState()
        }
    }

    private fun initState() {
        viewModelScope.launch {
            combine(
                selectedCountry,
                advancedDetailsList,
                selectedState,
                selectedPostcode,
                showAdvancedOptions
            ) { country, advanced, state, postcode, showAdvanced ->
                val advancedFields = advanced.mapIndexed { index, item ->
                    EditableFieldSet(
                        index, key = item.first, value = item.second,
                        onKeyChanged = { newKey ->
                            onKeyChanged(newKey, index)
                        },
                        onValueChanged = { newValue ->
                            onValueChanged(newValue, index)
                        }
                    )
                }
                CustomerDetailsScreenState(
                    showAdvanced,
                    country,
                    countryList,
                    createEditableField(
                        state,
                        {
                            selectedState.value = it
                        }
                    ),
                    createEditableField(
                        postcode,
                        {
                            selectedPostcode.value = it
                        }
                    ),
                    advancedFields
                )
            }.collect {
                _state.value = it
            }
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun onKeyChanged(newKey: String, index: Int) {
        advancedDetailsList.value =
            ArrayList(advancedDetailsList.value).updateKeyAtIndex(index, newKey)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun onValueChanged(newValue: String, index: Int) {
        advancedDetailsList.value =
            ArrayList(advancedDetailsList.value).updateValueAtIndex(index, newValue)
    }

    fun onToggleAdvancedOptions() {
        showAdvancedOptions.value = showAdvancedOptions.value.not()
    }

    fun onCountrySelected(text: String) {
        selectedCountry.value = text
    }

    fun getCustomerDetails(): HashMap<String, String> {
        val screenState = state.value
        return hashMapOf<String, String>().apply {
            if (screenState.selectedCountry.isNotBlank()) {
                this["country"] = screenState.selectedCountry
            }
            screenState.advancedOptions.forEach {
                if (it.key.isNotEmpty() && it.value.isNotEmpty()) {
                    this[it.key] = it.value
                }
            }
        }
    }

    companion object {
        private const val DEFAULT_COUNTRY = "US"
    }
}

data class CustomerDetailsScreenState(
    val showAdvancedOptions: Boolean = false,
    val selectedCountry: String = "",
    val countryList: List<String> = listOf(),
    val selectedState: EditableField = EditableField(),
    val postcode: EditableField = EditableField(),
    val advancedOptions: List<EditableFieldSet> = listOf(),
    val initialized: Boolean = true,
)
