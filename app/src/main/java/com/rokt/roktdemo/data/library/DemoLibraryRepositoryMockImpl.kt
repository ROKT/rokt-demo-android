package com.rokt.roktdemo.data.library

import com.rokt.roktdemo.model.AccountDetails
import com.rokt.roktdemo.model.CustomConfigurationPage
import com.rokt.roktdemo.model.CustomerDetails
import com.rokt.roktdemo.model.DefaultPlacements
import com.rokt.roktdemo.model.DemoLibrary
import com.rokt.roktdemo.model.PredefinedScreen
import com.rokt.roktdemo.model.Screen

class DemoLibraryRepositoryMockImpl : DemoLibraryRepository {
    val DEMO_LIB_TITLE = "Demo Library"
    val DEMO_LIB_DESC =
        """Discover why Rokt is the easiest and most powerful way to optimize value across both your customer engagement and revenue objectives. 

Explore how our award-winning Machine Learning algorithms power stronger revenue outcomes for our clients by providing a more personalized experience for each customer at scale.
"""

    val DEMO_LIB_IMAGE_URL = ""
    val TAG_ID = ""

    val SCREEN_NAME_1 = "Embedded Placement (1)"
    val SCREEN_DESC_1 =
        "The below preview shows an embedded placement with brand logos displayed. You can progress through the offers by clicking “Yes Please” or “No Thanks”. \n\nThe Rokt placement powers traffic, email, phone and, app install campaigns. Opting-in to a traffic or app install campaign will direct the customer to a landing page in either an in-app webview, the associated app or app store.\n"

    val SCREEN_VIEW_NAME_1 = ""
    val SCREEN_PLACEHOLDER_NAME_1 = ""
    val SCREEN_TYPE_NAME_1 = ""

    val SCREEN_NAME_2 = "Embedded Placement (2)"
    val SCREEN_DESC_2 =
        "The below preview shows an embedded placement with brand logos displayed. You can progress through the offers by clicking “Yes Please” or “No Thanks”. \n\nThe Rokt placement powers traffic, email, phone and, app install campaigns. Opting-in to a traffic or app install campaign will direct the customer to a landing page in either an in-app webview, the associated app or app store.\n"

    val SCREEN_VIEW_NAME_2 = ""
    val SCREEN_PLACEHOLDER_NAME_2 = ""
    val SCREEN_TYPE_NAME_2 = ""

    val attributes1 = listOf(Pair("first", "jenny"), Pair("email", "jenny@email.com"))
    val screens = listOf(
        Screen(
            SCREEN_NAME_1,
            SCREEN_DESC_1,
            SCREEN_VIEW_NAME_1,
            SCREEN_DESC_1,
            SCREEN_TYPE_NAME_1,
            attributes1
        ),
        Screen(
            SCREEN_NAME_2,
            SCREEN_DESC_2,
            SCREEN_VIEW_NAME_2,
            SCREEN_DESC_2,
            SCREEN_TYPE_NAME_2,
            attributes1
        )

    )

    val defaultPlacementExamples = DefaultPlacements(
        "Feature Walkthrough",
        "View various examples of Rokt placements and offers that can be used or combined with your native app integration. ",
        DEMO_LIB_IMAGE_URL,
        TAG_ID,
        screens
    )

    val CUSTOM_CONFIG_TITLE = "Custom Checkout Flow"
    val CUSTOM_CONFIG_SHORT_DESC =
        "View a specific placement or combination of placements on an unbranded confirmation page via a custom checkout flow with configurable options."

    val CUSTOM_CONFIG_ICON_URL = ""
    val CUSTOM_CONFIG_ACCOUNT_ID = ""
    val CUSTOM_CONFIG_VIEW_NAME = "RoktExperience"
    val CUSTOM_CONFIG_LOC_1 = ""
    val CUSTOM_CONFIG_LOC_2 = ""

    val customConfigurationPage = CustomConfigurationPage(
        CUSTOM_CONFIG_TITLE,
        CUSTOM_CONFIG_SHORT_DESC,
        CUSTOM_CONFIG_ICON_URL,
        AccountDetails(
            CUSTOM_CONFIG_ACCOUNT_ID,
            CUSTOM_CONFIG_VIEW_NAME,
            CUSTOM_CONFIG_LOC_1,
            CUSTOM_CONFIG_LOC_2
        ),
        CustomerDetails("AU", "NSW", "2000"),
        listOf(Pair("experience", "true"), Pair("majorCat", "true"))
    )

    val PREDEFINED_TITLE_1 = "Confirmation Page"
    val PREDEFINED_DESC_SHORT_1 =
        "View a demonstration of how Groupon has integrated in-app Rokt technology into their post-purchase confirmation page. "

    val PREDEFINED_TITLE_2 = "Confirmation Page"
    val PREDEFINED_DESC_SHORT_2 =
        "View a demonstration of how Stubhub has integrated in-app Rokt technology into their post-purchase confirmation page."

    val PREDEFINED_TITLE_3 = "Post Listing Page"
    val PREDEFINED_DESC_SHORT_3 =
        "View a demonstration of how Gumtree has integrated in-app Rokt technology into their post-listing confirmation page. \n"

    override fun getDemoLibrary(): DemoLibrary {
        return DemoLibrary(
            DEMO_LIB_TITLE, DEMO_LIB_DESC,
            defaultPlacementExamples,
            customConfigurationPage,
            PredefinedScreen(PREDEFINED_TITLE_1, PREDEFINED_DESC_SHORT_1, ""),
            PredefinedScreen(PREDEFINED_TITLE_1, PREDEFINED_DESC_SHORT_2, ""),
            PredefinedScreen(PREDEFINED_TITLE_1, PREDEFINED_DESC_SHORT_3, "")
        )
    }
}
