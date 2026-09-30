package com.rokt.roktdemo.di

import android.content.Context
import com.rokt.roktdemo.data.about.AboutRoktRepository
import com.rokt.roktdemo.data.about.AboutRoktRepositoryImpl
import com.rokt.roktdemo.data.library.DemoLibraryRepository
import com.rokt.roktdemo.data.library.DemoLibraryRepositoryImpl
import com.rokt.roktdemo.data.service.LocalRoktDemoService
import com.rokt.roktdemo.data.service.RoktDemoService
import com.rokt.roktdemo.data.settings.SettingsRepository
import com.rokt.roktdemo.data.settings.SettingsRepositoryImpl
import com.rokt.roktdemo.data.validate.ValidatorMockImplementation
import com.rokt.roktdemo.data.validate.ValidatorRepository
import com.rokt.roktdemo.ui.demo.RoktExecutor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val PREFERENCE_NAME = "RoktDemo"

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {
    @Singleton
    @Provides
    fun provideAboutRoktRepository(roktDemoService: RoktDemoService): AboutRoktRepository {
        return AboutRoktRepositoryImpl(roktDemoService)
    }

    @Singleton
    @Provides
    fun provideDemoRepository(roktDemoService: RoktDemoService): DemoLibraryRepository {
        return DemoLibraryRepositoryImpl(roktDemoService)
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

    @Singleton
    @Provides
    fun provideRoktDemoService(@ApplicationContext appContext: Context): RoktDemoService {
        return LocalRoktDemoService(appContext)
    }

    @Singleton
    @Provides
    fun provideSettingsRepository(@ApplicationContext appContext: Context): SettingsRepository {
        val preferences = appContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        return SettingsRepositoryImpl(preferences)
    }
}
