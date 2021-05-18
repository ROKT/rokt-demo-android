package com.rokt.roktdemo.utils

import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.ArrayList

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class ExtensionsTest {

    @Test
    fun `updateKeyAtIndex should update the key at given index`() {
        val sampleList: ArrayList<Pair<String, String>> =
            arrayListOf(("key" to "val"), ("key2" to "val2"))
        val newList = sampleList.updateKeyAtIndex(0, "newKey")
        Truth.assertThat(newList[0].first).isEqualTo("newKey")
    }

    @Test
    fun `updateValueAtIndex should update the value at given index`() {
        val sampleList: ArrayList<Pair<String, String>> =
            arrayListOf(("key" to "val"), ("key2" to "val2"))
        val newList = sampleList.updateValueAtIndex(0, "newValue")
        Truth.assertThat(newList[0].second).isEqualTo("newValue")
    }
}
