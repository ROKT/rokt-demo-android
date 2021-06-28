package com.rokt.roktdemo.checkout

import com.google.common.truth.Truth
import com.rokt.roktdemo.CoroutineTestRule
import com.rokt.roktdemo.data.Result
import com.rokt.roktdemo.data.data
import com.rokt.roktdemo.data.library.DemoLibraryRepository
import com.rokt.roktdemo.data.library.DemoLibraryRepositoryMockImpl
import com.rokt.roktdemo.ui.demo.custom.screen.customer.CustomerDetailsViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class CustomerDetailsViewmodelTest {
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Test
    fun `showAdvancedOptions should be false by default`() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val customerDetailsViewModel = CustomerDetailsViewModel(DemoLibraryRepositoryMockImpl())

            Truth.assertThat(customerDetailsViewModel.state.value.data!!.showAdvancedOptions)
                .isEqualTo(false)
        }
    }

    @Test
    fun `onToggleAdvancedOptions should set showAdvancedOptions to true if its set to false `() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val customerDetailsViewModel = CustomerDetailsViewModel(DemoLibraryRepositoryMockImpl())

            Truth.assertThat(customerDetailsViewModel.state.value.data!!.showAdvancedOptions)
                .isEqualTo(false)

            customerDetailsViewModel.onToggleAdvancedOptions()

            Truth.assertThat(customerDetailsViewModel.state.value.data!!.showAdvancedOptions)
                .isEqualTo(true)
        }
    }

    @Test
    fun `onCountrySelected should set selectedCountry to the new value `() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val customerDetailsViewModel = CustomerDetailsViewModel(DemoLibraryRepositoryMockImpl())

            customerDetailsViewModel.onCountrySelected("Australia")

            Truth.assertThat(customerDetailsViewModel.state.value.data!!.selectedCountry)
                .isEqualTo("Australia")
        }
    }

    @Test
    fun `getCustomerDetails should return all user details as a hashmap`() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val repositoryMockImpl: DemoLibraryRepository = mockk()
            coEvery { repositoryMockImpl.getDemoLibrary() } returns flowOf(
                Result.Success(
                    DemoLibraryRepositoryMockImpl().getDemoLibraryMocked()
                )
            )

            val customerDetailsViewModel = CustomerDetailsViewModel(repositoryMockImpl)

            customerDetailsViewModel.onCountrySelected("Australia")
            val expectedMap = hashMapOf("country" to "Australia")

            repositoryMockImpl.getDemoLibrary().collect {
                it.data().customConfigurationPage.advancedDetails.toList()
                    .forEachIndexed { index, pair ->
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
            val repositoryMockImpl = DemoLibraryRepositoryMockImpl()
            val customerDetailsViewModel = CustomerDetailsViewModel(repositoryMockImpl)

            customerDetailsViewModel.onKeyChanged("newKey", 0)
            Truth.assertThat(customerDetailsViewModel.state.value.data!!.advancedOptions.get(0).key)
                .isEqualTo("newKey")
        }
    }

    @Test
    fun `onValueChanged should modify the key at the given index with a new value`() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val repositoryMockImpl = DemoLibraryRepositoryMockImpl()
            val customerDetailsViewModel = CustomerDetailsViewModel(repositoryMockImpl)

            customerDetailsViewModel.onValueChanged("newValue", 0)
            Truth.assertThat(customerDetailsViewModel.state.value.data!!.advancedOptions.get(0).value)
                .isEqualTo("newValue")
        }
    }
}
