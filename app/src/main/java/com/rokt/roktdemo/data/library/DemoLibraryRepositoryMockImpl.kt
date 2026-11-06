package com.rokt.roktdemo.data.library

import androidx.annotation.VisibleForTesting
import com.rokt.roktdemo.data.Result
import com.rokt.roktdemo.model.AccountDetails
import com.rokt.roktdemo.model.CustomConfigurationPage
import com.rokt.roktdemo.model.CustomerDetails
import com.rokt.roktdemo.model.DefaultPlacements
import com.rokt.roktdemo.model.DemoLibrary
import com.rokt.roktdemo.model.DescriptionItem
import com.rokt.roktdemo.model.PredefinedScreen
import com.rokt.roktdemo.model.Screen
import com.rokt.roktdemo.model.ScreenType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DemoLibraryRepositoryMockImpl : DemoLibraryRepository {
    val demoLibTitle = "Demo Library"
    val demoLibDesc =
        """Discover why Rokt is the easiest and most powerful way to optimize value across both your customer engagement and revenue objectives. 

Explore how our award-winning Machine Learning algorithms power stronger revenue outcomes for our clients by providing a more personalized experience for each customer at scale.
"""

    val demoLibImageUrl = ""
    val tagId = "2754655826098840951"

    val screenName1 = "Embedded Placement (1)"
    val screenDesc1 =
        "The below preview shows an embedded placement with brand logos displayed. You can progress through the offers by clicking “Yes Please” or “No Thanks”. \n\nThe Rokt placement powers traffic, email, phone and, app install campaigns. Opting-in to a traffic or app install campaign will direct the customer to a landing page in either an in-app webview, the associated app or app store.\n"

    val screenName2 = "Embedded Placement (2)"
    val screenDesc2 =
        "The below preview shows an embedded placement with brand logos displayed. You can progress through the offers by clicking “Yes Please” or “No Thanks”. \n\nThe Rokt placement powers traffic, email, phone and, app install campaigns. Opting-in to a traffic or app install campaign will direct the customer to a landing page in either an in-app webview, the associated app or app store.\n"

    val screenDesc3 =
        "Click “View Example” to preview a Rokt overlay placement. You can progress through the offers by clicking “Yes Please” or “No Thanks”. \n\nOn iOS devices, the overlay placement follows Apple’s best practice automatic presentation style for overlays. \n\nOn Android devices, the overlay placements can be configured as a “full-screen overlay” or a “lightbox overlay” with a transparent background.\n"

    val attributes1 =
        hashMapOf(
            Pair("name", "Roktstar"),
            Pair("lastname", "Smith"),
            Pair("mobile", "(323) 867-5309"),
            Pair("postcode", "90210"),
            Pair("country", "AU"),
            Pair("email", "demotester123@gmail.com"),
            Pair("sandbox", "true"),
        )

    val screens =
        listOf(
            Screen(
                screenName1,
                screenDesc1,
                "testAndroid",
                "Location1",
                ScreenType.Embedded,
                attributes1,
            ),
            Screen(
                screenName2,
                screenDesc2,
                "testAndroid",
                "Location1",
                ScreenType.Embedded,
                attributes1,
            ),
            Screen(
                "Overlay Placement",
                screenDesc3,
                "testAndroidLightbox",
                "Location1",
                ScreenType.Overlay,
                attributes1,
            ),
        )

    val defaultPlacementLongDesc =
        "The in-app feature walkthrough highlights the various placement and offer types that are available to native app integrations. \n\nThis includes overlay and embedded placements as well as email, traffic, phone, and app install offers. \n"

    val defaultPlacementExamples =
        DefaultPlacements(
            "Feature Walkthrough",
            "View various examples of Rokt placements and offers that can be used or combined with your native app integration. ",
            defaultPlacementLongDesc,
            demoLibImageUrl,
            tagId,
            screens,
        )

    val customConfigTitle = "Custom Checkout Flow"
    val customConfigShortDesc =
        "View a specific placement or combination of placements on an unbranded confirmation page via a custom checkout flow with configurable options."

    val customConfigIconUrl = ""
    val customConfigAccountId = "2754655826098840951"
    val customConfigViewName = "testAndroid"
    val customConfigLoc1 = "Location1"
    val customConfigLoc2 = "Location2"
    val customConfigLongDesc =
        "The Custom Checkout Flow allows you to preview a specific placement from a specific account in an unbranded confirmation page. This allows you to preview the specific UI of this placement and experience the in-app behaviour of the Rokt placement.\n\nNote: In order to preview a specific placement, you will require details about your Rokt account and placement configuration. If you do not have these, please reach out to your Rokt Account Manager.\n"

    val customConfigurationPage =
        CustomConfigurationPage(
            customConfigTitle,
            customConfigShortDesc,
            customConfigIconUrl,
            customConfigLongDesc,
            AccountDetails(
                customConfigAccountId,
                "testTwoEmbedded",
                customConfigLoc1,
                customConfigLoc2,
            ),
            CustomerDetails("NSW", "2000", country = listOf("AU", "UK", "US")),
            hashMapOf(
                "experience" to "true",
                "majorCat" to "true",
                "email" to "testingEmail@testing.com",
            ),
        )

    val predefinedTitle1 = "Confirmation Page"
    val predefinedDescShort1 = "View a demonstration of how Groupon has integrated in-app Rokt technology into their post-purchase confirmation page. "

    val predefinedTitle2 = "Confirmation Page"
    val predefinedDescShort2 =
        "View a demonstration of how Stubhub has integrated in-app Rokt technology into their post-purchase confirmation page."

    val predefinedTitle3 = "Post Listing Page"
    val predefinedDescShort3 =
        "View a demonstration of how Gumtree has integrated in-app Rokt technology into their post-listing confirmation page. \n"

    val predefinedDescriptions =
        listOf(
            DescriptionItem(
                "Vertical",
                "Retail - Deals and Group Buying",
                "VerticalIcon",
            ),
        )

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getDemoLibraryMocked(): DemoLibrary = DemoLibrary(
        demoLibTitle,
        demoLibDesc,
        defaultPlacementExamples,
        customConfigurationPage,
        PredefinedScreen(
            predefinedTitle1,
            predefinedDescShort1,
            "",
            predefinedDescriptions,
            "",
            "",
            "",
            "",
            attributes1,
            false,
        ),
        PredefinedScreen(
            predefinedTitle2,
            predefinedDescShort2,
            "",
            predefinedDescriptions,
            "",
            "",
            "",
            "",
            attributes1,
            false,
        ),
        PredefinedScreen(
            predefinedTitle3,
            predefinedDescShort3,
            "",
            predefinedDescriptions,
            "",
            "",
            "",
            "",
            attributes1,
            false,
        ),
    )

    override suspend fun getDemoLibrary(): Flow<Result<DemoLibrary>> = flow {
        emit(Result.Success(getDemoLibraryMocked()))
    }
}
