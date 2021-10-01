package com.rokt.roktdemo.ui.demo

import android.accounts.NetworkErrorException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rokt.roktdemo.data.data
import com.rokt.roktdemo.data.exception
import com.rokt.roktdemo.data.library.DemoLibraryRepository
import com.rokt.roktdemo.data.succeeded
import com.rokt.roktdemo.model.DemoLibrary
import com.rokt.roktdemo.ui.demo.summary.SummaryViewModel
import com.rokt.roktdemo.ui.state.RoktDemoErrorTypes
import com.rokt.roktdemo.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DemoViewModel @Inject constructor(private val demoLibraryRepository: DemoLibraryRepository) :
    ViewModel() {
    private val _state: MutableStateFlow<UiState<DemoScreenState>> =
        MutableStateFlow(UiState(loading = true))
    val state: StateFlow<UiState<DemoScreenState>>
        get() = _state

    init {
        viewModelScope.launch {
            getDemoPage()
        }
    }

    private suspend fun getDemoPage() {
        _state.value = UiState(loading = true)
        demoLibraryRepository.getDemoLibrary().collect { result ->
            if (result.succeeded) {
                _state.value = getSuccessState(result.data())
            } else {
                _state.value = UiState(error = result.exception().getErrorType())
            }
        }
    }
}

fun Exception.getErrorType(): RoktDemoErrorTypes {
    return if (this is NetworkErrorException) {
        RoktDemoErrorTypes.NETWORK
    } else {
        RoktDemoErrorTypes.GENERAL
    }
}

fun getSuccessState(library: DemoLibrary): UiState<DemoScreenState> {
    return UiState(
        data = DemoScreenState(
            library.demoTitle,
            library.demoDescription,

            arrayListOf<DemoPageListItem>().apply {
                add(
                    DemoPageListItem(
                        library.defaultPlacementsExamples.title,
                        library.defaultPlacementsExamples.shortDescription,
                        library.defaultPlacementsExamples.iconResource,
                        DestinationType.FEATURE_WALKTHROUGH,
                        SummaryViewModel(library, DestinationType.FEATURE_WALKTHROUGH),
                    )
                )
                add(
                    DemoPageListItem(
                        library.customConfigurationPage.title,
                        library.customConfigurationPage.shortDescription,
                        library.customConfigurationPage.iconResource,
                        DestinationType.CUSTOM_CHECKOUT,
                        SummaryViewModel(library, DestinationType.CUSTOM_CHECKOUT),
                    )
                )
                add(
                    DemoPageListItem(
                        library.preDefinedScreen1.title,
                        library.preDefinedScreen1.shortDescription,
                        library.preDefinedScreen1.iconResource,
                        DestinationType.CONFIRMATION_PREDEFINED1,
                        SummaryViewModel(library, DestinationType.CONFIRMATION_PREDEFINED1)
                    )
                )
                add(
                    DemoPageListItem(
                        library.preDefinedScreen2.title,
                        library.preDefinedScreen2.shortDescription,
                        library.preDefinedScreen2.iconResource,
                        DestinationType.CONFIRMATION_PREDEFINED2,
                        SummaryViewModel(library, DestinationType.CONFIRMATION_PREDEFINED2)
                    )
                )
                add(
                    DemoPageListItem(
                        library.preDefinedScreen3.title,
                        library.preDefinedScreen3.shortDescription,
                        library.preDefinedScreen3.iconResource,
                        DestinationType.CONFIRMATION_PREDEFINED3,
                        SummaryViewModel(library, DestinationType.CONFIRMATION_PREDEFINED3)
                    )
                )
            },
            library = library
        )
    )
}

data class DemoScreenState(
    val title: String = "",
    val description: String = "",
    val items: List<DemoPageListItem> = listOf(),
    val errorString: String? = null,
    val library: DemoLibrary,
)

class DemoPageListItem(
    val title: String,
    val description: String,
    val drawableResource: Int,
    val navAction: DestinationType,
    val summaryViewModel: SummaryViewModel,
)
