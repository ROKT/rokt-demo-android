package com.rokt.roktdemo.ui.demo.custom.screen.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import com.rokt.roktdemo.R
import com.rokt.roktdemo.ui.common.ButtonLight
import com.rokt.roktdemo.ui.common.ContentText
import com.rokt.roktdemo.ui.common.MediumSpace
import com.rokt.roktdemo.ui.common.ScreenHeader
import com.rokt.roktdemo.ui.common.SmallSpace
import com.rokt.roktdemo.ui.common.XSmallSpace
import com.rokt.roktdemo.ui.demo.custom.CustomCheckoutViewModel
import com.rokt.roktdemo.ui.demo.custom.screen.common.EditableField
import com.rokt.roktdemo.ui.theme.RoktColors
import com.rokt.roktdemo.ui.theme.RoktColors.ErrorColor
import com.rokt.roktdemo.ui.theme.RoktFonts

@Composable
fun AccountDetailsScreen(
    navigateToNextScreen: () -> Unit,
    parentViewModel: CustomCheckoutViewModel,
) {

    val viewModel: AccountDetailsViewModel = hiltNavGraphViewModel()
    val state = viewModel.state.collectAsState()
    val scroll = rememberScrollState(0)

    if (state.value.formValidated) {
        parentViewModel.onAccountDetailsSubmitted(
            state.value.accountId.text,
            state.value.viewName.text,
            state.value.placementLocation1.text,
            state.value.placementLocation2.text
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
        state.value.accountId,
        state.value.viewName,
        state.value.placementLocation1,
        state.value.placementLocation2,
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
            .padding(24.dp)
            .verticalScroll(scrollState)
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
        TextField(
            stringResource(R.string.label_view_name),
            viewName.text,
            viewName.onValueChanged
        )
        TextField(
            stringResource(R.string.label_location_1),
            placementLocation1.text,
            placementLocation1.onValueChanged
        )
        TextField(
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
        TextField(
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
private fun TextField(
    label: String,
    text: String,
    onValueChange: (String) -> Unit,
    errorText: String = "",
) {
    val borderColor =
        if (errorText.isBlank()) MaterialTheme.colors.onSurface else ErrorColor

    TextField(
        value = text,
        onValueChange = { onValueChange(it) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        label = {
            Text(
                label,
                color = RoktColors.HintTextColor,
                fontFamily = RoktFonts.DefaultFontFamily,
                fontSize = 12.sp
            )
        },
        textStyle = TextStyle(
            color = MaterialTheme.colors.primaryVariant,
            fontSize = 16.sp,
            fontFamily = RoktFonts.DefaultFontFamily
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
            .background(Color.White)
            .border(2.dp, borderColor)
    )
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
