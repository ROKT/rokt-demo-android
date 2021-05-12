package com.rokt.roktdemo.data.library

import com.rokt.roktdemo.model.AccountDetails
import com.rokt.roktdemo.model.CustomConfigurationPage
import com.rokt.roktdemo.model.CustomerDetails
import com.rokt.roktdemo.model.DefaultPlacements
import com.rokt.roktdemo.model.DemoLibrary
import com.rokt.roktdemo.model.PredefinedScreen
import com.rokt.roktdemo.model.Screen
import com.rokt.roktdemo.model.ScreenType

class DemoLibraryRepositoryMockImpl : DemoLibraryRepository {
    val DEMO_LIB_TITLE = "Demo Library"
    val DEMO_LIB_DESC =
        """Discover why Rokt is the easiest and most powerful way to optimize value across both your customer engagement and revenue objectives. 

Explore how our award-winning Machine Learning algorithms power stronger revenue outcomes for our clients by providing a more personalized experience for each customer at scale.
"""

    val DEMO_LIB_IMAGE_URL = ""
    val TAG_ID = "2754655826098840951"

    val SCREEN_NAME_1 = "Embedded Placement (1)"
    val SCREEN_DESC_1 =
        "The below preview shows an embedded placement with brand logos displayed. You can progress through the offers by clicking “Yes Please” or “No Thanks”. \n\nThe Rokt placement powers traffic, email, phone and, app install campaigns. Opting-in to a traffic or app install campaign will direct the customer to a landing page in either an in-app webview, the associated app or app store.\n"

    val SCREEN_NAME_2 = "Embedded Placement (2)"
    val SCREEN_DESC_2 =
        "The below preview shows an embedded placement with brand logos displayed. You can progress through the offers by clicking “Yes Please” or “No Thanks”. \n\nThe Rokt placement powers traffic, email, phone and, app install campaigns. Opting-in to a traffic or app install campaign will direct the customer to a landing page in either an in-app webview, the associated app or app store.\n"

    val SCREEN_DESC_3 =
        "Click “View Example” to preview a Rokt overlay placement. You can progress through the offers by clicking “Yes Please” or “No Thanks”. \n" +
                "\n" +
                "On iOS devices, the overlay placement follows Apple’s best practice automatic presentation style for overlays. \n" +
                "\n" +
                "On Android devices, the overlay placements can be configured as a “full-screen overlay” or a “lightbox overlay” with a transparent background.\n"
    val SCREEN_TYPE_NAME_2 = ""

    val attributes1 = hashMapOf(
        Pair("name", "Roktstar"),
        Pair("lastname", "Smith"),
        Pair("mobile", "(323) 867-5309"),
        Pair("postcode", "90210"),
        Pair("country", "AU"),
        Pair("email", "demotester123@gmail.com"),
        Pair("sandbox", "true")
    )

    val screens = listOf(
        Screen(
            SCREEN_NAME_1,
            SCREEN_DESC_1,
            "testAndroid",
            "Location1",
            ScreenType.Embedded,
            attributes1
        ),
        Screen(
            SCREEN_NAME_2,
            SCREEN_DESC_2,
            "testAndroid",
            "Location1",
            ScreenType.Embedded,
            attributes1
        ),
        Screen(
            "Overlay Placement",
            SCREEN_DESC_3,
            "testAndroidLightbox",
            "Location1",
            ScreenType.Overlay,
            attributes1
        )
    )

    val DEFAULT_PLACEMENT_LONG_DESC =
        "The in-app feature walkthrough highlights the various placement and offer types that are available to native app integrations. \n\nThis includes overlay and embedded placements as well as email, traffic, phone, and app install offers. \n"

    val defaultPlacementExamples = DefaultPlacements(
        "Feature Walkthrough",
        "View various examples of Rokt placements and offers that can be used or combined with your native app integration. ",
        DEFAULT_PLACEMENT_LONG_DESC,
        DEMO_LIB_IMAGE_URL,
        TAG_ID,
        screens
    )

    val CUSTOM_CONFIG_TITLE = "Custom Checkout Flow"
    val CUSTOM_CONFIG_SHORT_DESC =
        "View a specific placement or combination of placements on an unbranded confirmation page via a custom checkout flow with configurable options."

    val CUSTOM_CONFIG_ICON_URL = ""
    val CUSTOM_CONFIG_ACCOUNT_ID = "2754655826098840951"
    val CUSTOM_CONFIG_VIEW_NAME = "testAndroid"
    val CUSTOM_CONFIG_LOC_1 = "Location1"
    val CUSTOM_CONFIG_LOC_2 = "Location2"
    val CUSTOM_CONFIG_LONG_DESC =
        "The Custom Checkout Flow allows you to preview a specific placement from a specific account in an unbranded confirmation page. This allows you to preview the specific UI of this placement and experience the in-app behaviour of the Rokt placement.\n\nNote: In order to preview a specific placement, you will require details about your Rokt account and placement configuration. If you do not have these, please reach out to your Rokt Account Manager.\n"

    val customConfigurationPage = CustomConfigurationPage(
        CUSTOM_CONFIG_TITLE,
        CUSTOM_CONFIG_SHORT_DESC,
        CUSTOM_CONFIG_ICON_URL,
        CUSTOM_CONFIG_LONG_DESC,
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
