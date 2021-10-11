package com.rokt.roktdemo.ui.demo.predefined.predefined1

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rokt.roktdemo.R
import com.rokt.roktdemo.model.PredefinedScreen
import com.rokt.roktdemo.ui.common.PreDefinedHeader
import com.rokt.roktdemo.ui.common.RoktEmbeddedWidget
import com.rokt.roktdemo.ui.common.SMALL_SPACE
import com.rokt.roktdemo.ui.common.SmallSpace
import com.rokt.roktdemo.ui.common.XSmallSpace
import com.rokt.roktdemo.ui.demo.predefined.PreDefinedViewModel
import com.rokt.roktdemo.ui.theme.RoktColors
import com.rokt.roktdemo.ui.theme.RoktFonts

@Composable
fun PreDefined1Page(
    onBackPressed: () -> Unit,
    preDefinedScreenModel: PredefinedScreen,
    viewModel: PreDefinedViewModel = hiltViewModel()
) {
    viewModel.initWithModel(preDefinedScreenModel)
    val scrollState = rememberScrollState(0)

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(color = MaterialTheme.colors.surface)
    ) {
        PreDefinedHeader(onBackPressed)
        ThankYouView()
        ConfirmationView()
        if (viewModel.state.value.isBranded) {
            OrderSummaryView()
        }
        XSmallSpace()
        RoktEmbeddedWidget {
            viewModel.onEmbeddedWidgetAddedToView(it)
        }
    }
}

@Composable
private fun ThankYouView() {
    Box(
        Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.onPrimary)
            .padding(SMALL_SPACE.dp)
    ) {
        TextLato(stringResource(id = R.string.text_thank_you), 20, Modifier.align(Alignment.Center))
        Image(
            painter = painterResource(id = R.drawable.ic_share),
            contentDescription = stringResource(R.string.text_share),
            Modifier.align(Alignment.CenterEnd)
        )
    }
    Divider()
}

@Composable
private fun ConfirmationView() {
    Column(
        Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.onPrimary)
            .padding(SMALL_SPACE.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SmallSpace()
        Image(
            painter = painterResource(id = R.drawable.ic_done),
            contentDescription = stringResource(R.string.text_done)
        )
        XSmallSpace()
        TextLato(
            text = stringResource(id = R.string.text_amazing_deal),
            textSize = 22,
            modifier = Modifier.fillMaxWidth(),
            true
        )
        XSmallSpace()
        TextLato(
            text = stringResource(id = R.string.text_receipt),
            textSize = 16
        )
        SmallSpace()
    }
}

@Composable
private fun OrderSummaryView() {
    XSmallSpace()
    Column(
        Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.onPrimary)
            .padding(SMALL_SPACE.dp)
    ) {
        Box(
            Modifier
                .padding(bottom = SMALL_SPACE.dp)

        ) {
            TextLato(
                stringResource(R.string.text_order_details), 16,
                Modifier.fillMaxWidth(), false, TextAlign.Start
            )

            TextLato(
                stringResource(id = R.string.text_total_0), 16,
                Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = SMALL_SPACE.dp),
                false, TextAlign.End
            )

            Image(
                painter = painterResource(id = R.drawable.ic_arrow_up_gray),
                contentDescription = stringResource(R.string.text_arrow_up),
                Modifier.align(Alignment.CenterEnd)
            )
        }
        Divider(RoktColors.PreDefined1Gray1)

        TextLato(
            text = stringResource(R.string.text_two_month),
            textSize = 14,
            Modifier
                .fillMaxWidth()
                .padding(top = SMALL_SPACE.dp),
            false, TextAlign.Start, RoktColors.PreDefined1Gray2
        )
    }
    Divider()
    TextLato(
        text = stringResource(R.string.text_view_your),
        textSize = 16,
        Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.onPrimary)
            .padding(SMALL_SPACE.dp),
        true,
        TextAlign.Center, RoktColors.PreDefined1Green
    )
}

@Composable
private fun Divider(color: Color = RoktColors.PreDefined1Gray3) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(color = color)
    )
}

@Composable
private fun TextLato(
    text: String,
    textSize: Int,
    modifier: Modifier = Modifier,
    isBold: Boolean = false,
    textAlign: TextAlign = TextAlign.Center,
    color: Color = RoktColors.PreDefined1Black
) {
    Text(
        text,
        modifier,
        color,
        fontSize = textSize.sp,
        fontFamily = RoktFonts.Lato,
        fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
        textAlign = textAlign
    )
}
