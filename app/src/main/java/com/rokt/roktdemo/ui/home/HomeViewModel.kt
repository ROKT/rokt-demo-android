package com.rokt.roktdemo.ui.home

import android.content.Context
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import com.rokt.roktdemo.R
import com.rokt.roktdemo.utils.openInBrowser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    fun contactUsClicked(context: Context) {
        context.getString(R.string.link_contact_us).toUri().openInBrowser(context = context)
    }
}
