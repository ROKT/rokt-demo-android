package com.rokt.roktdemo.checkout

import com.google.common.truth.Truth
import com.rokt.roktdemo.CoroutineTestRule
import com.rokt.roktdemo.ui.demo.custom.CustomCheckoutViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class CustomCheckoutViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Test
    fun `onAccountDetailsSubmitted() should set account details to the state`() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val customCheckoutViewModel = CustomCheckoutViewModel()
            customCheckoutViewModel.onAccountDetailsSubmitted(
                "123",
                "viewName",
                "Location1",
                "Location2"
            )

            Truth.assertThat(customCheckoutViewModel.state.accountId).isEqualTo("123")
            Truth.assertThat(customCheckoutViewModel.state.viewName).isEqualTo("viewName")
            Truth.assertThat(customCheckoutViewModel.state.placementLocation1)
                .isEqualTo("Location1")
            Truth.assertThat(customCheckoutViewModel.state.placementLocation2)
                .isEqualTo("Location2")
        }
    }
}
