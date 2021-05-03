package com.rokt.roktdemo.ui.demo.walkthrough.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.rokt.roktdemo.R
import com.rokt.roktdemo.ui.common.ButtonText
import com.rokt.roktdemo.ui.common.ContentText
import com.rokt.roktdemo.ui.common.MEDIUM_SPACE
import com.rokt.roktdemo.ui.common.SmallSpace
import com.rokt.roktdemo.ui.common.SubHeading
import com.rokt.roktdemo.ui.theme.RoktFonts
import com.rokt.roktsdk.Widget
import java.lang.ref.WeakReference

@Composable
fun WalkthroughScreen(
    screenIndex: Int,
    viewModel: WalkthroughScreenViewModel = hiltNavGraphViewModel(),
) {
    viewModel.setScreenIndex(screenIndex)

    val state by viewModel.state.collectAsState()
    val scroll = rememberScrollState(0)

    Column(
        Modifier
            .fillMaxSize()
            .padding(
                PaddingValues(
                    start = MEDIUM_SPACE.dp,
                    end = MEDIUM_SPACE.dp,
                    bottom = MEDIUM_SPACE.dp
                )
            )
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(scroll)
    ) {
        Text(
            state.title,
            fontSize = 28.sp,
            fontFamily = RoktFonts.HeadingsFontFamily,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.primaryVariant
        )
        SmallSpace()
        ContentText(state.description)
        state.isEmbedded?.let { isEmbedded ->
            if (isEmbedded) {
                RoktEmbeddedWidget(viewModel::onEmbeddedWidgetAddedToView)
            } else {
                RoktFullscreenWidget(viewModel::onViewExampleButtonClicked)
            }
        }
    }
}

@Composable
fun RoktEmbeddedWidget(onWidgetAdded: (WeakReference<Widget>) -> Unit) {
    SubHeading(stringResource(R.string.text_preview))
    SmallSpace()
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context ->
            Widget(context).apply {
                onWidgetAdded(WeakReference(this))
            }
        }
    )
}

@Composable
fun ColumnScope.RoktFullscreenWidget(onViewExampleButtonClicked: () -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .weight(1f),
        verticalArrangement = Arrangement.Bottom
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = MaterialTheme.colors.secondary,
                contentColor = MaterialTheme.colors.primary
            ),
            onClick = onViewExampleButtonClicked
        ) {
            Row(
                Modifier
                    .background(MaterialTheme.colors.secondary)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ButtonText(stringResource(R.string.button_view_example_text))
                Icon(
                    painterResource(id = R.drawable.ic_eye),
                    stringResource(R.string.button_view_example_icon),
                    Modifier.padding(start = 10.dp)
                )
            }
        }
    }
}
