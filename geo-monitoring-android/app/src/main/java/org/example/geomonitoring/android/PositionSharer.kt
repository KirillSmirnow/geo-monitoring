package org.example.geomonitoring.android

import android.app.Activity
import android.util.Log
import org.example.geomonitoring.android.UserSettings.Setting
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.UUID.randomUUID
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

object PositionSharer {

    private var executor: ExecutorService? = null
    private var log: (String) -> Unit = {}
    var enabled: Boolean = false

    fun initialize(activity: Activity, log: (String) -> Unit) {
        this.log = log
        this.enabled = false
        this.executor?.shutdown()
        val executor = Executors.newSingleThreadScheduledExecutor()
        executor.scheduleWithFixedDelay({ sharePosition(activity) }, 1, 5, TimeUnit.SECONDS)
        this.executor = executor
    }

    private fun sharePosition(activity: Activity) {
        if (!enabled) {
            log("Position sharing disabled")
            return
        }
        try {
            val location = Locator.getLocation(activity)
            val geoPosition = GeoPosition(
                id = randomUUID(),
                objectId = UserSettings.get(activity, Setting.USERNAME) ?: throw RuntimeException("No username"),
                utcDateTime = LocalDateTime.now(ZoneOffset.UTC),
                latitude = location.latitude,
                longitude = location.longitude,
            )
            Client.send(geoPosition)
            log("<span style='color: teal'>${geoPosition.utcDateTime.toLocalTime()} --- $location</span>")
        } catch (e: Exception) {
            Log.w(javaClass.simpleName, e)
            log("<span style='color: red'>${e.message}</span>")
        }
    }
}
