package com.rokt.roktdemo.utils

import com.rokt.roktdemo.R

class ImageSource {
    companion object {
        fun getLogo(name: String): Int {
            return when (name) {
                "FeatureWalkthrough" -> {
                    R.drawable.ic_feature_walkthrough
                }
                "CustomerCheckout" -> {
                    R.drawable.ic_custom_checkout
                }
                "WaveroomSupplyLogo" -> {
                    R.drawable.ic_waveroom_supply_logo
                }
                "HeatedLogo" -> {
                    R.drawable.ic_heated_logo
                }
                "StylusCommerceLogo" -> {
                    R.drawable.ic_stylus_commerce_logo
                }
                "GrouponLogo" -> {
                    R.drawable.ic_groupon_logo
                }
                "StubhubLogo" -> {
                    R.drawable.ic_stubhub_logo
                }
                "GumtreeLogo" -> {
                    R.drawable.ic_gumtree_logo
                }
                else -> {
                    0
                }
            }
        }

        fun getIcon(name: String): Int {
            return when (name) {
                "VerticalIcon" -> {
                    R.drawable.ic_description_vertical
                }
                "MarketingIcon" -> {
                    R.drawable.ic_description_marketing
                }
                "SolutionsIcon" -> {
                    R.drawable.ic_description_solutions
                }
                "PlacementTypeIcon" -> {
                    R.drawable.ic_description_placement_type
                }
                else -> {
                    0
                }
            }
        }
    }
}
