package com.arash.altafi.notification2.pushy

import me.pushy.sdk.Pushy
import android.content.Intent
import android.graphics.Color
import android.content.Context
import android.app.PendingIntent
import android.media.RingtoneManager
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import androidx.core.app.NotificationCompat

private const val CHANNEL_ID_PUSHY = "my_channel_pushy"

class PushReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // Attempt to extract the "title" property from the data payload, or fallback to app shortcut label
        val notificationTitle = if (intent.getStringExtra("title") != null) intent.getStringExtra("title") else context.packageManager.getApplicationLabel(context.applicationInfo).toString()

        // Attempt to extract the "message" property from the data payload: {"message":"Hello World!"}
        val notificationText = if (intent.getStringExtra("message") != null ) intent.getStringExtra("message") else "Test notification"

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE
            else 0x0)
                    or
                    PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Prepare a notification with vibration, sound and lights
        val builder = NotificationCompat.Builder(context, CHANNEL_ID_PUSHY)
            .setAutoCancel(true)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(notificationTitle)
            .setContentText(notificationText)
            .setLights(Color.RED, 1000, 1000)
            .setVibrate(longArrayOf(0, 400, 250, 400))
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setContentIntent(pendingIntent)

        // Automatically configure a Notification Channel for devices running Android O+
        Pushy.setNotificationChannel(builder, context)

        // Get an instance of the NotificationManager service
        val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // Build the notification and display it
        notificationManager.notify(1, builder.build())
    }
}