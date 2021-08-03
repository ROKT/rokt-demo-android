package com.rokt.roktdemo.ui.demo.summary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.rokt.roktdemo.R
import com.rokt.roktdemo.ui.common.ContentText
import com.rokt.roktdemo.ui.common.MediumSpace
import com.rokt.roktdemo.ui.common.SMALL_SPACE
import com.rokt.roktdemo.ui.common.SmallSpace
import com.rokt.roktdemo.ui.common.SubHeading
import com.rokt.roktdemo.ui.common.X_SMALL_SPACE
import com.rokt.roktdemo.ui.theme.RoktFonts

@Composable
fun SummaryDisclaimerDialog(
    openDialog: MutableState<Boolean>,
    text: String,
    onDismiss: () -> Unit,
    onPositive: () -> Unit
) {
    if (openDialog.value) {
        Dialog(onDismissRequest = onDismiss) {
            Surface(
                shape = RectangleShape,
                color = Color.White
            ) {
                Column(
                    modifier = Modifier.padding(SMALL_SPACE.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SubHeading(text = stringResource(R.string.text_disclaimer))
                    SmallSpace()
                    ContentText(text)
                    MediumSpace()
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Button(
                            onClick = onPositive,
                            modifier = Modifier
                                .height(44.dp)
                                .padding(end = X_SMALL_SPACE.dp)
                                .weight(1F),
                            colors = ButtonDefaults.textButtonColors(
                                backgroundColor = MaterialTheme.colors.secondary,
                                contentColor = MaterialTheme.colors.primary
                            ),
                            shape = RectangleShape
                        ) {
                            SmallButtonText(text = stringResource(R.string.summary_dialog_btn_positive))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SmallButtonText(text: String) {
    Text(
        text, fontSize = SMALL_SPACE.sp, fontFamily = RoktFonts.DefaultFontFamily,
        fontWeight = FontWeight.Bold
    )
}
