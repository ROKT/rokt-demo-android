package com.rokt.roktdemo.data.about

import com.rokt.roktdemo.model.AboutRokt
import javax.inject.Singleton

@Singleton
interface AboutRoktRepository {
    abstract fun getAboutRokt(): AboutRokt
}
