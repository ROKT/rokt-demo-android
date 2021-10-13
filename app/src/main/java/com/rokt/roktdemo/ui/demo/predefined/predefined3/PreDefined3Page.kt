package com.rokt.roktdemo.ui.demo.predefined.predefined3

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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rokt.roktdemo.R
import com.rokt.roktdemo.model.PredefinedScreen
import com.rokt.roktdemo.ui.common.LargeSpace
import com.rokt.roktdemo.ui.common.MediumSpace
import com.rokt.roktdemo.ui.common.PreDefinedHeader
import com.rokt.roktdemo.ui.common.RoktEmbeddedWidget
import com.rokt.roktdemo.ui.common.SMALL_SPACE
import com.rokt.roktdemo.ui.common.SmallSpace
import com.rokt.roktdemo.ui.common.XSmallSpace
import com.rokt.roktdemo.ui.demo.predefined.PreDefinedViewModel
import com.rokt.roktdemo.ui.theme.RoktColors
import com.rokt.roktdemo.ui.theme.RoktFonts

@Composable
fun PreDefined3Page(
    onBackPressed: () -> Unit,
    preDefinedScreenModel: PredefinedScreen,
    viewModel: PreDefinedViewModel = hiltViewModel()
) {
    viewModel.initWithModel(preDefinedScreenModel)
    val scrollState = rememberScrollState(0)
    Column(
        Modifier
            .fillMaxSize()
            .background(color = RoktColors.PreDefined3White)
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
        Congratulation(isBranded = viewModel.state.value.isBranded)
        PostConfirmation()
        SmallSpace()
        RoktEmbeddedWidget {
            viewModel.onEmbeddedWidgetAddedToView(it)
        }
    }
}

@Composable
private fun Congratulation(isBranded: Boolean) {
    Column {
        if (isBranded) {
            Image(
                painter = painterResource(id = R.drawable.ic_share_green),
                contentDescription = stringResource(R.string.text_share),
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.End),
                alignment = Alignment.CenterEnd
            )
        }
        LargeSpace()
        TextLato(
            text = stringResource(R.string.text_congratulations),
            textSize = 26,
            Modifier.fillMaxWidth(),
            isBold = true,
            isItalic = true,
            color = if (isBranded) RoktColors.PreDefined3Green else RoktColors.PreDefined3Blue
        )
        MediumSpace()
        TextLato(
            text = stringResource(R.string.text_your_ad_created),
            textSize = 16, color = RoktColors.PreDefined3Black1
        )
        MediumSpace()
        Divider()
    }
}

@Composable
private fun PostConfirmation() {
    SmallSpace()
    TextLato(
        text = stringResource(R.string.text_post_your_ad),
        textSize = 16,
        Modifier.fillMaxWidth(),
        isBold = true
    )
    XSmallSpace()
    TextLato(
        text = stringResource(R.string.text_increase_your_changes),
        textSize = 12,
        Modifier.fillMaxWidth(),
        color = RoktColors.PreDefined3Gray2,
        lineHeight = 15
    )
    SmallSpace()
    Image(
        painter = painterResource(id = R.drawable.ic_ebay_logo),
        contentDescription = stringResource(R.string.text_ebay),
        Modifier
            .fillMaxWidth()
    )
    SmallSpace()
    TextLato(
        text = stringResource(R.string.text_limited_items),
        textSize = 9,
        Modifier.fillMaxWidth(),
        color = RoktColors.PreDefined3Gray3
    )
    SmallSpace()
    Divider()
}

@Composable
private fun Divider() {
    Box(
        Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(color = RoktColors.PreDefined3Gray1)
    )
}

@Composable
private fun TextLato(
    text: String,
    textSize: Int,
    modifier: Modifier = Modifier,
    isBold: Boolean = false,
    isItalic: Boolean = false,
    textAlign: TextAlign = TextAlign.Center,
    color: Color = RoktColors.PreDefined3Black2,
    lineHeight: Int = 20
) {
    Text(
        text,
        modifier,
        color,
        fontSize = textSize.sp,
        fontStyle = if (isItalic) FontStyle.Italic else FontStyle.Normal,
        fontFamily = RoktFonts.Lato,
        fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
        textAlign = textAlign,
        lineHeight = lineHeight.sp
    )
}
