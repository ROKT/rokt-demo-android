package com.rokt.roktdemo.ui.demo

import androidx.lifecycle.ViewModel
import com.rokt.roktdemo.data.library.DemoLibraryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DemoViewModel @Inject constructor(private val demoLibraryRepository: DemoLibraryRepository) :
    ViewModel() {

    fun getDemoPage(): DemoScreenState {
        val library = demoLibraryRepository.getDemoLibrary()
        return DemoScreenState(
            library.demoTitle,
            library.demoDescription,

            arrayListOf<DemoPageListItem>().apply {
                add(
                    DemoPageListItem(
                        library.defaultPlacementsExamples.title,
                        library.defaultPlacementsExamples.shortDescription,
                        DestinationType.FEATURE_WALKTHROUGH.getImageResource(),
                        DestinationType.FEATURE_WALKTHROUGH
                    )
                )
                add(
                    DemoPageListItem(
                        library.customCustomConfigurationPage.title,
                        library.customCustomConfigurationPage.shortDescription,
                        DestinationType.CUSTOM_CHECKOUT.getImageResource(),
                        DestinationType.CUSTOM_CHECKOUT
                    )
                )
                add(
                    DemoPageListItem(
                        library.preDefinedScreen1.title,
                        library.preDefinedScreen1.shortDescription,
                        DestinationType.CONFIRMATION_GROUPON.getImageResource(),
                        DestinationType.CONFIRMATION_GROUPON
                    )
                )
                add(
                    DemoPageListItem(
                        library.preDefinedScreen2.title,
                        library.preDefinedScreen2.shortDescription,
                        DestinationType.CONFIRMATION_STUBHUB.getImageResource(),
                        DestinationType.CONFIRMATION_STUBHUB
                    )
                )
                add(
                    DemoPageListItem(
                        library.preDefinedScreen3.title,
                        library.preDefinedScreen3.shortDescription,
                        DestinationType.CONFIRMATION_GUMTREE.getImageResource(),
                        DestinationType.CONFIRMATION_GUMTREE
                    )
                )
            }
        )
    }
}

data class DemoScreenState(
    val title: String,
    val description: String,
    val items: List<DemoPageListItem>
)

class DemoPageListItem(
    val title: String,
    val description: String,
    val drawableResource: Int,
    val navAction: DestinationType
)
