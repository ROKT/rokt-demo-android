package com.rokt.roktdemo.model

class DemoLibrary(
    val demoTitle: String,
    val demoDescription: String,
    val defaultPlacementsExamples: DefaultPlacements,
    val customCustomConfigurationPage: CustomConfigurationPage,
    // TODO: Predefined screens
    val preDefinedScreen1: PredefinedScreen,
    val preDefinedScreen2: PredefinedScreen,
    val preDefinedScreen3: PredefinedScreen
)

class CustomConfigurationPage(
    title: String,
    shortDescription: String,
    iconUrl: String,
    val longDescription: String,
    val accountDetails: AccountDetails,
    val customerDetails: CustomerDetails,
    val advancedDetails: List<Pair<String, String>>
) : RoktDemoScreen(title, shortDescription, iconUrl)

class AccountDetails(
    val accountId: String,
    val viewName: String,
    val placementLocation1: String,
    val placementLocation2: String
)

class CustomerDetails(
    // TODO: Discuss required attributes
//    val email: String,
//    val firstName: String,
//    val lastName: String,
//    val dateOfBirth: String,
//    val mobile: String,
    val country: String,
    val state: String,
    val postcode: String
)

class DefaultPlacements(
    title: String,
    shortDescription: String,
    val longDescription: String,
    iconUrl: String,
    val tagId: String,
    val screens: List<Screen>,
) : RoktDemoScreen(title, shortDescription, iconUrl)

class Screen(
    val title: String,
    val description: String,
    val viewName: String,
    val placeholderName: String,
    val type: String,
    val attributes: List<Pair<String, String>>
)

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
