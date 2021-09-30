package com.rokt.roktdemo.model

import com.rokt.roktdemo.utils.ImageSource
import java.util.HashMap

data class DemoLibrary(
    val demoTitle: String,
    val demoDescription: String,
    val defaultPlacementsExamples: DefaultPlacements,
    val customConfigurationPage: CustomConfigurationPage,
    val preDefinedScreen1: PredefinedScreen,
    val preDefinedScreen2: PredefinedScreen,
    val preDefinedScreen3: PredefinedScreen
)

data class CustomConfigurationPage(
    val title: String,
    val shortDescription: String,
    private val iconURL: String,
    val longDescription: String,
    val accountDetails: AccountDetails,
    val customerDetails: CustomerDetails,
    val advancedDetails: HashMap<String, String>
) {
    val iconResource: Int
        get() = ImageSource.getLogo(iconURL)
}

data class AccountDetails(
    val accountID: String,
    val viewName: String,
    val placementLocation1: String,
    val placementLocation2: String,
    val password: String
)

class CustomerDetails(
    val state: String,
    val postcode: String,
    val country: List<String>
)

class DefaultPlacements(
    title: String,
    shortDescription: String,
    val longDescription: String,
    iconURL: String,
    val tagID: String,
    val screens: List<Screen>,
) : RoktDemoScreen(title, shortDescription, iconURL)

class Screen(
    val title: String,
    val description: String,
    val viewName: String,
    val placeholderName: String,
    val type: ScreenType,
    val attributes: HashMap<String, String>,
)

enum class ScreenType {
    Overlay, Embedded
}

open class PredefinedScreen(
    title: String,
    shortDescription: String,
    iconURL: String,
    val descriptions: List<DescriptionItem>,
    val tagID: String,
    val viewName: String,
    val placeholderName: String,
    val type: String,
    val attributes: HashMap<String, String>,
    val isBranded: Boolean,
) : RoktDemoScreen(title, shortDescription, iconURL)

class DescriptionItem(
    val title: String,
    val text: String,
    private val iconUrl: String
) {
    val iconResource: Int
        get() = ImageSource.getIcon(iconUrl)
}

open class RoktDemoScreen(
    val title: String,
    val shortDescription: String,
    private val iconURL: String
) {
    val iconResource: Int
        get() = ImageSource.getLogo(iconURL)
}
