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
import com.arash.altafi.notification2.models.ChatData
import com.arash.altafi.notification2.models.MyNotificationModel
import com.arash.altafi.notification2.ui.MainActivity
import com.arash.altafi.notification2.utils.Constants.CHANNEL_ID
import com.google.firebase.messaging.RemoteMessage
import java.io.IOException
import java.net.URL

object NotificationUtils {

    fun sendNotification(context: Context, body: MyNotificationModel, message: RemoteMessage) {
        val notificationID = message.data["notification_id"]?.toInt() ?: 1
        Log.i("987564", "sendNotification: $notificationID")
        val inboxStyle = NotificationCompat.InboxStyle()

        val intent = Intent(context, MainActivity::class.java)
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

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

        body.body.forEach {
            inboxStyle.setBigContentTitle("${message.data["title"]} $notificationID")
            inboxStyle.setSummaryText("${message.data["title"]} $notificationID")
            inboxStyle.addLine(it)
        }

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_android_black_24dp)
            .setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE)
            .setGroup(notificationID.toString())
            .setContentTitle(message.data["title"])
            .setContentText(body.body.last())
            .setLargeIcon(image)
            .setAutoCancel(true)
            .setStyle(inboxStyle)
            .setGroupSummary(false)
            .setContentIntent(pendingIntent)

        notificationManager.notify(notificationID, notification.build())
    }

    fun test(context: Context, list: ArrayList<String>, notificationID: Int) {
        val inboxStyle = NotificationCompat.InboxStyle()

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

        list.forEach {
            inboxStyle.setBigContentTitle("Content Text $notificationID")
            inboxStyle.setSummaryText("summary Text $notificationID")
            inboxStyle.addLine(it)
        }

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_android_black_24dp)
            .setGroup(notificationID.toString())
            .setContentTitle("title")
            .setContentText(list.last())
            .setAutoCancel(true)
            .setStyle(inboxStyle)
            .setGroupSummary(false)
            .setContentIntent(pendingIntent)

        notificationManager.notify(notificationID, notification.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channelName = "channelName"
        val channel = NotificationChannel(
            CHANNEL_ID,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "My channel description"
            enableLights(true)
            lightColor = Color.GREEN
        }
        notificationManager.createNotificationChannel(channel)
    }

    fun messengingNotification(context: Context, nId: Int, list: MutableList<ChatData>) {

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationUtils.createNotificationChannel(notificationManager)
        }

        val messagingStyle =
            NotificationCompat.MessagingStyle("me").setConversationTitle("GroupChat")
        for (i in list) {
            messagingStyle.addMessage(
                NotificationCompat.MessagingStyle.Message(
                    i.message,
                    i.t,
                    i.username
                )
            )
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            Intent(context, MainActivity::class.java),
            (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE
            else 0x0)
                    or
                    PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notificationCompat = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_android_black_24dp)
            .setContentTitle("User1")
            .setContentText("hi...san")
            .setSubText("ChatApp")
            .setStyle(messagingStyle)
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
            .setOnlyAlertOnce(true)
            .setColor(Color.GREEN)
            .build()

        notificationManager.notify(nId, notificationCompat)
    }

}