package com.rokt.roktdemo.ui.common

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

const val X_SMALL_SPACE = 8
const val SMALL_SPACE = 16
const val DEFAULT_SPACE = 20
const val LARGE_SPACE = 40

const val DEFAULT_PADDING = 24


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

@Composable
fun SmallSpace() {
    Spacer(
        Modifier
            .fillMaxWidth()
            .height(SMALL_SPACE.dp)
    )
}

@Composable
fun XSmallSpace() {
    Spacer(
        Modifier
            .fillMaxWidth()
            .height(X_SMALL_SPACE.dp)
    )
}