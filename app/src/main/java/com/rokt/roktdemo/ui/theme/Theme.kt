package com.rokt.roktdemo.ui.theme

import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.rokt.roktdemo.R

object RoktColors {
    val RoktPink = Color(181, 30, 109)
    private val RoktGrey = Color(24, 24, 24)
    private val TextLight = Color(67, 67, 67)
    private val BackgroundColor = Color(245, 245, 245)
    val BorderColor = Color(217, 217, 217)

    val HintTextColor = Color(140, 140, 140)
    val ErrorColor = Color(212, 56, 13)

    val LightColors = lightColors(
        primary = Color.White,
        secondary = RoktPink,
        primaryVariant = RoktGrey,
        secondaryVariant = TextLight,
        onPrimary = Color.White,
        surface = BackgroundColor,
        onSurface = BorderColor
    )
}

object RoktFonts {
    private val light = Font(R.font.font_soleil_light, FontWeight.Light)
    private val regular = Font(R.font.font_soleil_regular, FontWeight.Medium)
    private val bold = Font(R.font.font_soleil_bold, FontWeight.Bold)

    // Balto
    private val heading = Font(R.font.balto_bold, FontWeight.Bold)

    val DefaultFontFamily = FontFamily(light, regular, bold)
    val HeadingsFontFamily = FontFamily(heading)
}
