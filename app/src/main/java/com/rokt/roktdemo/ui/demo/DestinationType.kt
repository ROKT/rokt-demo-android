package com.rokt.roktdemo.ui.demo

import com.rokt.roktdemo.R

enum class DestinationType(val value: String) {
    FEATURE_WALKTHROUGH("walkthrough"),
    CUSTOM_CHECKOUT("custom_check"),
    CONFIRMATION_GROUPON("groupon"),
    CONFIRMATION_STUBHUB("stubhub"),
    CONFIRMATION_GUMTREE("gumtree")
}

fun DestinationType.getImageResource(): Int {
    return when {
        this == DestinationType.FEATURE_WALKTHROUGH -> {
            R.drawable.ic_feature_walkthrough
        }
        this == DestinationType.CUSTOM_CHECKOUT -> {
            R.drawable.ic_custom_checkout
        }
        this == DestinationType.CONFIRMATION_GUMTREE -> {
            R.drawable.ic_gumtree_logo
        }
        this == DestinationType.CONFIRMATION_GROUPON -> {
            R.drawable.ic_groupon_logo
        }
        this == DestinationType.CONFIRMATION_STUBHUB -> {
            R.drawable.ic_stubhub_logo
        }
        else -> 0
    }
}

private const val WALKTHROUGH_DISCLAIMER =
    """As you progress, try interacting with the Rokt placements by tapping "Yes please" or "No thanks". To progress to the next placement type, tap "NEXT" at the top right hand corner of the screen.

No personal and device data will be captured or stored."""

private const val DISCLAIMER_DEFAULT =
    """As you progress, try interacting with the Rokt placements by tapping "Yes please" or "No thanks".

No personal and device data will be captured or stored."""

fun DestinationType.getDisclaimer(): String {
    return when (DestinationType.FEATURE_WALKTHROUGH) {
        this -> {
            WALKTHROUGH_DISCLAIMER
        }
        else -> DISCLAIMER_DEFAULT
    }
}
