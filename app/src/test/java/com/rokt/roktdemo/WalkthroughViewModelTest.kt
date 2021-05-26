package com.rokt.roktdemo

import com.google.common.truth.Truth.assertThat
import com.rokt.roktdemo.data.Result
import com.rokt.roktdemo.data.library.DemoLibraryRepository
import com.rokt.roktdemo.data.library.DemoLibraryRepositoryMockImpl
import com.rokt.roktdemo.ui.demo.walkthrough.WalkthroughViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class WalkthroughViewModelTest {
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private val walkthroughViewModel = WalkthroughViewModel(DemoLibraryRepositoryMockImpl())

    @Test
    fun `getNextButtonText should return 'Next' if index is not the last element in the list`() {
        val text = walkthroughViewModel.getNextButtonText(3, 1)
        assertThat(text).isEqualTo("Next")
    }

    @Test
    fun `getNextButtonText should return 'Quit Demo' if index is last element in the list`() {
        val text = walkthroughViewModel.getNextButtonText(2, 1)
        assertThat(text).isEqualTo("Quit Demo")
    }

    @Test
    fun `getNextButtonText should return an empty string if screen count is less than 1`() {
        val text = walkthroughViewModel.getNextButtonText(0, 0)
        assertThat(text).isEqualTo("")

        val text2 = walkthroughViewModel.getNextButtonText(-1, 0)
        assertThat(text2).isEqualTo("")
    }

    @Test
    fun `getCounterText should return index + 1 over screenCount when index is less than screenCount - 1`() {
        val text = walkthroughViewModel.getCounterText(2, 0)
        assertThat(text).isEqualTo("1/2")

        val text2 = walkthroughViewModel.getCounterText(1, 0)
        assertThat(text2).isEqualTo("1/1")

        val text3 = walkthroughViewModel.getCounterText(4, 2)
        assertThat(text3).isEqualTo("3/4")
    }

    @Test
    fun `getCounterText should return the index over screenCount when index == screenCount`() {
        val text2 = walkthroughViewModel.getCounterText(1, 1)
        assertThat(text2).isEqualTo("1/1")
    }

    @Test
    fun `getCounterText should return an empty String if screenCount is less than 1`() {
        val text1 = walkthroughViewModel.getCounterText(0, 1)
        assertThat(text1).isEqualTo("")

        val text2 = walkthroughViewModel.getCounterText(-1, -1)
        assertThat(text2).isEqualTo("")
    }

    @Test
    fun `nextButtonPressed should increment index when index is less than screenCount - 1`() {
        assertThat(walkthroughViewModel.state.value.data!!.currentIndex == 0)
        walkthroughViewModel.nextButtonPressed()
        assertThat(walkthroughViewModel.state.value.data!!.currentIndex == 1)
    }

    @Test
    fun `nextButtonPressed should not do anything if index is equal to screenCount - 1`() {
        assertThat(walkthroughViewModel.state.value.data!!.currentIndex == 0)

        // Next until the end of the list
        for (i in 0 until walkthroughViewModel.state.value.data!!.screenCount) {
            walkthroughViewModel.nextButtonPressed()
        }

        assertThat(walkthroughViewModel.state.value.data!!.currentIndex == walkthroughViewModel.state.value.data!!.screenCount - 1)

        // Index is now out of range
        walkthroughViewModel.nextButtonPressed()
        assertThat(walkthroughViewModel.state.value.data!!.currentIndex == walkthroughViewModel.state.value.data!!.screenCount - 1)
    }

    @Test
    fun `backButtonPressed should decrement index when index greater than zero`() {
        assertThat(walkthroughViewModel.state.value.data!!.currentIndex == 0)
        walkthroughViewModel.nextButtonPressed()
        assertThat(walkthroughViewModel.state.value.data!!.currentIndex == 1)
        walkthroughViewModel.backButtonPressed()
        assertThat(walkthroughViewModel.state.value.data!!.currentIndex == 0)
    }

    @Test
    fun `backButtonPressed should not do anything if index is zero`() {
        assertThat(walkthroughViewModel.state.value.data!!.currentIndex == 0)
        walkthroughViewModel.backButtonPressed()
        assertThat(walkthroughViewModel.state.value.data!!.currentIndex == 0)
    }

    @Test
    fun `uiState hasError should be set to true if the repository returns an error`() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val mockedRepository = mockk<DemoLibraryRepository>()
            coEvery { mockedRepository.getDemoLibrary() } returns flowOf(Result.Error(Exception("Error")))
            val mockedViewModel = WalkthroughViewModel(mockedRepository)
            assertThat(mockedViewModel.state.value.hasError).isTrue()
        }
    }

    @Test
    fun `uiState hasError should be set to false if the repository returns success`() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val mockedRepository = mockk<DemoLibraryRepository>()
            coEvery { mockedRepository.getDemoLibrary() } returns flowOf(Result.Success(mockk()))
            val mockedViewModel = WalkthroughViewModel(mockedRepository)
            assertThat(mockedViewModel.state.value.hasError).isFalse()
        }
    }

    @Test
    fun `uiState should have data if the repository returns success`() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val mockedRepository = mockk<DemoLibraryRepository>()
            coEvery { mockedRepository.getDemoLibrary() } returns flowOf(
                Result.Success(
                    DemoLibraryRepositoryMockImpl().getDemoLibraryMocked()
                )
            )
            val mockedViewModel = WalkthroughViewModel(mockedRepository)
            assertThat(mockedViewModel.state.value.hasData).isTrue()
        }
    }
}
