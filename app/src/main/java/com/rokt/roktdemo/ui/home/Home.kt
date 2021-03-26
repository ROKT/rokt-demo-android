package com.rokt.roktdemo.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rokt.roktdemo.BuildConfig
import com.rokt.roktdemo.R
import com.rokt.roktdemo.ui.common.ButtonDark
import com.rokt.roktdemo.ui.common.ButtonLight
import com.rokt.roktdemo.ui.common.DefaultSpace
import com.rokt.roktdemo.ui.common.LARGE_SPACE
import com.rokt.roktdemo.ui.common.LargeSpace

@Composable
fun Home() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(PaddingValues(0.dp, 0.dp, 0.dp, LARGE_SPACE.dp)),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HomeButtons()
        DefaultSpace()
        Footer()
        LargeSpace()
    }
}

@Composable
private fun HomeButtons() {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.9F),
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        ButtonDark(text = stringResource(R.string.menu_button_demo), onClick = {})
        DefaultSpace()
        ButtonLight(text = stringResource(R.string.menu_button_about), onClick = {})
        DefaultSpace()
        ButtonLight(text = stringResource(R.string.menu_button_contact), onClick = {})
    }
}

@Composable
private fun Footer() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FooterText(stringResource(R.string.footer_copyright))
        FooterText("${stringResource(R.string.footer_text_version)}${BuildConfig.VERSION_NAME}")
    }
}

@Composable
private fun FooterText(text: String) {
    Text(text = text, color = MaterialTheme.colors.secondaryVariant, fontSize = 12.sp)
}
