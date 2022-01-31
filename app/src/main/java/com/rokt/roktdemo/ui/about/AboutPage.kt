package com.rokt.roktdemo.ui.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.coil.rememberCoilPainter
import com.rokt.roktdemo.model.AboutContent
import com.rokt.roktdemo.model.AboutLink
import com.rokt.roktdemo.model.AboutRokt
import com.rokt.roktdemo.ui.common.BackButton
import com.rokt.roktdemo.ui.common.ButtonLight
import com.rokt.roktdemo.ui.common.ContentText
import com.rokt.roktdemo.ui.common.DefaultSpace
import com.rokt.roktdemo.ui.common.Heading
import com.rokt.roktdemo.ui.common.LargeSpace
import com.rokt.roktdemo.ui.common.LoadingPage
import com.rokt.roktdemo.ui.common.MEDIUM_SPACE
import com.rokt.roktdemo.ui.demo.error.RoktError

private const val HEADER_TOP_PADDING = 50 // How far from the top the header items sit

@Composable
fun AboutPage(backPressed: () -> Unit, viewModel: AboutViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsState()
    val scroll = rememberScrollState(0)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White, RectangleShape)
    ) {

        when {
            state.value.loading -> {
                LoadingPage()
            }
            state.value.hasData -> {
                AboutPageContent(scroll, state.value.data!!, viewModel, backPressed)
            }
            else -> {
                RoktError(errorType = state.value.error)
            }
        }
    }
}

@Composable
private fun AboutPageContent(
    scroll: ScrollState,
    aboutPageContent: AboutRokt,
    viewModel: AboutViewModel,
    backPressed: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Box(Modifier.padding(PaddingValues(start = 3.dp, top = HEADER_TOP_PADDING.dp))) {
            BackButton(backPressed, MaterialTheme.colors.primaryVariant)
        }

        Column(
            Modifier
                .verticalScroll(scroll)
                .padding(PaddingValues(MEDIUM_SPACE.dp, 10.dp))
        ) {
            Contents(aboutPageContent.contents)
            DefaultSpace()
            Links(aboutPageContent.links, viewModel = viewModel)
            LargeSpace()
        }
    }
}

@Composable
private fun Contents(content: List<AboutContent>) {
    content.forEach {
        Column {
            it.imageUrl.takeIf { it?.isNotEmpty() == true }?.let { imageUrl ->
                Image(
                    painter = rememberCoilPainter(
                        request = imageUrl
                    ),
                    contentDescription = it.title,
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
