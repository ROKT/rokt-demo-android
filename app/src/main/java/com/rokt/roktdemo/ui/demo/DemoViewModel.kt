package com.rokt.roktdemo.ui.demo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rokt.roktdemo.data.data
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
                val library = result.data()
                _state.value = getSuccessState(library)
            } else {
                _state.value = UiState(error = RoktDemoErrorTypes.GENERAL)
            }
        }
    }
}

fun getSuccessState(library: DemoLibrary): UiState<DemoScreenState> {
    return UiState(data = DemoScreenState(
        library.demoTitle,
        library.demoDescription,

        arrayListOf<DemoPageListItem>().apply {
            add(
                DemoPageListItem(
                    library.defaultPlacementsExamples.title,
                    library.defaultPlacementsExamples.shortDescription,
                    DestinationType.FEATURE_WALKTHROUGH.getImageResource(),
                    DestinationType.FEATURE_WALKTHROUGH,
                    SummaryViewModel(library, DestinationType.FEATURE_WALKTHROUGH)
                )
            )
            add(
                DemoPageListItem(
                    library.customCustomConfigurationPage.title,
                    library.customCustomConfigurationPage.shortDescription,
                    DestinationType.CUSTOM_CHECKOUT.getImageResource(),
                    DestinationType.CUSTOM_CHECKOUT,
                    SummaryViewModel(library, DestinationType.CUSTOM_CHECKOUT)
                )
            )
            add(
                DemoPageListItem(
                    library.preDefinedScreen1.title,
                    library.preDefinedScreen1.shortDescription,
                    DestinationType.CONFIRMATION_GROUPON.getImageResource(),
                    DestinationType.CONFIRMATION_GROUPON,
                    SummaryViewModel(library, DestinationType.CONFIRMATION_GROUPON)
                )
            )
            add(
                DemoPageListItem(
                    library.preDefinedScreen2.title,
                    library.preDefinedScreen2.shortDescription,
                    DestinationType.CONFIRMATION_STUBHUB.getImageResource(),
                    DestinationType.CONFIRMATION_STUBHUB,
                    SummaryViewModel(library, DestinationType.CONFIRMATION_STUBHUB)
                )
            )
            add(
                DemoPageListItem(
                    library.preDefinedScreen3.title,
                    library.preDefinedScreen3.shortDescription,
                    DestinationType.CONFIRMATION_GUMTREE.getImageResource(),
                    DestinationType.CONFIRMATION_GUMTREE,
                    SummaryViewModel(library, DestinationType.CONFIRMATION_GUMTREE)
                )
            )
        }
    )
    )
}

data class DemoScreenState(
    val title: String = "",
    val description: String = "",
    val items: List<DemoPageListItem> = listOf(),
    val errorString: String? = null,
)

class DemoPageListItem(
    val title: String,
    val description: String,
    val drawableResource: Int,
    val navAction: DestinationType,
    val summaryViewModel: SummaryViewModel,
)
