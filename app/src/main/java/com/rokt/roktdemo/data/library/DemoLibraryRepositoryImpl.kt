package com.rokt.roktdemo.data.library

import com.rokt.roktdemo.data.Result
import com.rokt.roktdemo.data.service.RoktDemoService
import com.rokt.roktdemo.model.DemoLibrary
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class DemoLibraryRepositoryImpl(private val roktDemoService: RoktDemoService) :
    DemoLibraryRepository {
    private var library: DemoLibrary? = null

    override suspend fun getDemoLibrary(): Flow<Result<DemoLibrary>> {
        if (library == null) {
            try {
                val demoLibrary = roktDemoService.getDemoLibrary()
                library = demoLibrary
            } catch (e: Exception) {
                Timber.d(e)
                return flow {
                    emit(Result.Error(e))
                }
            }
        }

        return flow { emit(Result.Success(library!!)) }
    }
}
