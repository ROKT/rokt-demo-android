package com.rokt.roktdemo.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
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
    modifier: Modifier = Modifier.fillMaxWidth(),
) {
    val borderColor =
        if (errorText.isBlank()) MaterialTheme.colors.onSurface else RoktColors.ErrorColor

    androidx.compose.material.TextField(
        value = text,
        onValueChange = { onValueChange(it) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
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
        modifier = modifier
            .padding(top = 20.dp)
            .background(Color.White)
            .border(2.dp, borderColor)
    )
}