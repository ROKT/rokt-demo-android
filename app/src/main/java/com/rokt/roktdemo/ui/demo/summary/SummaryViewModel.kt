package com.rokt.roktdemo.ui.demo.summary

import androidx.lifecycle.ViewModel
import com.rokt.roktdemo.model.DemoLibrary
import com.rokt.roktdemo.ui.demo.DestinationType
import com.rokt.roktdemo.ui.demo.DestinationType.CUSTOM_CHECKOUT
import com.rokt.roktdemo.ui.demo.DestinationType.FEATURE_WALKTHROUGH
import com.rokt.roktdemo.ui.demo.getDisclaimer
import com.rokt.roktdemo.ui.demo.getImageResource

class SummaryViewModel constructor(
    demoLibrary: DemoLibrary,
    destinationType: DestinationType,
) : ViewModel() {
    val state = getSummaryPageState(destinationType, demoLibrary)
}

private fun getSummaryPageState(
    destinationType: DestinationType,
    demoLibrary: DemoLibrary,
): SummaryPageState {

    return when (destinationType.value) {
        FEATURE_WALKTHROUGH.value -> {
            val demoLibraryItem =
                demoLibrary.defaultPlacementsExamples
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
                demoLibrary.customCustomConfigurationPage
            SummaryPageState(
                demoLibraryItem.title,
                demoLibraryItem.longDescription,
                destinationType.getDisclaimer(),
                destinationType.getImageResource(),
                demoLibraryItem.accountDetails.accountId
            )
        }
        else -> {
            // TODO: Confirmation pages
            val demoLibraryItem =
                demoLibrary.defaultPlacementsExamples
            SummaryPageState(
                demoLibraryItem.title,
                demoLibraryItem.longDescription,
                destinationType.getDisclaimer(),
                destinationType.getImageResource(),
                demoLibraryItem.tagId
            )
        }
    }
}

data class SummaryPageState(
    val title: String,
    val longDescription: String,
    val disclaimerText: String,
    val drawableResourceId: Int,
    val selectedTagId: String,
)
