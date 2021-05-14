package com.rokt.roktdemo.data.library

import com.rokt.roktdemo.model.DemoLibrary
import javax.inject.Singleton

@Singleton
interface DemoLibraryRepository {
    abstract fun getDemoLibrary(): DemoLibrary
}
