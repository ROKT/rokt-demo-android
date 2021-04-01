package com.rokt.roktdemo.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Uri.openInBrowser(context: Context) {
    val browserIntent = Intent(Intent.ACTION_VIEW, this)
    androidx.core.content.ContextCompat.startActivity(context, browserIntent, null)
}
