package com.rokt.roktdemo.ui.demo.error

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rokt.roktdemo.R
import com.rokt.roktdemo.ui.common.ContentText
import com.rokt.roktdemo.ui.common.RoktBackground
import com.rokt.roktdemo.ui.common.Title
import com.rokt.roktdemo.ui.common.XSmallSpace
import com.rokt.roktdemo.ui.state.RoktDemoErrorTypes

@Composable
fun RoktError(errorType: RoktDemoErrorTypes?) {
    if (errorType == RoktDemoErrorTypes.NETWORK) {
        NetworkError()
    } else {
        GeneralError()
    }
}

@Composable
private fun GeneralError() {
    Box(
        Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        RoktBackground()
        Column(
            Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_error),
                contentDescription = stringResource(R.string.content_description_error_icon)
            )
            XSmallSpace()
            Title(stringResource(R.string.error_title_text), textAlign = TextAlign.Center)
            XSmallSpace()
            ContentText(
                text = stringResource(R.string.error_message_text),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun NetworkError() {
    Box(
        Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        RoktBackground()
        Column(
            Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_error),
                contentDescription = stringResource(R.string.content_description_error_icon)
            )
            XSmallSpace()
            Title(stringResource(R.string.network_error_title_text), textAlign = TextAlign.Center)
        }
    }
}
