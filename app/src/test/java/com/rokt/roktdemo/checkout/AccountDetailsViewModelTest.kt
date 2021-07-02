package com.rokt.roktdemo.checkout

import com.google.common.truth.Truth
import com.rokt.roktdemo.CoroutineTestRule
import com.rokt.roktdemo.data.library.DemoLibraryRepositoryMockImpl
import com.rokt.roktdemo.data.validate.ValidationState
import com.rokt.roktdemo.data.validate.ValidationStatus
import com.rokt.roktdemo.data.validate.ValidatorRepository
import com.rokt.roktdemo.ui.demo.custom.screen.account.AccountDetailsViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class AccountDetailsViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Test
    fun `validateForm() should set formValidated to true if form is valid`() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val accountDetailsViewModel = getViewModelForStatus(ValidationStatus.VALID)
            accountDetailsViewModel.continueButtonPressed()
            Truth.assertThat(accountDetailsViewModel.state.value.formValidated)
                .isEqualTo(true)
        }
    }

    @Test
    fun `validateForm() should set formValidated to false if form is invalid`() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val accountDetailsViewModel = getViewModelForStatus(ValidationStatus.INVALID)
            accountDetailsViewModel.init(DemoLibraryRepositoryMockImpl().getDemoLibraryMocked().customConfigurationPage.accountDetails)

            accountDetailsViewModel.continueButtonPressed()
            Truth.assertThat(accountDetailsViewModel.state.value.formValidated)
                .isEqualTo(false)
        }
    }

    @Test
    fun `onNavigatedAway() should set formValidated to false`() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val accountDetailsViewModel = getViewModelForStatus(ValidationStatus.VALID)

            accountDetailsViewModel.continueButtonPressed()
            Truth.assertThat(accountDetailsViewModel.state.value.formValidated)
                .isEqualTo(true)
            accountDetailsViewModel.onNavigatedAway()
            Truth.assertThat(accountDetailsViewModel.state.value.formValidated)
                .isEqualTo(false)
        }
    }

    @Test
    fun `onFieldEdited() should set formValidated to false`() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val accountDetailsViewModel = getViewModelForStatus(ValidationStatus.VALID)
            accountDetailsViewModel.continueButtonPressed()
            Truth.assertThat(accountDetailsViewModel.state.value.formValidated)
                .isEqualTo(true)
            accountDetailsViewModel.onFieldEdited()
            Truth.assertThat(accountDetailsViewModel.state.value.formValidated)
                .isEqualTo(false)
        }
    }
}

private fun getViewModelForStatus(validationState: ValidationStatus): AccountDetailsViewModel {
    val validator: ValidatorRepository = mockk()
    val accountDetailsViewModel =
        AccountDetailsViewModel(validator)
    accountDetailsViewModel.init(DemoLibraryRepositoryMockImpl().getDemoLibraryMocked().customConfigurationPage.accountDetails)

    val accountId = DemoLibraryRepositoryMockImpl().TAG_ID
    every { validator.validateAccountId(accountId) } returns ValidationState(
        validationState
    )
    return accountDetailsViewModel
}
