package com.rokt.roktdemo.ui.demo.predefined.predefined2

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rokt.roktdemo.R
import com.rokt.roktdemo.model.PredefinedScreen
import com.rokt.roktdemo.ui.common.LargeSpace
import com.rokt.roktdemo.ui.common.PreDefinedHeader
import com.rokt.roktdemo.ui.common.RoktEmbeddedWidget
import com.rokt.roktdemo.ui.common.SMALL_SPACE
import com.rokt.roktdemo.ui.common.SmallSpace
import com.rokt.roktdemo.ui.common.XSmallSpace
import com.rokt.roktdemo.ui.common.X_SMALL_SPACE
import com.rokt.roktdemo.ui.demo.predefined.PreDefinedViewModel
import com.rokt.roktdemo.ui.theme.RoktColors
import com.rokt.roktdemo.ui.theme.RoktFonts

@Composable
fun PreDefined2Page(
    onBackPressed: () -> Unit,
    preDefinedScreenModel: PredefinedScreen,
    viewModel: PreDefinedViewModel = hiltViewModel()
) {
    viewModel.initWithModel(preDefinedScreenModel)
    val scrollState = rememberScrollState(0)
    Column(
        Modifier
            .fillMaxSize()
            .background(color = RoktColors.PreDefined2White)
    ) {
        PreDefinedHeader(onBackPressed)
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Content(viewModel)
        }
    }
}

@Composable
private fun Content(viewModel: PreDefinedViewModel) {
    Column(Modifier.padding(SMALL_SPACE.dp)) {
        if (viewModel.state.value.isBranded) {
            Image(
                painter = painterResource(id = R.drawable.ic_ticket),
                contentDescription = stringResource(R.string.text_share),
                Modifier
                    .width(300.dp)
                    .padding(SMALL_SPACE.dp)
                    .align(Alignment.CenterHorizontally),
                contentScale = ContentScale.FillWidth,
                alignment = Alignment.Center
            )
        }

        TextArial(
            text = stringResource(R.string.text_have_great_time),
            textSize = 30,
            isBold = true,
            textAlign = TextAlign.Start
        )
        WhatsNext(isBranded = viewModel.state.value.isBranded)
        RoktEmbeddedWidget {
            viewModel.onEmbeddedWidgetAddedToView(it)
        }
        OrderNumber()
        EventInfo()
    }
}

@Composable
private fun WhatsNext(isBranded: Boolean) {
    SmallSpace()
    TextArial(
        text = stringResource(R.string.text_whats_next),
        textSize = 16,
        color = RoktColors.PreDefined2Gray1
    )
    XSmallSpace()
    TextArial(
        text = stringResource(if (isBranded) R.string.text_predefined2_branded else R.string.text_predefined2_unbranded),
        textSize = 16
    )
    SmallSpace()
    Button(
        onClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .height(49.dp)
            .padding(end = X_SMALL_SPACE.dp),
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = RoktColors.PreDefined2Purple,
            contentColor = RoktColors.PreDefined2White
        ),
    ) {
        TextArial(
            stringResource(R.string.text_view_order),
            16,
            isBold = true,
            textAlign = TextAlign.Center,
            color = RoktColors.PreDefined2White
        )
    }
    SmallSpace()
    Button(
        onClick = {},
        border = BorderStroke(1.5.dp, RoktColors.PreDefined2Black),
        modifier = Modifier
            .fillMaxWidth()
            .height(49.dp)
            .padding(end = X_SMALL_SPACE.dp),
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = RoktColors.PreDefined2White,
            contentColor = RoktColors.PreDefined2Purple
        ),
    ) {
        TextArial(
            stringResource(R.string.text_find_more_events),
            16,
            isBold = true,
            textAlign = TextAlign.Center,
        )
    }
    SmallSpace()
    Divider()
}

@Composable
private fun OrderNumber() {
    SmallSpace()
    TextArial(
        text = stringResource(R.string.text_order_number),
        textSize = 16,
        color = RoktColors.PreDefined2Gray1
    )
    TextArial(text = stringResource(R.string.text_numbers), textSize = 16)
    SmallSpace()
    Divider()
}

@Composable
private fun EventInfo() {
    SmallSpace()
    TextArial(
        text = stringResource(R.string.text_event_info),
        textSize = 16,
        color = RoktColors.PreDefined2Gray1
    )
    XSmallSpace()
    TextArial(
        text = stringResource(R.string.text_parking_info),
        textSize = 16,
        isBold = true
    )
    XSmallSpace()
    TextArial(
        text = stringResource(R.string.text_event_time),
        textSize = 16,
        color = RoktColors.PreDefined2Gray1
    )
    SmallSpace()
    Button(
        onClick = {},
        border = BorderStroke(1.5.dp, RoktColors.PreDefined2Black),
        modifier = Modifier
            .fillMaxWidth()
            .height(49.dp)
            .padding(end = X_SMALL_SPACE.dp),
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = RoktColors.PreDefined2White,
            contentColor = RoktColors.PreDefined2Purple
        ),
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.ic_calender),
                contentDescription = stringResource(
                    R.string.text_share
                ),
                Modifier
                    .padding(end = X_SMALL_SPACE.dp)
                    .align(Alignment.CenterVertically)
            )

            TextArial(
                stringResource(R.string.text_add_to_calender),
                16,
                Modifier.align(Alignment.CenterVertically),
                isBold = true,
                textAlign = TextAlign.Center,
            )
        }
    }
    LargeSpace()
}

@Composable
private fun Divider() {
    Box(
        Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(color = RoktColors.PreDefined2Gray2)
    )
}

@Composable
private fun TextArial(
    text: String,
    textSize: Int,
    modifier: Modifier = Modifier,
    isBold: Boolean = false,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = RoktColors.PreDefined2Black
) {
    Text(
        text,
        modifier,
        color,
        fontSize = textSize.sp,
        fontFamily = RoktFonts.Arial,
        fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
        textAlign = textAlign,
        lineHeight = 22.sp
    )
}
