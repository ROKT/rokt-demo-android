package com.rokt.roktdemo.ui.demo.custom.screen.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.systemBarsPadding
import com.rokt.roktdemo.R
import com.rokt.roktdemo.ui.common.ButtonLight
import com.rokt.roktdemo.ui.common.ContentText
import com.rokt.roktdemo.ui.common.LoadingPage
import com.rokt.roktdemo.ui.common.MediumSpace
import com.rokt.roktdemo.ui.common.RoktTextField
import com.rokt.roktdemo.ui.common.ScreenHeader
import com.rokt.roktdemo.ui.common.SmallSpace
import com.rokt.roktdemo.ui.common.XSmallSpace
import com.rokt.roktdemo.ui.demo.custom.CustomCheckoutViewModel
import com.rokt.roktdemo.ui.demo.custom.screen.common.EditableField
import com.rokt.roktdemo.ui.demo.error.RoktError
import com.rokt.roktdemo.ui.theme.RoktColors.ErrorColor
import com.rokt.roktdemo.ui.theme.RoktFonts

@Composable
fun AccountDetailsScreen(
    parentViewModel: CustomCheckoutViewModel,
    navigateToNextScreen: () -> Unit,
) {

    val viewModel: AccountDetailsViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState()
    val scroll = rememberScrollState(0)

    when {
        state.value.loading -> {
           LoadingPage()
        }
        state.value.hasData -> {
            AccountDetailsSuccess(
                state.value.data!!,
                scroll,
                parentViewModel,
                viewModel,
                navigateToNextScreen
            )
        }
        else -> {
            RoktError(errorType = state.value.error)
        }
    }
}

@Composable
private fun AccountDetailsSuccess(
    data: AccountDetailsViewState,
    scroll: ScrollState,
    parentViewModel: CustomCheckoutViewModel,
    viewModel: AccountDetailsViewModel,
    navigateToNextScreen: () -> Unit,
) {
    if (data.formValidated) {
        parentViewModel.onAccountDetailsSubmitted(
            data.accountId.text,
            data.viewName.text,
            data.placementLocation1.text,
            data.placementLocation2.text
        )

        viewModel.onNavigatedAway()
        navigateToNextScreen.invoke()
        return
    }

    val onContinueButtonPress = {
        viewModel.continueButtonPressed()
    }

    AccountDetailsScreenContent(
        scroll,
        onContinueButtonPress,
        data.accountId,
        data.viewName,
        data.placementLocation1,
        data.placementLocation2,
    )
}

@Composable
private fun AccountDetailsScreenContent(
    scrollState: ScrollState,
    continueButtonPressed: () -> Unit,
    accountId: EditableField,
    viewName: EditableField,
    placementLocation1: EditableField,
    placementLocation2: EditableField,
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp, bottom = 24.dp)
            .verticalScroll(scrollState)
            .systemBarsPadding()
    ) {
        Text(
            text = stringResource(R.string.account_details_step_count_text),
            fontSize = 16.sp,
            color = MaterialTheme.colors.secondaryVariant
        )
        SmallSpace()
        ScreenHeader(text = stringResource(R.string.label_account_details))
        SmallSpace()
        ContentText(stringResource(id = R.string.text_account_details_description))
        XSmallSpace()
        ErrorTextField(
            stringResource(R.string.label_account_id),
            accountId.text,
            accountId.onValueChanged,
            accountId.errorText
        )
        RoktTextField(
            stringResource(R.string.label_view_name),
            viewName.text,
            viewName.onValueChanged
        )
        RoktTextField(
            stringResource(R.string.label_location_1),
            placementLocation1.text,
            placementLocation1.onValueChanged
        )
        RoktTextField(
            stringResource(R.string.label_location_2),
            placementLocation2.text,
            placementLocation2.onValueChanged
        )
        MediumSpace()
        ButtonLight(text = stringResource(R.string.button_continue)) {
            continueButtonPressed.invoke()
        }
    }
}

@Composable
private fun ErrorTextField(
    label: String,
    text: String,
    onValueChange: (String) -> Unit,
    errorText: String,
) {
    Column(Modifier.fillMaxWidth()) {
        RoktTextField(
            label,
            text,
            onValueChange,
            errorText
        )

        if (errorText.isNotEmpty()) {
            ErrorMessage(errorText)
        }
    }
}

@Composable
fun ErrorMessage(text: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_exclamation_circle),
            contentDescription = stringResource(R.string.content_description_exclamation)
        )
        Text(
            modifier = Modifier.padding(start = 5.dp),
            text = text,
            color = ErrorColor,
            fontFamily = RoktFonts.DefaultFontFamily,
            fontSize = 14.sp
        )
    }
}
