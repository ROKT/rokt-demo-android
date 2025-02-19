package com.rokt.roktdemo.data.about

import com.rokt.roktdemo.data.Result
import com.rokt.roktdemo.model.AboutRokt
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface AboutRoktRepository {
    abstract suspend fun getAboutRokt(): Flow<Result<AboutRokt>>
}
