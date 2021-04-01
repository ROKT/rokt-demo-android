package com.rokt.roktdemo.ui.about

import android.content.Context
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import com.rokt.roktdemo.data.AboutRoktRepository
import com.rokt.roktdemo.model.AboutRokt
import com.rokt.roktdemo.utils.openInBrowser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor(
    private val aboutRepository: AboutRoktRepository
) : ViewModel() {

    fun getAboutPage(): AboutRokt {
        return aboutRepository.getAboutRokt()
    }

    fun linkClicked(context: Context, url: String) {
        url.toUri().openInBrowser(context = context)
    }
}