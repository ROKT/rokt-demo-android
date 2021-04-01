package com.rokt.roktdemo.di

import com.rokt.roktdemo.data.AboutRoktRepository
import com.rokt.roktdemo.data.about.AboutRoktRepositoryMockImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {
    @Singleton
    @Provides
    fun provideAboutRoktRepository(): AboutRoktRepository {
        return AboutRoktRepositoryMockImpl()
    }
}