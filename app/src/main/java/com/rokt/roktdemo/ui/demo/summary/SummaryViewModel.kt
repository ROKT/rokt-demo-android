package com.rokt.roktdemo.ui.demo.summary

import androidx.lifecycle.ViewModel
import com.rokt.roktdemo.model.DemoLibrary
import com.rokt.roktdemo.model.DescriptionItem
import com.rokt.roktdemo.ui.demo.DestinationType
import com.rokt.roktdemo.ui.demo.DestinationType.CONFIRMATION_GROUPON
import com.rokt.roktdemo.ui.demo.DestinationType.CONFIRMATION_GUMTREE
import com.rokt.roktdemo.ui.demo.DestinationType.CONFIRMATION_STUBHUB
import com.rokt.roktdemo.ui.demo.DestinationType.CUSTOM_CHECKOUT
import com.rokt.roktdemo.ui.demo.DestinationType.FEATURE_WALKTHROUGH
import com.rokt.roktdemo.ui.demo.getDisclaimer

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
                demoLibraryItem.iconResource,
                demoLibraryItem.tagID,
                null
            )
        }
        CUSTOM_CHECKOUT.value -> {
            val demoLibraryItem =
                demoLibrary.customConfigurationPage
            SummaryPageState(
                demoLibraryItem.title,
                demoLibraryItem.longDescription,
                destinationType.getDisclaimer(),
                demoLibraryItem.iconResource,
                demoLibraryItem.accountDetails.accountID,
                null
            )
        }
        CONFIRMATION_GROUPON.value -> {
            val demoLibraryItem =
                demoLibrary.preDefinedScreen1
            SummaryPageState(
                demoLibraryItem.title,
                "",
                destinationType.getDisclaimer(),
                demoLibraryItem.iconResource,
                demoLibraryItem.tagID,
                demoLibraryItem.descriptions
            )
        }
        CONFIRMATION_STUBHUB.value -> {
            val demoLibraryItem =
                demoLibrary.preDefinedScreen2
            SummaryPageState(
                demoLibraryItem.title,
                "",
                destinationType.getDisclaimer(),
                demoLibraryItem.iconResource,
                demoLibraryItem.tagID,
                demoLibraryItem.descriptions
            )
        }
        CONFIRMATION_GUMTREE.value -> {
            val demoLibraryItem =
                demoLibrary.preDefinedScreen3
            SummaryPageState(
                demoLibraryItem.title,
                "",
                destinationType.getDisclaimer(),
                demoLibraryItem.iconResource,
                demoLibraryItem.tagID,
                demoLibraryItem.descriptions
            )
        }
        else -> {
            val demoLibraryItem =
                demoLibrary.defaultPlacementsExamples
            SummaryPageState(
                demoLibraryItem.title,
                demoLibraryItem.longDescription,
                destinationType.getDisclaimer(),
                demoLibraryItem.iconResource,
                demoLibraryItem.tagID,
                null
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
    val descriptions: List<DescriptionItem>?
)
