package com.rokt.roktdemo.ui.demo.walkthrough.screen

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rokt.roktdemo.model.DemoLibrary
import com.rokt.roktdemo.model.ScreenType
import com.rokt.roktdemo.ui.demo.RoktExecutor
import com.rokt.roktsdk.Widget
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject
import kotlin.collections.HashMap
import kotlin.random.Random

@HiltViewModel
class WalkthroughScreenViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(WalkthroughScreenState())
    val state: MutableStateFlow<WalkthroughScreenState>
        get() = _state

    private val screenIndex: MutableStateFlow<Int> = MutableStateFlow(0)

    fun initWithLibrary(demoLibrary: DemoLibrary, index: Int) {
        screenIndex.value = index

        viewModelScope.launch {
            screenIndex.collect { screenIndex ->
                val screens = demoLibrary.defaultPlacementsExamples.screens
                screens[screenIndex].let { screen ->

                    _state.value = WalkthroughScreenState(
                        screen.title,
                        screen.description,
                        screen.placeholderName,
                        screen.attributes,
                        screen.viewName,
                        screen.type == ScreenType.Embedded
                    )
                }
            }
        }
    }

    fun onEmbeddedWidgetAddedToView(widget: WeakReference<Widget>) {
        executeRokt(hashMapOf(state.value.placeholderName to widget))
    }

    fun onViewExampleButtonClicked() = executeRokt()

    private fun getAttributes(): HashMap<String, String> {
        val attributes = state.value.attributes
        attributes.put("email", randomiseEmail(attributes.get("email")))
        return attributes
    }

    private fun randomiseEmail(email: String?): String {
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

    private fun executeRokt(placeholders: HashMap<String, WeakReference<Widget>>? = null) {
        RoktExecutor.executeRokt(state.value.viewName, getAttributes(), placeholders)
    }
}

data class WalkthroughScreenState(
    val title: String = "",
    val description: String = "",
    val placeholderName: String = "",
    val attributes: HashMap<String, String> = hashMapOf(),
    val viewName: String = "",
    val isEmbedded: Boolean = false,
)
