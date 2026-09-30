package com.rokt.roktdemo.data.service

import com.google.gson.Gson
import com.rokt.roktdemo.model.AboutRokt
import com.rokt.roktdemo.model.DemoLibrary
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

/**
 * Pins the bundled content the app now serves instead of the retired demo content server.
 * Runs on the JVM with no Android runtime; the module directory is the working directory.
 */
class LocalAssetsDecodeTest {

    private fun asset(name: String): String = File("src/main/assets/$name").readText()

    @Test
    fun aboutAssetDecodesIntoAboutRokt() {
        val about = Gson().fromJson(asset("about.json"), AboutRokt::class.java)
        assertEquals(2, about.contents.size)
        assertEquals(1, about.links.size)
        assertTrue(about.contents.all { it.title.isNotBlank() && it.content.isNotBlank() })
    }

    @Test
    fun libraryAssetDecodesIntoDemoLibrary() {
        val library = Gson().fromJson(asset("library.json"), DemoLibrary::class.java)
        assertEquals("Placement library", library.demoTitle)
        assertTrue(library.demoDescription.isNotBlank())
        assertEquals("Confirmation Page", library.preDefinedScreen1.title)
        assertTrue(library.preDefinedScreen2.title.isNotBlank())
        assertTrue(library.preDefinedScreen3.title.isNotBlank())
        assertEquals("2920840145279427107", library.customConfigurationPage.accountDetails.accountID)
    }
}
