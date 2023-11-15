package org.example.geomonitoring.android

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat

class LocationForegroundService : Service() {

    private val notificationChannelId = "101"
    private val notificationId = 101

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(notificationId, buildNotification())
        return super.onStartCommand(intent, flags, startId)
    }

    private fun buildNotification(): Notification {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(
            NotificationChannel(notificationChannelId, notificationChannelId, NotificationManager.IMPORTANCE_HIGH)
        )
        return NotificationCompat.Builder(this, notificationChannelId).build()
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}
