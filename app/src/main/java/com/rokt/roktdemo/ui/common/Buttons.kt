package com.rokt.roktdemo.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rokt.roktdemo.ui.theme.RoktFonts

const val BUTTON_HEIGHT = 64
const val BUTTON_FONT_SIZE = 20

@Composable
fun ButtonDark(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(BUTTON_HEIGHT.dp),
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = MaterialTheme.colors.secondary,
            contentColor = MaterialTheme.colors.primary
        ),
        shape = RectangleShape
    ) {
        Text(
            text, fontSize = BUTTON_FONT_SIZE.sp,
            fontFamily = RoktFonts.DefaultFontFamily,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ButtonLight(text: String, onClick: () -> Unit) {
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
            .height(BUTTON_HEIGHT.dp),
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.secondary
        ),
        shape = RectangleShape,
        border = BorderStroke(2.dp, MaterialTheme.colors.secondary)
    ) {
        Text(
            text, fontSize = BUTTON_FONT_SIZE.sp, fontFamily = RoktFonts.DefaultFontFamily,
            fontWeight = FontWeight.Bold
        )
    }
}
