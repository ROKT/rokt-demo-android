package com.rokt.roktdemo.ui.demo

enum class DestinationType(val value: String) {
    FEATURE_WALKTHROUGH("walkthrough"),
    CUSTOM_CHECKOUT("custom_check"),
    CONFIRMATION_GROUPON("groupon"),
    CONFIRMATION_STUBHUB("stubhub"),
    CONFIRMATION_GUMTREE("gumtree")
}

private const val WALKTHROUGH_DISCLAIMER =
    """As you progress, try interacting with the Rokt placements by tapping "Yes please" or "No thanks". To progress to the next placement type, tap "NEXT" at the top right hand corner of the screen.

No personal and device data will be captured or stored."""

private const val DISCLAIMER_DEFAULT =
    """As you progress, try interacting with the Rokt placements by tapping "Yes please" or "No thanks".

No personal, account, or device data will be captured or stored."""

fun DestinationType.getDisclaimer(): String {
    return when (DestinationType.FEATURE_WALKTHROUGH) {
        this -> {
            WALKTHROUGH_DISCLAIMER
        }
        else -> DISCLAIMER_DEFAULT
    }
}
