package com.rokt.roktdemo.ui.demo.custom.screen.account

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rokt.roktdemo.data.library.DemoLibraryRepository
import com.rokt.roktdemo.data.validate.ValidationState
import com.rokt.roktdemo.data.validate.ValidationStatus
import com.rokt.roktdemo.data.validate.ValidatorRepository
import com.rokt.roktdemo.ui.demo.custom.screen.common.EditableField
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountDetailsViewModel @Inject constructor(
    repository: DemoLibraryRepository,
    private val validator: ValidatorRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(AccountDetailsViewState())
    private val page =
        MutableStateFlow(repository.getDemoLibrary().customCustomConfigurationPage)

    val state: StateFlow<AccountDetailsViewState>
        get() = _state

    private val accountId =
        MutableStateFlow(page.value.accountDetails.accountId)
    private val viewName = MutableStateFlow(page.value.accountDetails.viewName)
    private val placementLocation1 =
        MutableStateFlow(page.value.accountDetails.placementLocation1)
    private val placementLocation2 =
        MutableStateFlow(page.value.accountDetails.placementLocation2)

    private val validationState =
        MutableStateFlow(ValidationState(fieldStatus = ValidationStatus.NONE))

    init {
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

fun createEditableField(
    text: String,
    onFieldEdited: (String) -> Unit,
    errorText: String = "",
): EditableField {
    return EditableField(
        text = text,
        onValueChanged = fun(value: String) {
            onFieldEdited.invoke(value)
        },
        errorText = errorText
    )
}

data class AccountDetailsViewState(
    val accountId: EditableField = EditableField(),
    val viewName: EditableField = EditableField(),
    val placementLocation1: EditableField = EditableField(),
    val placementLocation2: EditableField = EditableField(),
    val formValidated: Boolean = false,
)
