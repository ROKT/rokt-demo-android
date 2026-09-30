package com.rokt.roktdemo.data.service

import com.rokt.roktdemo.model.AboutRokt
import com.rokt.roktdemo.model.DemoLibrary

interface RoktDemoService {
    suspend fun getDemoLibrary(): DemoLibrary

    suspend fun getAboutPage(): AboutRokt
}
