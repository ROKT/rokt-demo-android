package com.rokt.roktdemo.data.validate

interface ValidatorRepository {
    fun validateAccountId(accountId: String): ValidationState
}

data class ValidationState(
    val fieldStatus: ValidationStatus,
    val fieldErrorMessage: String = "",
)

enum class ValidationStatus {
    NONE, VALID, INVALID
}
