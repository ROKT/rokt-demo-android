package com.rokt.roktdemo.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import java.util.ArrayList

fun Uri.openInBrowser(context: Context) {
    val browserIntent = Intent(Intent.ACTION_VIEW, this)
    androidx.core.content.ContextCompat.startActivity(context, browserIntent, null)
}

fun ArrayList<Pair<String, String>>.updateKeyAtIndex(
    index: Int,
    newKey: String,
): ArrayList<Pair<String, String>> {
    this[index] = newKey to this[index].second
    return this
}

fun ArrayList<Pair<String, String>>.updateValueAtIndex(
    index: Int,
    newValue: String,
): ArrayList<Pair<String, String>> {
    this[index] = this[index].first to newValue
    return this
}
