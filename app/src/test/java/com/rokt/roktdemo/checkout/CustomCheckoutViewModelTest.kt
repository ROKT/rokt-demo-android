package com.rokt.roktdemo.checkout

import com.google.common.truth.Truth
import com.rokt.roktdemo.CoroutineTestRule
import com.rokt.roktdemo.ui.demo.RoktExecutor
import com.rokt.roktdemo.ui.demo.custom.CustomCheckoutViewModel
import com.rokt.roktsdk.Widget
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.lang.ref.WeakReference
import java.util.HashMap

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class CustomCheckoutViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Test
    fun `onAccountDetailsSubmitted should set account details to the state`() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val customCheckoutViewModel = CustomCheckoutViewModel(mockk())
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

    @Test
    fun `onCustomerDetailsSubmitted should set customer details to the state`() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val customCheckoutViewModel = CustomCheckoutViewModel(mockk())
            customCheckoutViewModel.onCustomerDetailsSubmitted(
                hashMapOf("country" to "Australia", "postcode" to "2323")
            )
            Truth.assertThat(customCheckoutViewModel.state.attributes)
                .containsEntry("country", "Australia")
            Truth.assertThat(customCheckoutViewModel.state.attributes)
                .containsEntry("postcode", "2323")
        }
    }

    @Test
    fun `onEmbeddedWidgetAddedToView should call the execute in the RoktExecutor`() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val roktExecutor: RoktExecutor = mockk()
            val widget = WeakReference(mockk<Widget>(relaxed = true))

            val customCheckoutViewModel = CustomCheckoutViewModel(roktExecutor)
            customCheckoutViewModel.onAccountDetailsSubmitted(
                "123",
                "viewName",
                "Location1",
                "Location2"
            )
            val viewName = "viewName"
            val attributes: HashMap<String, String> = hashMapOf()
            val placeholders = hashMapOf("Location1" to widget)
            every {
                roktExecutor.executeRokt(
                    viewName,
                    attributes,
                    placeholders
                )
            } returns Unit

            // Act
            customCheckoutViewModel.onEmbeddedWidgetAddedToView(widget)

            // Assert
            verify {
                roktExecutor.executeRokt(
                    viewName,
                    attributes,
                    placeholders
                )
            }
        }
    }
}
