package com.rokt.roktdemo.ui.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import com.rokt.roktdemo.R
import com.rokt.roktdemo.model.AboutContent
import com.rokt.roktdemo.model.AboutLink
import com.rokt.roktdemo.model.AboutRokt
import com.rokt.roktdemo.ui.common.BackButton
import com.rokt.roktdemo.ui.common.ButtonLight
import com.rokt.roktdemo.ui.common.ContentText
import com.rokt.roktdemo.ui.common.DefaultSpace
import com.rokt.roktdemo.ui.common.Heading
import com.rokt.roktdemo.ui.common.LargeSpace
import com.rokt.roktdemo.ui.theme.RoktColors
import com.rokt.roktdemo.ui.theme.RoktFonts

private const val FIXED_HEADER_HEIGHT = 70 // The non scrolling top bar height
private const val HEADER_TOP_PADDING = 50 // How far from the top the header items sit

@Composable
fun AboutPage(backPressed : () -> Unit) {
    val viewModel: AboutViewModel = hiltNavGraphViewModel()

    val aboutPageContent = viewModel.getAboutPage()
    val scroll = rememberScrollState(0)

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        AboutPageContent(scroll, aboutPageContent, viewModel)
        StickyHeader(backPressed)
    }
}

@Composable
private fun AboutPageContent(scroll: ScrollState, aboutPageContent: AboutRokt, viewModel: AboutViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scroll)
    ) {

        Header()

        Column(Modifier.padding(24.dp)) {
            Contents(aboutPageContent.content)
            DefaultSpace()
            Links(aboutPageContent.links, viewModel = viewModel)
        }

        LargeSpace()
    }
}

@Composable
private fun Contents(content: List<AboutContent>) {
    content.forEach {
        Column {
            it.imageUrl?.let {
                //  TODO: Get image from server once available
                Image(
                    painter = painterResource(id = R.drawable.header_about),
                    contentDescription = "content image",
                    modifier = Modifier.fillMaxWidth()
                )

                DefaultSpace()
            }

            Heading(it.title)
            ContentText(text = it.content)
        }
    }
}

@Composable
private fun Links(links: List<AboutLink>, viewModel: AboutViewModel) {
    val context = LocalContext.current
    links.forEach {
        ButtonLight(
            text = it.text,
            onClick = {
                viewModel.linkClicked(context, it.url)
            }
        )
        DefaultSpace()
    }
}

@Composable
private fun StickyHeader(backPressed: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(FIXED_HEADER_HEIGHT.dp),
        contentAlignment = Alignment.CenterStart
    ) {

        Image(
            painter = painterResource(id = R.drawable.header_about),
            contentDescription = stringResource(R.string.content_description_top_bar_background),
            modifier = Modifier
                .fillMaxWidth(),
            contentScale = ContentScale.Crop,
            alignment = Alignment.TopCenter
        )

        Image(
            painter = painterResource(id = R.drawable.ic_bg_grad),
            contentDescription = stringResource(R.string.content_description_gradient),
            modifier = Modifier
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )

        BackButton(backPressed)
    }
}


@Composable
private fun Header() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Image(
            painter = painterResource(id = R.drawable.header_about),
            contentDescription = stringResource(R.string.content_description_about_header),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )

        Column {
            Spacer(modifier = Modifier.height(HEADER_TOP_PADDING.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_rokt_connector),
                contentDescription = stringResource(R.string.content_description_connector),
                alignment = Alignment.BottomEnd,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            )
        }

        Column(
            Modifier.matchParentSize(), verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(HEADER_TOP_PADDING.dp))

            Text(
                text = stringResource(R.string.content_description_header_about_text),
                fontFamily = RoktFonts.HeadingsFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                textAlign = TextAlign.Center,
                color = RoktColors.LightColors.primary
            )
        }
    }
}

