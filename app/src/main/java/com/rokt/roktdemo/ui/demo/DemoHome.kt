package com.rokt.roktdemo.ui.demo

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rokt.roktdemo.R
import com.rokt.roktdemo.ui.common.BackButton
import com.rokt.roktdemo.ui.common.ContentText
import com.rokt.roktdemo.ui.common.DefaultSpace
import com.rokt.roktdemo.ui.common.Heading
import com.rokt.roktdemo.ui.common.LargeSpace
import com.rokt.roktdemo.ui.common.MEDIUM_SPACE
import com.rokt.roktdemo.ui.common.SubHeading
import com.rokt.roktdemo.ui.common.XSmallSpace

@Composable
fun DemoHome(
    demoScreenState: DemoScreenState,
    backPressed: () -> Unit,
    actions: DemoActions,
) {
    val scroll = rememberScrollState(0)
    DemoPageSuccess(
        demoPage = demoScreenState,
        backPressed = backPressed,
        scroll = scroll,
        actions = actions
    )
}

@Composable
private fun DemoPageSuccess(
    demoPage: DemoScreenState,
    backPressed: () -> Unit,
    scroll: ScrollState,
    actions: DemoActions,
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface)
    ) {
        Box(Modifier.padding(PaddingValues(start = 3.dp, top = 54.dp))) {
            BackButton(backPressed, MaterialTheme.colors.primaryVariant)
        }

        Column(
            modifier = Modifier
                .verticalScroll(scroll)
                .padding(PaddingValues(MEDIUM_SPACE.dp, 10.dp))
        ) {
            Heading(demoPage.title)
            ContentText(demoPage.description)
            DefaultSpace()
            demoPage.items.forEach {
                DemoListItemView(item = it, actions)
                DefaultSpace()
            }

            LargeSpace()
        }
    }
}

@Composable
private fun DemoListItemView(item: DemoPageListItem, actions: DemoActions) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .border(2.dp, MaterialTheme.colors.onSurface)
            .clickable {
                actions.navigateToSummary(item.navAction)
            }
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(
                    PaddingValues(
                        start = MEDIUM_SPACE.dp,
                        top = MEDIUM_SPACE.dp,
                        end = MEDIUM_SPACE.dp
                    )
                )
        ) {
            Image(
                painter = painterResource(id = item.drawableResource),
                contentDescription = "${item.title} icon",
                modifier = Modifier.weight(1F, fill = true),
                alignment = Alignment.BottomStart
            )

            Image(
                painter = painterResource(id = R.drawable.ic_primary_button),
                contentDescription = stringResource(R.string.content_description_arrow_go),
                modifier = Modifier.clip(CircleShape)
            )
        }

        Column(
            Modifier.padding(
                PaddingValues(
                    start = MEDIUM_SPACE.dp,
                    end = MEDIUM_SPACE.dp,
                    top = 16.dp,
                    bottom = MEDIUM_SPACE.dp
                )
            )
        ) {
            SubHeading(item.title)
            XSmallSpace()
            ContentText(item.description, fontSize = 14, lineHeight = 18)
        }
    }
}
