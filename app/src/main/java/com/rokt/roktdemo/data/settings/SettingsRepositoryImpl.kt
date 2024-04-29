package com.rokt.roktdemo.data.settings

import android.content.SharedPreferences

class SettingsRepositoryImpl(private val preferences: SharedPreferences) : SettingsRepository {

    override fun setBooleanSettingsValue(settingsKey: String, value: Boolean) {
        preferences.edit()
            .putBoolean(settingsKey, value)
            .apply()
    }

    override fun getBooleanSettingsValue(settingsKey: String): Boolean {
        return preferences.getBoolean(settingsKey, false)
    }
}