package com.rokt.roktdemo.ui.common

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

const val DEFAULT_SPACE = 20
const val LARGE_SPACE = 40

@Composable
fun DefaultSpace() {
    Spacer(
        Modifier
            .fillMaxWidth()
            .height(DEFAULT_SPACE.dp)
    )
}

@Composable
fun LargeSpace() {
    Spacer(
        Modifier
            .fillMaxWidth()
            .height(LARGE_SPACE.dp)
    )
}