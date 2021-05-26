package com.rokt.roktdemo.ui.demo.custom.screen.account

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rokt.roktdemo.data.data
import com.rokt.roktdemo.data.library.DemoLibraryRepository
import com.rokt.roktdemo.data.succeeded
import com.rokt.roktdemo.data.validate.ValidationState
import com.rokt.roktdemo.data.validate.ValidationStatus
import com.rokt.roktdemo.data.validate.ValidatorRepository
import com.rokt.roktdemo.ui.demo.custom.screen.common.EditableField
import com.rokt.roktdemo.ui.demo.custom.screen.common.createEditableField
import com.rokt.roktdemo.ui.demo.error.GeneralError
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
class AccountDetailsViewModel @Inject constructor(
    val repository: DemoLibraryRepository,
    private val validator: ValidatorRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(UiState(data = AccountDetailsViewState()))
    val state: MutableStateFlow<UiState<AccountDetailsViewState>>
        get() = _state

    private val accountId = MutableStateFlow("")
    private val viewName = MutableStateFlow("")
    private val placementLocation1 = MutableStateFlow("")
    private val placementLocation2 = MutableStateFlow("")

    private val validationState =
        MutableStateFlow(ValidationState(fieldStatus = ValidationStatus.NONE))

    init {
        viewModelScope.launch {
            loadDemoLibrary()
        }

        viewModelScope.launch {
            combine(
                accountId,
                viewName,
                placementLocation1,
                placementLocation2,
                validationState
            ) { id, name, placement1, placement2, validation ->

                UiState(data = AccountDetailsViewState(
                    accountId = createEditableField(
                        text = id,
                        onFieldEdited = {
                            accountId.value = it
                            onFieldEdited()
                        },
                        validation.fieldErrorMessage
                    ),
                    viewName = createEditableField(
                        text = name,
                        onFieldEdited = {
                            viewName.value = it
                            onFieldEdited()
                        }
                    ),
                    placementLocation1 = createEditableField(
                        text = placement1,
                        onFieldEdited = {
                            placementLocation1.value = it
                            onFieldEdited()
                        }
                    ),
                    placementLocation2 = createEditableField(
                        text = placement2,
                        onFieldEdited = {
                            placementLocation2.value = it
                            onFieldEdited()
                        }
                    ),
                    formValidated = validationState.value.fieldStatus == ValidationStatus.VALID
                ))
            }.collect {
                _state.value = it
            }
        }
    }

    private suspend fun loadDemoLibrary() {
        repository.getDemoLibrary().collect {
            if (it.succeeded) {
                val accountDetails = it.data().customCustomConfigurationPage.accountDetails
                accountId.value = accountDetails.accountId
                viewName.value = accountDetails.viewName
                placementLocation1.value = accountDetails.placementLocation1
                placementLocation2.value = accountDetails.placementLocation2
            }else {
                _state.value = UiState(error = RoktDemoErrorTypes.GENERAL)
            }
        }
    }

    fun continueButtonPressed() {
        validationState.value = validator.validateAccountId(accountId.value)
    }

    fun onNavigatedAway() {
        // Reset validationState, so when we return the fields can be modified
        validationState.value = ValidationState(ValidationStatus.NONE)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal fun onFieldEdited() {
        validationState.value = ValidationState(ValidationStatus.NONE)
    }
}

data class AccountDetailsViewState(
    val accountId: EditableField = EditableField(),
    val viewName: EditableField = EditableField(),
    val placementLocation1: EditableField = EditableField(),
    val placementLocation2: EditableField = EditableField(),
    val formValidated: Boolean = false,
)
