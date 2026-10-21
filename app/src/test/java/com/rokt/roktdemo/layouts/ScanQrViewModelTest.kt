package com.rokt.roktdemo.layouts

import com.google.common.truth.Truth
import com.rokt.roktdemo.CoroutineTestRule
import com.rokt.roktdemo.runBlockingTest
import com.rokt.roktdemo.ui.demo.RoktExecutor
import com.rokt.roktdemo.ui.layouts.ScanQrViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class ScanQrViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private val failureCallbacks = mutableListOf<() -> Unit>()

    private val roktExecutor: RoktExecutor = mockk {
        every { executeRokt(any(), any(), any(), any(), any()) } answers {
            failureCallbacks.add(arg(4))
        }
    }

    private val previewData =
        """{"tagId":"tag","previewId":"preview","versionId":"version","creativeIds":["creative"],""" +
            """"language":"en","layoutVariantIds":["variant"]}"""

    @Test
    fun `a placement failure keeps the scanner and flags the preview as failed`() {
        coroutineTestRule.runBlockingTest {
            val viewModel = ScanQrViewModel(roktExecutor)
            viewModel.qrCodeScanned(previewData, null)
            viewModel.executePreview(null)

            failureCallbacks.last().invoke()

            Truth.assertThat(viewModel.state.value.hasError).isFalse()
            Truth.assertThat(viewModel.state.value.data?.previewFailed).isTrue()
            Truth.assertThat(viewModel.state.value.data?.scannedData).isNotNull()
        }
    }

    @Test
    fun `a failure from an earlier scan is ignored after a newer scan`() {
        coroutineTestRule.runBlockingTest {
            val viewModel = ScanQrViewModel(roktExecutor)
            viewModel.qrCodeScanned(previewData, null)
            viewModel.executePreview(null)
            val staleFailure = failureCallbacks.last()

            viewModel.qrCodeScanned(previewData, null)
            staleFailure.invoke()

            Truth.assertThat(viewModel.state.value.data?.previewFailed).isFalse()
        }
    }
}
