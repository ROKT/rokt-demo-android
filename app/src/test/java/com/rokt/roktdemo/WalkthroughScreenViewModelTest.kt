package com.rokt.roktdemo

import com.google.common.truth.Truth.assertThat
import com.rokt.roktdemo.data.library.DemoLibraryRepositoryMockImpl
import com.rokt.roktdemo.ui.demo.walkthrough.screen.WalkthroughScreenViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
            val mockedViewModel = WalkthroughScreenViewModel()
            mockedViewModel.initWithLibrary(
                DemoLibraryRepositoryMockImpl().getDemoLibraryMocked(),
                0
            )
            assertThat(mockedViewModel.state.value.isEmbedded).isEqualTo(true)
        }
    }

    @Test
    fun `isEmbedded is set to true at second index`() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val mockedViewModel = WalkthroughScreenViewModel()
            mockedViewModel.initWithLibrary(
                DemoLibraryRepositoryMockImpl().getDemoLibraryMocked(),
                1
            )
            assertThat(mockedViewModel.state.value.isEmbedded).isEqualTo(true)
        }
    }

    @Test
    fun `isEmbedded is set to false at third index`() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val mockedViewModel = WalkthroughScreenViewModel()
            mockedViewModel.initWithLibrary(
                DemoLibraryRepositoryMockImpl().getDemoLibraryMocked(),
                2
            )
            assertThat(mockedViewModel.state.value.isEmbedded).isEqualTo(false)
        }
    }
}
