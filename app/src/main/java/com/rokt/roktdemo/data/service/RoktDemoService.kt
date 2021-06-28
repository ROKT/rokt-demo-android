package com.rokt.roktdemo.data.service

import com.rokt.roktdemo.model.AboutRokt
import com.rokt.roktdemo.model.DemoLibrary
import retrofit2.http.GET
import retrofit2.http.POST

interface RoktDemoService {
    @POST("library")
    suspend fun getDemoLibrary(): DemoLibrary

    @GET("about")
    suspend fun getAboutPage(): AboutRokt
}
