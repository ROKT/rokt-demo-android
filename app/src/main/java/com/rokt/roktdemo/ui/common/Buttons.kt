package com.rokt.roktdemo.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

const val BUTTON_PADDING = 15
const val BUTTON_FONT_SIZE = 20

@Composable
fun ButtonDark(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = MaterialTheme.colors.secondary,
            contentColor = MaterialTheme.colors.primary
        ),
        shape = RectangleShape,
        contentPadding = PaddingValues(BUTTON_PADDING.dp)
    ) {
        Text(text, fontSize = BUTTON_FONT_SIZE.sp)
    }
}

@Composable
fun ButtonLight(text: String, onClick: () -> Unit) {
    OutlinedButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.secondary
        ),
        shape = RectangleShape,
        border = BorderStroke(2.dp, MaterialTheme.colors.secondary),
        contentPadding = PaddingValues(BUTTON_PADDING.dp)

    ) {
        Text(text, fontSize = BUTTON_FONT_SIZE.sp)
    }
}
