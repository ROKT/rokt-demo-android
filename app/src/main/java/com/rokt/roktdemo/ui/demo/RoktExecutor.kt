package com.rokt.roktdemo.ui.demo

import com.rokt.roktsdk.Rokt
import com.rokt.roktsdk.Widget
import timber.log.Timber
import java.lang.ref.WeakReference

object RoktExecutor {
    fun executeRokt(
        viewName: String,
        attributes: HashMap<String, String>?,
        placeholders: HashMap<String, WeakReference<Widget>>?,
    ) {

        Timber.d("Calling Execute with viewName $viewName, attributes $attributes and placeholders $placeholders")
        Rokt.execute(
            viewName,
            attributes,
            placeholders = placeholders,
            callback = object : Rokt.RoktCallback {
                override fun onLoad() {
                    Timber.d(
                        "%s%s",
                        "Widget loaded for viewName $viewName with attributes ",
                        attributes.toString()
                    )
                }

                override fun onShouldHideLoadingIndicator() {
                }

                override fun onShouldShowLoadingIndicator() {
                }

                override fun onUnload(reason: Rokt.UnloadReasons) {
                    Timber.d("Could not load widget. Reason %s", reason)
                }
            }
        )
    }
}
