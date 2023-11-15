package org.example.geomonitoring.android

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.Task
import java.time.Duration

object Locator {

    private val requiredPermissions = setOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
    )

    fun getLocation(activity: Activity): Location {
        if (!permissionsGranted(activity)) {
            requestPermissions(activity)
            throw RuntimeException("Location access not granted")
        }
        return findBestLocation(activity)
    }

    private fun permissionsGranted(context: Context): Boolean {
        return requiredPermissions.all { permission ->
            ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun requestPermissions(activity: Activity) {
        ActivityCompat.requestPermissions(activity, requiredPermissions.toTypedArray(), 0)
    }

    private fun findBestLocation(context: Context): Location {
        val client = LocationServices.getFusedLocationProviderClient(context)
        val task = client.getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY, null)
        val location = waitForCompletion(task, Duration.ofSeconds(3))
        return Location(location.latitude, location.longitude)
    }

    private fun <T> waitForCompletion(task: Task<T>, limit: Duration): T {
        var waitedMs = 0
        while (!task.isComplete && waitedMs < limit.toMillis()) {
            Thread.sleep(100)
            waitedMs += 100
        }
        if (!task.isComplete) throw RuntimeException("Location request too long")
        return task.result ?: throw RuntimeException("Location unavailable")
    }

    data class Location(val latitude: Double, val longitude: Double) {
        override fun toString(): String {
            return "(%.6f, %.6f)".format(latitude, longitude)
        }
    }
}
