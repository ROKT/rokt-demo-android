package com.rokt.roktdemo.checkout

import com.google.common.truth.Truth
import com.rokt.roktdemo.CoroutineTestRule
import com.rokt.roktdemo.data.data
import com.rokt.roktdemo.data.library.DemoLibraryRepositoryMockImpl
import com.rokt.roktdemo.ui.demo.custom.screen.customer.CustomerDetailsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@Ignore
@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class CustomerDetailsViewmodelTest {
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Test
    fun `showAdvancedOptions should be false by default`() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val customerDetailsViewModel = getCustomerDetailsViewModel()

            Truth.assertThat(customerDetailsViewModel.state.value.showAdvancedOptions)
                .isEqualTo(false)
        }
    }

    @Test
    fun `onToggleAdvancedOptions should set showAdvancedOptions to true if its set to false `() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val customerDetailsViewModel = getCustomerDetailsViewModel()

            Truth.assertThat(customerDetailsViewModel.state.value.showAdvancedOptions)
                .isEqualTo(false)

            customerDetailsViewModel.onToggleAdvancedOptions()

            Truth.assertThat(customerDetailsViewModel.state.value.showAdvancedOptions)
                .isEqualTo(true)
        }
    }

    @Test
    fun `onCountrySelected should set selectedCountry to the new value `() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val customerDetailsViewModel = getCustomerDetailsViewModel()

            customerDetailsViewModel.onCountrySelected("Australia")

            Truth.assertThat(customerDetailsViewModel.state.value.selectedCountry)
                .isEqualTo("Australia")
        }
    }

    @Test
    fun `getCustomerDetails should return all user details as a hashmap`() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val customerDetailsViewModel = getCustomerDetailsViewModel()

            customerDetailsViewModel.onCountrySelected("Australia")
            val expectedMap = hashMapOf("country" to "Australia")

            DemoLibraryRepositoryMockImpl().getDemoLibrary().collect {
                it.data().customConfigurationPage.advancedDetails.toList()
                    .forEachIndexed { index, _ ->
                        val newKey = "key$index"
                        val newValue = "value$index"
                        customerDetailsViewModel.onKeyChanged(newKey, index)
                        customerDetailsViewModel.onValueChanged(newValue, index)
                        expectedMap[newKey] = newValue
                    }
            }

            Truth.assertThat(customerDetailsViewModel.getCustomerDetails())
                .isEqualTo(expectedMap)
        }
    }

    @Test
    fun `onKeyChanged should modify the key in advancedOptions to the new key`() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val customerDetailsViewModel = getCustomerDetailsViewModel()

            customerDetailsViewModel.onKeyChanged("newKey", 0)
            Truth.assertThat(customerDetailsViewModel.state.value.advancedOptions[0].key)
                .isEqualTo("newKey")
        }
    }

    @Test
    fun `onValueChanged should modify the key at the given index with a new value`() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val customerDetailsViewModel = getCustomerDetailsViewModel()

            customerDetailsViewModel.onValueChanged("newValue", 0)
            Truth.assertThat(customerDetailsViewModel.state.value.advancedOptions[0].value)
                .isEqualTo("newValue")
        }
    }

    private fun getCustomerDetailsViewModel(): CustomerDetailsViewModel {
        return CustomerDetailsViewModel().apply {
            init(
                DemoLibraryRepositoryMockImpl().getDemoLibraryMocked().customConfigurationPage.customerDetails,
                DemoLibraryRepositoryMockImpl().getDemoLibraryMocked().customConfigurationPage.advancedDetails
            )
        }
    }
}
