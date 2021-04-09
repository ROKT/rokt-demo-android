package com.rokt.roktdemo.ui.demo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import com.rokt.roktdemo.R
import com.rokt.roktdemo.ui.common.BackButton
import com.rokt.roktdemo.ui.common.ContentText
import com.rokt.roktdemo.ui.common.DEFAULT_PADDING
import com.rokt.roktdemo.ui.common.DefaultSpace
import com.rokt.roktdemo.ui.common.Heading
import com.rokt.roktdemo.ui.common.LargeSpace
import com.rokt.roktdemo.ui.common.SubHeading
import com.rokt.roktdemo.ui.common.XSmallSpace

@Composable
fun DemoPage(backPressed: () -> Unit) {
    val viewModel: DemoViewModel = hiltNavGraphViewModel()

    val demoPage = viewModel.getDemoPage()
    val scroll = rememberScrollState(0)

    Column(
        Modifier
            .background(MaterialTheme.colors.surface)
    ) {
        Column(Modifier.padding(PaddingValues(start = 3.dp, top = 54.dp))) {
            BackButton(backPressed, MaterialTheme.colors.primaryVariant)
        }

        Column(
            modifier = Modifier
                .verticalScroll(scroll)
                .padding(PaddingValues(DEFAULT_PADDING.dp, 10.dp))
        ) {
            Heading(demoPage.title)
            Text(demoPage.description)
            DefaultSpace()
            demoPage.items.forEach {
                DemoListItemView(item = it)
                DefaultSpace()
            }

            LargeSpace()
        }
    }
}

@Composable
fun DemoListItemView(item: DemoPageListItem) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .border(2.dp, MaterialTheme.colors.onSurface)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(PaddingValues(start = DEFAULT_PADDING.dp, top = DEFAULT_PADDING.dp))
        ) {
            Image(
                painter = painterResource(id = item.drawableResource),
                contentDescription = "${item.title} icon",
                modifier = Modifier.weight(1F, fill = true),
                alignment = Alignment.BottomStart
            )

            Image(
                painter = painterResource(id = R.drawable.ic_primary_button),
                contentDescription = stringResource(R.string.content_description_arrow_go)
            )
        }

        Column(
            Modifier.padding(
                PaddingValues(
                    start = DEFAULT_PADDING.dp,
                    end = DEFAULT_PADDING.dp,
                    top = 16.dp,
                    bottom = DEFAULT_PADDING.dp
                )
            )
        ) {
            SubHeading(item.title)
            XSmallSpace()
            ContentText(item.description, fontSize = 14)
        }
    }
}
