package com.rokt.roktdemo.data.validate

class ValidatorMockImplementation : ValidatorRepository {
    override fun validateAccountId(accountId: String): ValidationState {
        return if (accountId.isEmpty()) {
            ValidationState(
                fieldStatus = ValidationStatus.INVALID,
                "Account Id can't be empty"
            )
        } else {
            ValidationState(fieldStatus = ValidationStatus.VALID)
        }
    }

    override fun validatePassword(password: String, input: String): ValidationState {
        return if (input.isEmpty()) {
            ValidationState(
                fieldStatus = ValidationStatus.INVALID,
                "Password can't be empty"
            )
        } else if (input != password) {
            ValidationState(
                fieldStatus = ValidationStatus.INVALID,
                "Incorrect password!"
            )
        } else {
            ValidationState(fieldStatus = ValidationStatus.VALID)
        }
    }
}
