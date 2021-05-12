package com.rokt.roktdemo.ui.demo.custom.screen.common


data class EditableField(
    val text: String = "",
    val onValueChanged: (String) -> Unit = {},
    val errorText: String = "",
)