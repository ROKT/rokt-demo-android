package com.rokt.roktdemo.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rokt.roktdemo.ui.theme.RoktColors
import com.rokt.roktdemo.ui.theme.RoktFonts

@Composable
fun Heading(text: String) {
    Title(text = text)
    HeadingDivider()
}

@Composable
fun Title(text: String, color: Color = MaterialTheme.colors.primaryVariant) {
    Text(
        text = text,
        fontFamily = RoktFonts.HeadingsFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        color = color
    )
}

@Composable
fun SubHeading(text: String) {
    Text(
        text = text,
        fontFamily = RoktFonts.DefaultFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        color = MaterialTheme.colors.primaryVariant
    )
}

@Composable
fun ContentText(text: String, fontSize: Int = 16) {
    Text(
        text = text,
        fontFamily = RoktFonts.DefaultFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = fontSize.sp,
        color = MaterialTheme.colors.primaryVariant
    )
}

@Composable
fun HeaderTextButton(text: String, onClick: (Int) -> Unit, color: Color = Color.White) {
    ClickableText(AnnotatedString(text),
        onClick = onClick,
        style = TextStyle(color = color,
            fontFamily = RoktFonts.DefaultFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp))
}

@Composable
fun ScreenHeader(text: String) {
    Text(
        text,
        fontSize = 28.sp,
        fontFamily = RoktFonts.HeadingsFontFamily,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colors.primaryVariant
    )
}

@Composable
private fun HeadingDivider() {
    Column {
        DefaultSpace()
        Box(
            Modifier
                .width(34.dp)
                .height(6.dp)
                .background(RoktColors.LightColors.secondary)
        )
        DefaultSpace()
    }
}
