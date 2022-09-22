package com.arash.altafi.notification2.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.arash.altafi.notification2.R
import com.arash.altafi.notification2.ui.MainActivity
import com.arash.altafi.notification2.utils.Constants.Companion.CHANNEL_ID
import com.google.firebase.messaging.RemoteMessage
import java.io.IOException
import java.net.URL

object NotificationUtils {

    private var value = 0
    private var defaultId = -1
    var inboxStyle = NotificationCompat.InboxStyle()

    fun sendNotification(context: Context, message: RemoteMessage) {
        value++
        val notificationID = 1

        val intent = Intent(context, MainActivity::class.java)
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (defaultId != notificationID) {
            inboxStyle = NotificationCompat.InboxStyle()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }

        var image: Bitmap? = null
        if (message.data["image"]?.isNotEmpty() == true) {
            try {
                val url = URL(message.data["image"])
                image = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            } catch (e: IOException) {
                image = null
                Log.e("test123321", "$e")
            }
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("NotClick", true)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE
            else 0x0)
                    or
                    PendingIntent.FLAG_UPDATE_CURRENT
        )

        inboxStyle.setBigContentTitle("Content Text $notificationID")
        inboxStyle.setSummaryText("summary Text $notificationID")
        inboxStyle.addLine("line$value")

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_android_black_24dp)
            .setGroup(notificationID.toString())
            .setLargeIcon(image)
            .setAutoCancel(true)
            .setStyle(inboxStyle)
            .setGroupSummary(false)
            .setContentIntent(pendingIntent)

        notificationManager.notify(notificationID, notification.build())

        defaultId = notificationID
    }

    fun test(context: Context, notificationID: Int) {

        if (defaultId != notificationID) {
            inboxStyle = NotificationCompat.InboxStyle()
            value = 0
        }

        value++

        val intent = Intent(context, MainActivity::class.java)
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("NotClick", true)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE
            else 0x0)
                    or
                    PendingIntent.FLAG_UPDATE_CURRENT
        )

        inboxStyle.setBigContentTitle("Content Text $notificationID")
        inboxStyle.setSummaryText("summary Text $notificationID")
        inboxStyle.addLine("line$value")

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_android_black_24dp)
            .setGroup(notificationID.toString())
            .setAutoCancel(true)
            .setStyle(inboxStyle)
            .setGroupSummary(false)
            .setContentIntent(pendingIntent)

        notificationManager.notify(notificationID, notification.build())

        defaultId = notificationID
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channelName = "channelName"
        val channel = NotificationChannel(CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_HIGH).apply {
            description = "My channel description"
            enableLights(true)
            lightColor = Color.GREEN
        }
        notificationManager.createNotificationChannel(channel)
    }

}