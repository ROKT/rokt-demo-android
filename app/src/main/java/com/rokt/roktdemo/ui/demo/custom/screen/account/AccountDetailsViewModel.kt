package com.rokt.roktdemo.ui.demo.custom.screen.account

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rokt.roktdemo.data.validate.ValidationState
import com.rokt.roktdemo.data.validate.ValidationStatus
import com.rokt.roktdemo.data.validate.ValidatorRepository
import com.rokt.roktdemo.model.DemoLibrary
import com.rokt.roktdemo.ui.demo.custom.screen.common.EditableField
import com.rokt.roktdemo.ui.demo.custom.screen.common.createEditableField
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountDetailsViewModel @Inject constructor(
    private val validator: ValidatorRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(AccountDetailsViewState())
    val state: MutableStateFlow<AccountDetailsViewState>
        get() = _state

    private val accountId = MutableStateFlow("")
    private val viewName = MutableStateFlow("")
    private val placementLocation1 = MutableStateFlow("")
    private val placementLocation2 = MutableStateFlow("")

    private val validationState =
        MutableStateFlow(ValidationState(fieldStatus = ValidationStatus.NONE))

    fun initWithLibrary(demoLibrary: DemoLibrary) {
        val accountDetails = demoLibrary.customConfigurationPage.accountDetails
        accountId.value = accountDetails.accountID
        viewName.value = accountDetails.viewName
        placementLocation1.value = accountDetails.placementLocation1
        placementLocation2.value = accountDetails.placementLocation2

        viewModelScope.launch {
            combine(
                accountId,
                viewName,
                placementLocation1,
                placementLocation2,
                validationState
            ) { id, name, placement1, placement2, validation ->
                AccountDetailsViewState(
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
                )
            }.collect {
                _state.value = it
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
