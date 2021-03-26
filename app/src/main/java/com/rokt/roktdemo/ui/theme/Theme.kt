package com.rokt.roktdemo.ui.theme

import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.rokt.roktdemo.R

object RoktColors {
    private val RoktPink = Color(181, 30, 109)
    private val RoktGrey = Color(24, 24, 24)
    private val TextLight = Color(126, 123, 122)

    val LightColors = lightColors(
        primary = Color.White,
        secondary = RoktPink,
        primaryVariant = RoktGrey,
        secondaryVariant = TextLight
    )
}

object RoktFonts {
    private val light = Font(R.font.font_soleil_light, FontWeight.Light)
    private val regular = Font(R.font.font_soleil_regular, FontWeight.Medium)
    private val bold = Font(R.font.font_soleil_bold, FontWeight.Bold)

    val DefaultFontFamily = FontFamily(light, regular, bold)
}
