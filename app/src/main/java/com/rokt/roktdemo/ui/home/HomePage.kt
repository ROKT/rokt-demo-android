package com.rokt.roktdemo.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rokt.roktdemo.BuildConfig
import com.rokt.roktdemo.R
import com.rokt.roktdemo.ui.MainActions
import com.rokt.roktdemo.ui.common.ButtonDark
import com.rokt.roktdemo.ui.common.ButtonLight
import com.rokt.roktdemo.ui.common.DEFAULT_SPACE
import com.rokt.roktdemo.ui.common.DefaultSpace
import com.rokt.roktdemo.ui.common.LARGE_SPACE
import com.rokt.roktdemo.ui.common.LargeSpace
import com.rokt.roktdemo.ui.common.X_SMALL_SPACE
import com.rokt.roktdemo.ui.theme.RoktFonts

@Composable
fun HomePage(actions: MainActions) {
    val viewModel: HomeViewModel = hiltViewModel()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        HomeLogo()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(PaddingValues(0.dp, 0.dp, 0.dp, LARGE_SPACE.dp)),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            HomeButtons(viewModel, actions = actions)
            Footer()
            LargeSpace()
        }
    }
}

@Composable
private fun HomeLogo() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6F)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Row(Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center) {
                Image(
                    painter = painterResource(id = R.drawable.ic_rokt_connector_filled),
                    contentDescription = stringResource(R.string.content_description_connector),
                    contentScale = ContentScale.FillBounds
                )
                Spacer(
                    Modifier
                        .width(X_SMALL_SPACE.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_rokt_logo),
                    contentDescription = stringResource(R.string.content_description_rokt_logo),
                    contentScale = ContentScale.FillBounds
                )
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth(0.8F)
                    .padding(PaddingValues(top = DEFAULT_SPACE.dp)),
                text = stringResource(R.string.content_description_rokt_title),
                fontFamily = RoktFonts.DefaultFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun HomeButtons(viewModel: HomeViewModel, actions: MainActions) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth(0.9F)
            .background(Color.White, RectangleShape)
            .padding(PaddingValues(top = LARGE_SPACE.dp, bottom = DEFAULT_SPACE.dp)),
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        ButtonDark(
            text = stringResource(R.string.menu_button_demo),
            onClick = actions.demoLibraryClicked
        )
        DefaultSpace()
        ButtonLight(
            text = stringResource(R.string.menu_button_about),
            onClick = actions.aboutRoktClicked
        )
        DefaultSpace()
        ButtonLight(
            text = stringResource(R.string.menu_button_contact),
            onClick = {
                viewModel.contactUsClicked(context)
            }
        )
    }
}

@Composable
private fun Footer() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FooterText(stringResource(R.string.footer_copyright))
        FooterText("${stringResource(R.string.footer_text_version)}${BuildConfig.VERSION_NAME}")
    }
}

@Composable
private fun FooterText(text: String) {
    Text(
        text = text, color = MaterialTheme.colors.secondaryVariant,
        fontSize = 14.sp, fontFamily = RoktFonts.DefaultFontFamily,
        fontWeight = FontWeight.Normal,
        lineHeight = 16.sp
    )
}
