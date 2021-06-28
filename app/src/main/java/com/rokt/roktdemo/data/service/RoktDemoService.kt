package com.rokt.roktdemo.data.service

import com.rokt.roktdemo.model.AboutRokt
import com.rokt.roktdemo.model.DemoLibrary
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface RoktDemoService {
    @Headers("accept: text/plain")
    @POST("library")
    suspend fun getDemoLibrary(): DemoLibrary

    @Headers("accept: text/plain")
    @GET("about")
    suspend fun getAboutPage(): AboutRokt
}
