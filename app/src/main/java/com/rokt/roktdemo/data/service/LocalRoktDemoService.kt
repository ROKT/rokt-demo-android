package com.rokt.roktdemo.data.service

import android.content.Context
import com.google.gson.Gson
import com.rokt.roktdemo.model.AboutRokt
import com.rokt.roktdemo.model.DemoLibrary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * The two responses the retired demo content server returned for these screens, bundled so they need no request;
 * a plain Gson decodes them exactly as the network converter did.
 */
class LocalRoktDemoService(
    private val context: Context,
    private val gson: Gson = Gson()
) : RoktDemoService {

    override suspend fun getDemoLibrary(): DemoLibrary = readAsset(LIBRARY_ASSET, DemoLibrary::class.java)

    override suspend fun getAboutPage(): AboutRokt = readAsset(ABOUT_ASSET, AboutRokt::class.java)

    private suspend fun <T> readAsset(fileName: String, type: Class<T>): T =
        withContext(Dispatchers.IO) {
            context.assets.open(fileName).bufferedReader().use { reader ->
                gson.fromJson(reader, type)
            }
        }

    companion object {
        const val ABOUT_ASSET = "about.json"
        const val LIBRARY_ASSET = "library.json"
    }
}
