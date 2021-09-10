package com.rokt.roktdemo.ui.demo.summary

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.systemBarsPadding
import com.rokt.roktdemo.MainActivityViewModel
import com.rokt.roktdemo.R
import com.rokt.roktdemo.ui.common.BackButton
import com.rokt.roktdemo.ui.common.ButtonDark
import com.rokt.roktdemo.ui.common.ContentText
import com.rokt.roktdemo.ui.common.LargeSpace
import com.rokt.roktdemo.ui.common.MEDIUM_SPACE
import com.rokt.roktdemo.ui.common.MediumSpace
import com.rokt.roktdemo.ui.common.Title

@Composable
fun SummaryPage(
    backPressed: () -> Unit,
    startDemoPressed: () -> Unit,
    activityViewModel: MainActivityViewModel,
    viewModel: SummaryViewModel,
) {
    val summaryPageState = viewModel.state
    val scroll = rememberScrollState(0)
    val openDialog = remember { mutableStateOf(false) }

    activityViewModel.updateSelectedTagId(summaryPageState.selectedTagId)
    SummaryPageSuccessPage(backPressed, summaryPageState, openDialog, startDemoPressed, scroll)
}

@Composable
fun SummaryPageSuccessPage(
    backPressed: () -> Unit,
    summaryPageState: SummaryPageState,
    openDialog: MutableState<Boolean>,
    startDemoPressed: () -> Unit,
    scroll: ScrollState,
) {
    Column(
        Modifier
            .background(color = MaterialTheme.colors.primaryVariant)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        LargeSpace()
        BackButton(backPressed)
        LargeSpace()

        SummaryDisclaimerDialog(
            openDialog = openDialog,
            text = summaryPageState.disclaimerText,
            onDismiss = { openDialog.value = false },
            onPositive = startDemoPressed
        )

        Column(
            Modifier
                .fillMaxWidth()
                .padding(
                    PaddingValues(
                        start = MEDIUM_SPACE.dp,
                        end = MEDIUM_SPACE.dp,
                        bottom = 61.dp
                    )
                ),
        ) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    modifier = Modifier.size(width = 136.dp, height = 64.dp),
                    painter = painterResource(id = summaryPageState.drawableResourceId),
                    contentDescription = stringResource(
                        R.string.content_description_walkrough_icon
                    ),
                    colorFilter = ColorFilter.tint(Color.White)
                )
                MediumSpace()
                Title(text = summaryPageState.title, color = Color.White)
            }
        }

        Column(
            Modifier
                .background(Color.White)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Bottom
        ) {
            Box(
                Modifier
                    .padding(MEDIUM_SPACE.dp)
                    .verticalScroll(scroll)
                    .weight(1F)
                    .fillMaxHeight(),
                Alignment.TopCenter
            ) {
                ContentText(text = summaryPageState.longDescription)
            }

            Box(
                modifier = Modifier
                    .systemBarsPadding()
                    .padding(MEDIUM_SPACE.dp)
            ) {
                ButtonDark(text = stringResource(R.string.summary_page_button_text)) {
                    openDialog.value = true
                }
            }
        }
    }
}
