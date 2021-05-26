package com.rokt.roktdemo

import com.google.common.truth.Truth.assertThat
import com.rokt.roktdemo.data.Result
import com.rokt.roktdemo.data.library.DemoLibraryRepository
import com.rokt.roktdemo.data.library.DemoLibraryRepositoryMockImpl
import com.rokt.roktdemo.ui.demo.walkthrough.screen.WalkthroughScreenViewModel
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
class WalkthroughScreenViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Test
    fun `isEmbedded is set to true at first index`() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val mockedViewModel = createMockedViewModel()
            assertThat(mockedViewModel.state.value.data!!.isEmbedded).isEqualTo(true)
        }
    }

    @Test
    fun `isEmbedded is set to true at second index`() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val mockedViewModel = createMockedViewModel()
            mockedViewModel.setScreenIndex(1)
            assertThat(mockedViewModel.state.value.data!!.isEmbedded).isEqualTo(true)
        }
    }

    @Test
    fun `isEmbedded is set to false at third index`() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val mockedViewModel = createMockedViewModel()
            mockedViewModel.setScreenIndex(2)
            assertThat(mockedViewModel.state.value.data!!.isEmbedded).isEqualTo(false)
        }
    }

    private fun createMockedViewModel(): WalkthroughScreenViewModel {
        val mockedRepository = mockk<DemoLibraryRepository>()
        coEvery { mockedRepository.getDemoLibrary() } returns flowOf(
            Result.Success(
                DemoLibraryRepositoryMockImpl().getDemoLibraryMocked()
            )
        )
        return WalkthroughScreenViewModel(mockedRepository)
    }
}
