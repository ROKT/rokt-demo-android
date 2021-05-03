package com.rokt.roktdemo

import com.google.common.truth.Truth.assertThat
import com.rokt.roktdemo.data.library.DemoLibraryRepositoryMockImpl
import com.rokt.roktdemo.ui.demo.walkthrough.WalkthroughViewModel
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class WalkthroughViewModelTest {
    val walkthroughViewModel = WalkthroughViewModel(DemoLibraryRepositoryMockImpl())

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
    fun `getCounterText should return index + 1 over screenCount when index is within range`() {
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
    fun `nextButtonPressed should increment index when index is within range`() {
        assertThat(walkthroughViewModel.state.value.currentIndex == 0)
        walkthroughViewModel.nextButtonPressed()
        assertThat(walkthroughViewModel.state.value.currentIndex == 1)
    }

    @Test
    fun `nextButtonPressed should not do anything if index is out of range`() {
        assertThat(walkthroughViewModel.state.value.currentIndex == 0)

        // Next until the end of the list
        for (i in 0 until walkthroughViewModel.state.value.screenCount) {
            walkthroughViewModel.nextButtonPressed()
        }

        assertThat(walkthroughViewModel.state.value.currentIndex == walkthroughViewModel.state.value.screenCount - 1)

        // Index is now out of range
        walkthroughViewModel.nextButtonPressed()
        assertThat(walkthroughViewModel.state.value.currentIndex == walkthroughViewModel.state.value.screenCount - 1)
    }

    @Test
    fun `backButtonPressed should decrement index when index is within range`() {
        assertThat(walkthroughViewModel.state.value.currentIndex == 0)
        walkthroughViewModel.nextButtonPressed()
        assertThat(walkthroughViewModel.state.value.currentIndex == 1)
        walkthroughViewModel.backButtonPressed()
        assertThat(walkthroughViewModel.state.value.currentIndex == 0)
    }

    @Test
    fun `backButtonPressed should not do anything if index is out of range`() {
        assertThat(walkthroughViewModel.state.value.currentIndex == 0)
        walkthroughViewModel.backButtonPressed()
        assertThat(walkthroughViewModel.state.value.currentIndex == 0)
    }
}
