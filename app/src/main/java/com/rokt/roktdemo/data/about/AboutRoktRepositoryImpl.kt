package com.rokt.roktdemo.data.about

import com.rokt.roktdemo.data.Result
import com.rokt.roktdemo.data.service.RoktDemoService
import com.rokt.roktdemo.model.AboutRokt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class AboutRoktRepositoryImpl(private val roktDemoService: RoktDemoService) : AboutRoktRepository {
    override suspend fun getAboutRokt(): Flow<Result<AboutRokt>> {
        return try {
            val aboutPage = roktDemoService.getAboutPage()
            flow {
                emit(Result.Success(aboutPage))
            }
        } catch (e: Exception) {
            Timber.d(e)
            flow {
                emit(Result.Error(e))
            }
        }
    }
}
