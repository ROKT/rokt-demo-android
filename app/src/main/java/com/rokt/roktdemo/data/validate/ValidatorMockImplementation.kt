package com.rokt.roktdemo.data.validate

class ValidatorMockImplementation : ValidatorRepository {
    override fun validateAccountId(accountId: String): ValidationState = if (accountId.isEmpty()) {
        ValidationState(
            fieldStatus = ValidationStatus.INVALID,
            "Account Id can't be empty",
        )
    } else {
        ValidationState(fieldStatus = ValidationStatus.VALID)
    }
}
