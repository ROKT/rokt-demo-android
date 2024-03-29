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
import org.junit.Ignore
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
    @Ignore
    fun `validateForm() should set formValidated to true if form is valid`() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val accountDetailsViewModel =
                getViewModelForStatus(ValidationStatus.VALID, ValidationStatus.VALID)
            accountDetailsViewModel.continueButtonPressed()
            Truth.assertThat(accountDetailsViewModel.state.value.formValidated)
                .isEqualTo(true)
        }
    }

    @Test
    fun `validateForm() should set formValidated to false if form is invalid`() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val accountDetailsViewModel =
                getViewModelForStatus(ValidationStatus.INVALID, ValidationStatus.INVALID)
            accountDetailsViewModel.init(DemoLibraryRepositoryMockImpl().getDemoLibraryMocked().customConfigurationPage.accountDetails)

            accountDetailsViewModel.continueButtonPressed()
            Truth.assertThat(accountDetailsViewModel.state.value.formValidated)
                .isEqualTo(false)
        }
    }

    @Test
    fun `validateForm() should set formValidated to false if account is invalid`() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val accountDetailsViewModel =
                getViewModelForStatus(ValidationStatus.INVALID, ValidationStatus.VALID)
            accountDetailsViewModel.init(DemoLibraryRepositoryMockImpl().getDemoLibraryMocked().customConfigurationPage.accountDetails)

            accountDetailsViewModel.continueButtonPressed()
            Truth.assertThat(accountDetailsViewModel.state.value.formValidated)
                .isEqualTo(false)
        }
    }

    @Test
    fun `validateForm() should set formValidated to false if password is invalid`() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val accountDetailsViewModel =
                getViewModelForStatus(ValidationStatus.VALID, ValidationStatus.INVALID)
            accountDetailsViewModel.init(DemoLibraryRepositoryMockImpl().getDemoLibraryMocked().customConfigurationPage.accountDetails)

            accountDetailsViewModel.continueButtonPressed()
            Truth.assertThat(accountDetailsViewModel.state.value.formValidated)
                .isEqualTo(false)
        }
    }

    @Test
    @Ignore
    fun `onNavigatedAway() should set formValidated to false`() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val accountDetailsViewModel =
                getViewModelForStatus(ValidationStatus.VALID, ValidationStatus.VALID)

            accountDetailsViewModel.continueButtonPressed()
            Truth.assertThat(accountDetailsViewModel.state.value.formValidated)
                .isEqualTo(true)
            accountDetailsViewModel.onNavigatedAway()
            Truth.assertThat(accountDetailsViewModel.state.value.formValidated)
                .isEqualTo(false)
        }
    }

    @Test
    @Ignore
    fun `onFieldEdited() should set formValidated to false`() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val accountDetailsViewModel =
                getViewModelForStatus(ValidationStatus.VALID, ValidationStatus.VALID)
            accountDetailsViewModel.continueButtonPressed()
            Truth.assertThat(accountDetailsViewModel.state.value.formValidated)
                .isEqualTo(true)
            accountDetailsViewModel.onAccountFieldEdited()
            Truth.assertThat(accountDetailsViewModel.state.value.formValidated)
                .isEqualTo(false)
        }
    }
}

private fun getViewModelForStatus(
    accountValidationState: ValidationStatus,
    passwordValidationState: ValidationStatus
): AccountDetailsViewModel {
    val validator: ValidatorRepository = mockk()
    val accountDetailsViewModel =
        AccountDetailsViewModel(validator)
    accountDetailsViewModel.init(DemoLibraryRepositoryMockImpl().getDemoLibraryMocked().customConfigurationPage.accountDetails)

    val accountId = DemoLibraryRepositoryMockImpl().TAG_ID
    every { validator.validateAccountId(accountId) } returns ValidationState(
        accountValidationState
    )
    val password = DemoLibraryRepositoryMockImpl().PASSWORD
    every { validator.validatePassword(password, "") } returns ValidationState(
        passwordValidationState
    )
    return accountDetailsViewModel
}
