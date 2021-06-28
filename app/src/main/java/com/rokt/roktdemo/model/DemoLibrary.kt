package com.rokt.roktdemo.model

import java.util.HashMap

data class DemoLibrary(
    val demoTitle: String,
    val demoDescription: String,
    val defaultPlacementsExamples: DefaultPlacements,
    val customConfigurationPage: CustomConfigurationPage
//    // TODO: Predefined screens
//    val preDefinedScreen1: PredefinedScreen,
//    val preDefinedScreen2: PredefinedScreen,
//    val preDefinedScreen3: PredefinedScreen,
)

data class CustomConfigurationPage(
    val title: String,
    val shortDescription: String,
    val iconUrl: String,
    val longDescription: String,
    val accountDetails: AccountDetails,
    val customerDetails: CustomerDetails,
    val advancedDetails: HashMap<String, String>,
)

data class AccountDetails(
    val accountID: String,
    val viewName: String,
    val placementLocation1: String,
    val placementLocation2: String,
)

class CustomerDetails(
    // TODO: Discuss required attributes
//    val email: String,
//    val firstName: String,
//    val lastName: String,
//    val dateOfBirth: String,
//    val mobile: String,
    val state: String,
    val postcode: String,
    val country: List<String>
)

class DefaultPlacements(
    title: String,
    shortDescription: String,
    val longDescription: String,
    iconUrl: String,
    val tagID: String,
    val screens: List<Screen>,
) : RoktDemoScreen(title, shortDescription, iconUrl)

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
//    val descriptions: List<String>,
//    val tagId: String,
//    val viewName: String,
//    val placeholderName: String,
//    val type: String,
//    val attributes: List<Pair<String, String>>,
    title: String,
    shortDescription: String,
    iconUrl: String,
) : RoktDemoScreen(title, shortDescription, iconUrl)

open class RoktDemoScreen(
    val title: String,
    val shortDescription: String,
    val iconUrl: String,
)
