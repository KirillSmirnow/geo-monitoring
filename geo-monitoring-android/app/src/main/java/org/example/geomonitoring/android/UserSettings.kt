package org.example.geomonitoring.android

import android.app.Activity
import android.content.Context
import kotlin.reflect.KClass

object UserSettings {

    fun save(activity: Activity, setting: Setting, value: Any) {
        with(activity.getPreferences(Context.MODE_PRIVATE).edit()) {
            when (setting.type) {
                Boolean::class -> putBoolean(setting.name, value as Boolean)
                String::class -> putString(setting.name, value as String)
                else -> throw NotImplementedError("Unable to save setting of type ${setting.type}")
            }
            apply()
        }
    }

    fun <T> get(activity: Activity, setting: Setting): T? {
        val preferences = activity.getPreferences(Context.MODE_PRIVATE)
        return when (setting.type) {
            Boolean::class -> preferences.getBoolean(setting.name, false)
            String::class -> preferences.getString(setting.name, null)
            else -> throw NotImplementedError("Unable to get setting of type ${setting.type}")
        } as T
    }

    enum class Setting(val type: KClass<*>) {
        USERNAME(String::class),
        SHARE_POSITION(Boolean::class),
    }
}
