package com.rokt.roktdemo.ui.demo.custom.screen.confirmation

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.insets.systemBarsPadding
import com.rokt.roktdemo.R
import com.rokt.roktdemo.ui.common.DefaultSpace
import com.rokt.roktdemo.ui.common.MediumSpace
import com.rokt.roktdemo.ui.common.SmallSpace
import com.rokt.roktdemo.ui.common.SubHeading
import com.rokt.roktdemo.ui.common.XSmallSpace
import com.rokt.roktdemo.ui.demo.custom.CustomCheckoutViewModel
import com.rokt.roktdemo.ui.theme.RoktColors
import com.rokt.roktdemo.ui.theme.RoktFonts
import com.rokt.roktsdk.Widget
import java.lang.ref.WeakReference

@Composable
fun CustomCheckoutConfirmationScreen(parentViewModel: CustomCheckoutViewModel) {
    val scroll = rememberScrollState(0)

    CustomCheckoutConfirmationScreenContent(scroll) {
        parentViewModel.onEmbeddedWidgetAddedToView(it)
    }
}

@Composable
private fun CustomCheckoutConfirmationScreenContent(
    scrollState: ScrollState,
    onWidgetAdded: (WeakReference<Widget>) -> Unit,
) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .systemBarsPadding()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_tick_confirmation_page),
            contentDescription = stringResource(R.string.content_description_tick_icon)
        )
        DefaultSpace()
        SubHeading(stringResource(R.string.confirmation_heading_text), 24)
        XSmallSpace()
        ConfirmationText(text = stringResource(R.string.text_order_numbr))
        MediumSpace()
        RoktEmbeddedWidget(onWidgetAdded)
        MediumSpace()
        OrderSummary()
        MediumSpace()
        CustomerDetails()
    }
}

@Composable
private fun RoktEmbeddedWidget(onWidgetAdded: (WeakReference<Widget>) -> Unit) {
    SmallSpace()
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, MaterialTheme.colors.onSurface),
        factory = { context ->
            Widget(context).apply {
                onWidgetAdded(WeakReference(this))
            }
        }
    )
}

@Composable
private fun SectionHeading(text: String) {
    BoldText(text, Modifier.padding(15.dp))
    Divider()
}

@Composable
private fun OrderSummary() {
    Column(
        Modifier
            .fillMaxWidth()
            .border(2.dp, MaterialTheme.colors.onSurface)
            .background(Color.White)
    ) {
        SectionHeading(
            text = stringResource(R.string.text_order_summary)
        )
        Column(Modifier.padding(start = 24.dp, end = 24.dp)) {
            Column(Modifier.fillMaxWidth()) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 25.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ConfirmationText(
                        stringResource(R.string.text_basic_tshirt)
                    )
                    ConfirmationText(
                        stringResource(R.string.text_basic_tshirt_price)
                    )
                }
                Text(
                    stringResource(R.string.text_medium),
                    Modifier.padding(bottom = 16.dp),
                    fontSize = 12.sp,
                    color = MaterialTheme.colors.secondaryVariant,
                    fontFamily = RoktFonts.DefaultFontFamily
                )
            }
            Divider()
            SmallSpace()
            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ConfirmationText(
                    stringResource(R.string.text_subtotal)
                )
                ConfirmationText(
                    stringResource(R.string.text_basic_tshirt_price)
                )
            }
            SmallSpace()
            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ConfirmationText(
                    stringResource(R.string.text_shipping)
                )
                ConfirmationText(
                    stringResource(R.string.text_shipping_price)
                )
            }
            SmallSpace()
            Divider()
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BoldText(
                    stringResource(R.string.text_total), Modifier.padding(top = 16.dp)
                )
                BoldText(
                    stringResource(R.string.text_basic_tshirt_price),
                    Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}

@Composable
private fun BoldText(text: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = text,
        color = MaterialTheme.colors.primaryVariant,
        fontFamily = RoktFonts.DefaultFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    )
}

@Composable
private fun BoldTextSmall(text: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = text,
        color = MaterialTheme.colors.primaryVariant,
        fontFamily = RoktFonts.DefaultFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    )
}

@Composable
private fun CustomerDetails() {
    Column(
        Modifier
            .fillMaxWidth()
            .border(2.dp, MaterialTheme.colors.onSurface)
            .background(Color.White)
    ) {
        SectionHeading(text = stringResource(R.string.text_customer_details))
        MediumSpace()
        Column(Modifier.padding(start = 24.dp, end = 24.dp)) {
            BoldTextSmall(stringResource(R.string.text_email))
            XSmallSpace()
            ConfirmationText(stringResource(R.string.text_email_value))
            MediumSpace()

            BoldTextSmall(stringResource(R.string.text_shipping_address))
            XSmallSpace()
            ConfirmationText(
                stringResource(R.string.text_shipping_address_value)
            )
            MediumSpace()
            BoldTextSmall(stringResource(R.string.text_billing_address))
            XSmallSpace()
            ConfirmationText(
                stringResource(R.string.text_shipping_address_value)
            )

            MediumSpace()
            BoldTextSmall(stringResource(R.string.text_payment_method))
            XSmallSpace()
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier
                        .width(34.dp)
                        .background(MaterialTheme.colors.primaryVariant)
                        .height(21.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_apple),
                        contentDescription = stringResource(R.string.content_description_apple_logo),
                        Modifier
                            .align(Alignment.Center)
                    )
                }
                ConfirmationText(
                    text = stringResource(id = R.string.text_payment_details),
                    Modifier.padding(start = 5.dp)
                )
            }
            MediumSpace()
            BoldTextSmall(stringResource(R.string.text_shipping_method))
            XSmallSpace()
            ConfirmationText(stringResource(R.string.text_shipping_duration))
            MediumSpace()
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = R.drawable.image_map),
                contentDescription = stringResource(R.string.content_description_map_image)
            )
            MediumSpace()
        }
    }
}

@Composable
private fun ConfirmationText(text: String, modifier: Modifier = Modifier) {
    Text(
        text,
        modifier = modifier,
        fontSize = 16.sp,
        color = MaterialTheme.colors.secondaryVariant,
        fontFamily = RoktFonts.DefaultFontFamily
    )
}

@Composable
private fun Divider() {
    Box(
        Modifier
            .fillMaxWidth()
            .height(1.5.dp)
            .background(color = RoktColors.BorderColor)
    )
}
