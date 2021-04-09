package com.rokt.roktdemo.ui.demo

import androidx.lifecycle.ViewModel
import com.rokt.roktdemo.R
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
                        R.drawable.ic_feature_walkthrough
                    )
                )
                add(
                    DemoPageListItem(
                        library.customCustomConfigurationPage.title,
                        library.customCustomConfigurationPage.shortDescription,
                        R.drawable.ic_custom_checkout

                    )
                )
                add(
                    DemoPageListItem(
                        library.preDefinedScreen1.title,
                        library.preDefinedScreen1.shortDescription,
                        R.drawable.ic_groupon_logo
                    )
                )
                add(
                    DemoPageListItem(
                        library.preDefinedScreen2.title,
                        library.preDefinedScreen2.shortDescription,
                        R.drawable.ic_stubhub_logo
                    )
                )
                add(
                    DemoPageListItem(
                        library.preDefinedScreen3.title,
                        library.preDefinedScreen3.shortDescription,
                        R.drawable.ic_gumtree_logo
                    )
                )
            })
    }
}

data class DemoScreenState(
    val title: String,
    val description: String,
    val items: List<DemoPageListItem>
)

class DemoPageListItem(val title: String, val description: String, val drawableResource: Int)