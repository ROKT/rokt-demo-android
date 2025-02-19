package com.rokt.roktdemo.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rokt.roktdemo.R
import com.rokt.roktdemo.ui.common.BackButton
import com.rokt.roktdemo.ui.common.ButtonText
import com.rokt.roktdemo.ui.common.Heading

private const val HEADER_TOP_PADDING = 50

@Composable
fun SettingsPage(backPressed: () -> Unit, viewModel: SettingsViewModel = hiltViewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White, RectangleShape)
    ) {
        Header(backPressed)
        val uiState = viewModel.state.collectAsState()
        if (uiState.value.hasData) {
            val data = uiState.value.data!!
            SettingsRow(text = R.string.settings_stage_env, data.stageEnvironment) {
                viewModel.onStageEnvironmentChange(it)
            }
            SettingsRow(text = R.string.settings_debug_logs, data.debugLogsEnabled) {
                viewModel.onDebugLogsChange(it)
            }
        }
    }
}

@Composable
private fun SettingsRow(text: Int, selected: Boolean, onCheckedChange: (value: Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f)
        ) {
            ButtonText(text = stringResource(id = text))
        }
        Switch(checked = selected, onCheckedChange = onCheckedChange)
    }
    Spacer(
        modifier = Modifier
            .height(1.dp)
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
            .background(Color.LightGray)
    )
}

@Composable
private fun Header(backPressed: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(Modifier.padding(PaddingValues(start = 3.dp, top = HEADER_TOP_PADDING.dp))) {
            BackButton(backPressed, MaterialTheme.colors.primaryVariant)
        }
        Column(
            modifier = Modifier
                .padding(30.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Heading(text = stringResource(id = R.string.header_settings))
        }
    }
}
