package com.rokt.roktdemo.data.library

import com.rokt.roktdemo.data.Result
import com.rokt.roktdemo.model.DemoLibrary
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface DemoLibraryRepository {
    suspend fun getDemoLibrary(): Flow<Result<DemoLibrary>>
}
