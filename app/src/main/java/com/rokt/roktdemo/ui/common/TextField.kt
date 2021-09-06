package com.rokt.roktdemo.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rokt.roktdemo.ui.theme.RoktColors
import com.rokt.roktdemo.ui.theme.RoktFonts

@Composable
fun RoktTextField(
    label: String,
    text: String,
    onValueChange: (String) -> Unit,
    errorText: String = "",
    isPassword: Boolean = false,
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    val borderColor =
        if (errorText.isBlank()) MaterialTheme.colors.onSurface else RoktColors.ErrorColor
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    androidx.compose.material.TextField(
        value = text,
        onValueChange = { onValueChange(it) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        ),
        label = {
            Text(
                label,
                color = RoktColors.HintTextColor,
                fontFamily = RoktFonts.DefaultFontFamily,
                fontSize = 12.sp
            )
        },
        textStyle = TextStyle(
            color = MaterialTheme.colors.primaryVariant,
            fontSize = 16.sp,
            fontFamily = RoktFonts.DefaultFontFamily
        ),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = modifier
            .padding(top = 20.dp)
            .background(Color.White)
            .border(2.dp, borderColor)
            .focusOrder(focusRequester)
    )
}
