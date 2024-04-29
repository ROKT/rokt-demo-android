package com.rokt.roktdemo.data.settings

import javax.inject.Singleton

@Singleton
interface SettingsRepository {
    fun getBooleanSettingsValue(settingsKey: String): Boolean
    fun setBooleanSettingsValue(settingsKey: String, value: Boolean)
}

const val STAGE_ENV_ENABLED = "StageEnvironment"
const val DEBUG_LOGS_ENABLED = "DebugLogs"