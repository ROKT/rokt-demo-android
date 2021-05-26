package com.rokt.roktdemo.ui.state

data class UiState<T>(
    val loading: Boolean = false,
    val error: RoktDemoErrorTypes? = null,
    val data: T? = null,
) {
    val hasError: Boolean
        get() = error != null

    val hasData: Boolean
        get() = data != null
}

enum class RoktDemoErrorTypes {
    GENERAL, NETWORK
}
