package com.rokt.roktdemo.ui.demo.summary

import androidx.lifecycle.ViewModel
import com.rokt.roktdemo.data.library.DemoLibraryRepository
import com.rokt.roktdemo.ui.demo.DestinationType
import com.rokt.roktdemo.ui.demo.DestinationType.CUSTOM_CHECKOUT
import com.rokt.roktdemo.ui.demo.DestinationType.FEATURE_WALKTHROUGH
import com.rokt.roktdemo.ui.demo.getDisclaimer
import com.rokt.roktdemo.ui.demo.getImageResource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(private val demoLibraryRepository: DemoLibraryRepository) :
    ViewModel() {

    fun getSummaryPageState(destinationType: DestinationType): SummaryPageState? {
        return when (destinationType.value) {
            FEATURE_WALKTHROUGH.value -> {
                val demoLibraryItem =
                    demoLibraryRepository.getDemoLibrary().defaultPlacementsExamples
                SummaryPageState(
                    demoLibraryItem.title,
                    demoLibraryItem.longDescription,
                    destinationType.getDisclaimer(),
                    destinationType.getImageResource(),
                    demoLibraryItem.tagId
                )
            }
            CUSTOM_CHECKOUT.value -> {
                val demoLibraryItem =
                    demoLibraryRepository.getDemoLibrary().customCustomConfigurationPage
                SummaryPageState(
                    demoLibraryItem.title,
                    demoLibraryItem.longDescription,
                    destinationType.getDisclaimer(),
                    destinationType.getImageResource(),
                    demoLibraryItem.accountDetails.accountId
                )
            }
            else -> null // TODO: Predefined Screens
        }
    }
}

data class SummaryPageState(
    val title: String,
    val longDescription: String,
    val disclaimerText: String,
    val drawableResourceId: Int,
    val selectedTagId: String
)
