package com.rokt.roktdemo.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.rokt.roktsdk.Widget
import java.lang.ref.WeakReference

@Composable
fun RoktEmbeddedWidget(onWidgetAdded: (WeakReference<Widget>) -> Unit) {
    AndroidView(
        modifier = Modifier
            .fillMaxWidth(),
        factory = { context ->
            Widget(context).apply {
                onWidgetAdded(WeakReference(this))
            }
        }
    )
}
