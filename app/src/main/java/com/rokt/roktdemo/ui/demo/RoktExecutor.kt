package com.rokt.roktdemo.ui.demo

import com.rokt.roktsdk.Rokt
import com.rokt.roktsdk.Widget
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.ref.WeakReference

object RoktExecutor {
    fun executeRokt(
        viewName: String,
        attributes: HashMap<String, String>?,
        placeholders: HashMap<String, WeakReference<Widget>>?,
        scope: CoroutineScope,
    ) {
        Timber.d("Calling Execute with identifier $viewName, attributes $attributes and placeholders $placeholders")
        scope.launch {
            Rokt
                .selectPlacements(
                    identifier = viewName,
                    attributes = attributes ?: emptyMap(),
                    placeholders = placeholders,
                ).collect { event ->
                    Timber.d("Rokt event: $event for identifier $viewName")
                }
        }
    }
}
