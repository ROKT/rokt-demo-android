package com.rokt.roktdemo.ui.demo.custom.screen.account

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rokt.roktdemo.data.validate.ValidationState
import com.rokt.roktdemo.data.validate.ValidationStatus
import com.rokt.roktdemo.data.validate.ValidatorRepository
import com.rokt.roktdemo.model.AccountDetails
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
    private val _state = MutableStateFlow(AccountDetailsViewState(initialized = false))
    val state: MutableStateFlow<AccountDetailsViewState>
        get() = _state

    private val account = MutableStateFlow(
        AccountDetailsViewData("", "", "", "", "")
    )
    private var password = ""

    private val accountValidationState =
        MutableStateFlow(ValidationState(fieldStatus = ValidationStatus.NONE))
    private val passwordValidationState =
        MutableStateFlow(ValidationState(fieldStatus = ValidationStatus.NONE))

    fun init(accountDetails: AccountDetails) {
        if (state.value.initialized.not()) {
            // Set default values
            account.value.accountId = accountDetails.accountID
            account.value.viewName = accountDetails.viewName
            account.value.placementLocation1 = accountDetails.placementLocation1
            account.value.placementLocation2 = accountDetails.placementLocation2
            password = accountDetails.password
            // Observe changes to default values
            initState()
        }
    }

    private fun initState() {
        viewModelScope.launch {
            combine(
                account,
                accountValidationState,
                passwordValidationState
            ) { accountDetail, accountValidation, passwordValidation ->
                AccountDetailsViewState(
                    accountId = createEditableField(
                        text = accountDetail.accountId,
                        onFieldEdited = {
                            account.value = account.value.copy(accountId = it)
                            onAccountFieldEdited()
                        },
                        accountValidation.fieldErrorMessage
                    ),
                    viewName = createEditableField(
                        text = accountDetail.viewName,
                        onFieldEdited = {
                            account.value = account.value.copy(viewName = it)
                        }
                    ),
                    placementLocation1 = createEditableField(
                        text = accountDetail.placementLocation1,
                        onFieldEdited = {
                            account.value = account.value.copy(placementLocation1 = it)
                        }
                    ),
                    placementLocation2 = createEditableField(
                        text = accountDetail.placementLocation2,
                        onFieldEdited = {
                            account.value = account.value.copy(placementLocation2 = it)
                        }
                    ),
                    password = createEditableField(
                        text = accountDetail.password,
                        onFieldEdited = {
                            account.value = account.value.copy(password = it)
                            onPasswordFieldEdited()
                        },
                        passwordValidation.fieldErrorMessage
                    ),
                    formValidated = accountValidationState.value.fieldStatus == ValidationStatus.VALID &&
                        passwordValidationState.value.fieldStatus == ValidationStatus.VALID
                )
            }.collect {
                _state.value = it
            }
        }
    }

    fun continueButtonPressed() {
        accountValidationState.value = validator.validateAccountId(account.value.accountId)
        passwordValidationState.value = validator.validatePassword(password, account.value.password)
    }

    fun onNavigatedAway() {
        // Reset validationState, so when we return the fields can be modified
        accountValidationState.value = ValidationState(ValidationStatus.NONE)
        passwordValidationState.value = ValidationState(ValidationStatus.NONE)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal fun onAccountFieldEdited() {
        accountValidationState.value = ValidationState(ValidationStatus.NONE)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal fun onPasswordFieldEdited() {
        passwordValidationState.value = ValidationState(ValidationStatus.NONE)
    }
}

data class AccountDetailsViewState(
    val accountId: EditableField = EditableField(),
    val viewName: EditableField = EditableField(),
    val placementLocation1: EditableField = EditableField(),
    val placementLocation2: EditableField = EditableField(),
    val password: EditableField = EditableField(),
    val formValidated: Boolean = false,
    val initialized: Boolean = true,
)

data class AccountDetailsViewData(
    var accountId: String,
    var viewName: String,
    var placementLocation1: String,
    var placementLocation2: String,
    var password: String
)
