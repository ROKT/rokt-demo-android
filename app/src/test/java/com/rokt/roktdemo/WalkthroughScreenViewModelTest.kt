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
    fun `state isEmbedded should be null until screenIndex is set`() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val walkthroughViewModel = WalkthroughScreenViewModel(DemoLibraryRepositoryMockImpl())
            assertThat(walkthroughViewModel.state.value.isEmbedded).isEqualTo(null)
            walkthroughViewModel.setScreenIndex(0)
            assertThat(walkthroughViewModel.state.value.isEmbedded).isEqualTo(true)
        }
    }
}
