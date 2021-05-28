package com.rokt.roktdemo.di

import com.rokt.roktdemo.data.about.AboutRoktRepository
import com.rokt.roktdemo.data.about.AboutRoktRepositoryMockImpl
import com.rokt.roktdemo.data.library.DemoLibraryRepository
import com.rokt.roktdemo.data.library.DemoLibraryRepositoryMockImpl
import com.rokt.roktdemo.data.validate.ValidatorMockImplementation
import com.rokt.roktdemo.data.validate.ValidatorRepository
import com.rokt.roktdemo.ui.demo.RoktExecutor
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

    @Singleton
    @Provides
    fun provideDemoRepository(): DemoLibraryRepository {
        return DemoLibraryRepositoryMockImpl()
    }

    @Singleton
    @Provides
    fun provideValidator(): ValidatorRepository {
        return ValidatorMockImplementation()
    }

    @Singleton
    @Provides
    fun provideRoktExecutor(): RoktExecutor {
        return RoktExecutor
    }
}