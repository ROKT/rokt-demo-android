package com.rokt.roktdemo.ui.demo.custom.screen.customer

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rokt.roktdemo.data.data
import com.rokt.roktdemo.data.library.DemoLibraryRepository
import com.rokt.roktdemo.data.succeeded
import com.rokt.roktdemo.ui.demo.custom.screen.common.EditableField
import com.rokt.roktdemo.ui.demo.custom.screen.common.EditableFieldSet
import com.rokt.roktdemo.ui.demo.custom.screen.common.createEditableField
import com.rokt.roktdemo.ui.state.UiState
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
class CustomerDetailsViewModel @Inject constructor(
    repository: DemoLibraryRepository,
) : ViewModel() {

    private val _state: MutableStateFlow<UiState<CustomerDetailsScreenState>> = MutableStateFlow(
        UiState(loading = true)
    )

    private val showAdvancedOptions = MutableStateFlow(false)
    private val selectedCountry = MutableStateFlow("")
    private val selectedState = MutableStateFlow("")
    private val selectedPostcode = MutableStateFlow("")
    private var countryList = listOf<String>()
    private val advancedDetailsList: MutableStateFlow<List<Pair<String, String>>> =
        MutableStateFlow(listOf())

    val state: MutableStateFlow<UiState<CustomerDetailsScreenState>>
        get() = _state

    init {
        viewModelScope.launch {
            val demoLibrary = repository.getDemoLibrary()

            demoLibrary.collect {
                if (it.succeeded) {
                    with(it.data().customConfigurationPage) {
                        selectedCountry.value = Companion.DEFAULT_COUNTRY
                        selectedPostcode.value = this.customerDetails.postcode
                        selectedState.value = this.customerDetails.state
                        advancedDetailsList.value = this.advancedDetails.toList()
                        countryList = this.customerDetails.country
                    }
                }
            }
        }

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

                UiState(
                    data = CustomerDetailsScreenState(
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
        state.value.data?.let { screenState ->
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
        return hashMapOf()
    }

    companion object {
        private const val DEFAULT_COUNTRY = "AU"
    }
}

data class CustomerDetailsScreenState(
    val showAdvancedOptions: Boolean = false,
    val selectedCountry: String = "",
    val countryList: List<String> = listOf(),
    val selectedState: EditableField = EditableField(),
    val postcode: EditableField = EditableField(),
    val advancedOptions: List<EditableFieldSet> = listOf(),
)
