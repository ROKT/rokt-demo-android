package com.rokt.roktdemo.ui.demo.predefined.predefined1

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.rokt.roktdemo.R
import com.rokt.roktdemo.model.PredefinedScreen
import com.rokt.roktdemo.ui.common.BackButton
import com.rokt.roktdemo.ui.common.HeaderTextButton
import com.rokt.roktdemo.ui.common.RoktHeader
import com.rokt.roktdemo.ui.common.SMALL_SPACE
import com.rokt.roktdemo.ui.common.SmallSpace
import com.rokt.roktdemo.ui.common.XSmallSpace
import com.rokt.roktdemo.ui.demo.predefined.PreDefinedViewModel
import com.rokt.roktdemo.ui.theme.RoktColors
import com.rokt.roktdemo.ui.theme.RoktFonts
import com.rokt.roktsdk.Widget
import java.lang.ref.WeakReference

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
        Header(onBackPressed)
        ThankYouView()
        ConfirmationView()
        RoktEmbeddedWidget {
            viewModel.onEmbeddedWidgetAddedToView(it)
        }
    }
}

@Composable
private fun Header(
    onBackPressed: () -> Unit,
) {
    RoktHeader {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BackButton(backPressed = onBackPressed)
            HeaderTextButton("EXIT", { onBackPressed.invoke() })
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
    Divider(1)
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
private fun RoktEmbeddedWidget(onWidgetAdded: (WeakReference<Widget>) -> Unit) {
    XSmallSpace()
    AndroidView(
        modifier = Modifier
            .fillMaxWidth(),
        factory = { context ->
            Widget(context).apply {
                onWidgetAdded(WeakReference(this))
            }
        }
    )
}

@Composable
private fun Divider(size: Int) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(size.dp)
            .background(color = RoktColors.BorderColor)
    )
}

@Composable
private fun TextLato(
    text: String,
    textSize: Int,
    modifier: Modifier = Modifier,
    isBold: Boolean = false,
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
        text,
        modifier,
        fontSize = textSize.sp,
        fontFamily = RoktFonts.Lato,
        fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
        textAlign = textAlign
    )
}
