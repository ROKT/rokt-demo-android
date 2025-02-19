package com.rokt.roktdemo.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.collections.ArrayList
import kotlin.random.Random

fun Uri.openInBrowser(context: Context) {
    val browserIntent = Intent(Intent.ACTION_VIEW, this)
    if (browserIntent.resolveActivity(context.packageManager) != null) {
        androidx.core.content.ContextCompat.startActivity(context, browserIntent, null)
    }
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

fun Random.randomiseEmail(email: String?): String {
    var randomisedEmail = email ?: "john.smith@example.com"
    if (randomisedEmail.contains("@")) {
        val domain = randomisedEmail.substringAfterLast("@")
        val emailId = randomisedEmail.substringBeforeLast("@")
        randomisedEmail = emailId + getFormattedDate() + getRandomNumber() + "@" + domain
    }
    return randomisedEmail
}

private fun getRandomNumber(): String {
    return Random.nextInt(10000).toString()
}

@SuppressLint("SimpleDateFormat")
private fun getFormattedDate(): String {
    val formatter = SimpleDateFormat("yyyyMMDDHHmmSS")
    return formatter.format(Date())
}
