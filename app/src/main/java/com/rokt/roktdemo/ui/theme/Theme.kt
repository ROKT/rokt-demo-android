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

    val PreDefined1Black = Color(54, 48, 46)
    val PreDefined1Green = Color(83, 163, 23)
    val PreDefined1Gray1 = Color(235, 236, 237)
    val PreDefined1Gray2 = Color(117, 120, 123)
    val PreDefined1Gray3 = Color(209, 212, 215)

    val PreDefined2Black = Color(43, 49, 55)
    val PreDefined2White = Color(255, 255, 255)
    val PreDefined2Gray1 = Color(131, 142, 154)
    val PreDefined2Gray2 = Color(192, 194, 196)
    val PreDefined2Purple = Color(218, 64, 134)

    val PreDefined3White = Color(255, 255, 255)
    val PreDefined3Black1 = Color(32, 32, 34)
    val PreDefined3Black2 = Color(68, 69, 70)
    val PreDefined3Gray1 = Color(223, 224, 225)
    val PreDefined3Gray2 = Color(68, 69, 70)
    val PreDefined3Gray3 = Color(157, 162, 163)
    val PreDefined3Green = Color(55, 171, 50)
    val PreDefined3Blue = Color(14, 39, 125)

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
    // Archievo
    private val regular = Font(R.font.font_archivo_regular, FontWeight.Normal)
    private val bold = Font(R.font.font_archivo_semibold, FontWeight.Bold)

    // Lato
    private val latoNormal = Font(R.font.lato_regular, FontWeight.Normal)
    private val latoBold = Font(R.font.lato_bold, FontWeight.Bold)

    // Arial
    private val arialNormal = Font(R.font.arial, FontWeight.Normal)
    private val arialBold = Font(R.font.arial_bold, FontWeight.Bold)

    // Font Families
    val DefaultFontFamily = FontFamily(regular, bold)
    val Lato = FontFamily(latoNormal, latoBold)
    val Arial = FontFamily(arialNormal, arialBold)
}
