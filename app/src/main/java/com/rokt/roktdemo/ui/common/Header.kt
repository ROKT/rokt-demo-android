package com.rokt.roktdemo.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsPadding
import com.rokt.roktdemo.ui.theme.RoktColors


@Composable
fun RoktHeader(content: @Composable RowScope.() -> Unit) {
    Box(Modifier
        .fillMaxWidth()
        .height(99.dp)
        .background(RoktColors.LightColors.primaryVariant)) {

        Row(modifier = Modifier
            .statusBarsPadding()
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(end = 16.dp),
            verticalAlignment = Alignment.CenterVertically) {
            content()
        }
    }
}