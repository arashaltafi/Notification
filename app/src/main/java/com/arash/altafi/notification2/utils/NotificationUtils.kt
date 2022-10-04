package com.arash.altafi.notification2.utils

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.Icon
import android.os.Build
import android.util.Log
import androidx.annotation.IntRange
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.Person
import androidx.core.graphics.drawable.IconCompat
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
            inboxStyle.setBigContentTitle("${message.data["title"]}")
            inboxStyle.setSummaryText(body.body.count().toString())
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

    @SuppressLint("RestrictedApi")
    fun sendMessageNotification(
        context: Context,
        list: MutableList<ChatData>,
        message: RemoteMessage
    ) {
        val notificationID = message.data["notification_id"]?.toInt() ?: 1
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }

        val messagingStyle = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val personStyle = Person.Builder()
                .setName("me")
                .setIcon(
                    IconCompat.createFromIcon(
                        Icon.createWithResource(
                            context,
                            R.drawable.ic_launcher_foreground
                        )
                    )
                )
                .build()
            NotificationCompat.MessagingStyle(personStyle).setConversationTitle("GroupChat")
        } else {
            NotificationCompat.MessagingStyle("me").setConversationTitle("GroupChat")
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (i in list) {
                val person = Person.Builder()
                    .setName(i.username)
                    .setIcon(
                        IconCompat.createFromIcon(
                            Icon.createWithResource(
                                context,
                                R.drawable.ic_android_black_24dp
                            )
                        )
                    )
                    .build()
                messagingStyle.addMessage(
                    NotificationCompat.MessagingStyle.Message(
                        i.message,
                        i.time ?: System.currentTimeMillis(),
                        person
                    )
                )
            }
        } else {
            for (i in list) {
                messagingStyle.addMessage(
                    NotificationCompat.MessagingStyle.Message(
                        i.message,
                        i.time ?: System.currentTimeMillis(),
                        i.username
                    )
                )
            }
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
            .setContentTitle("ContentTitle")
            .setContentText("ContentText")
            .setSubText("SubText")
            .setStyle(messagingStyle)
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
            .setOnlyAlertOnce(true)
            .setColor(Color.GREEN)
            .build()

        notificationManager.notify(notificationID, notificationCompat)
    }

    fun progressNotification(
        notificationID: Int,
        context: Context,
        channelId: String,
        @IntRange(from = 0, to = 100)
        progress: Int = 0,
        isDownload: Boolean
    ) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationBuilder = NotificationCompat.Builder(context, channelId)
        val icon = if (isDownload) R.drawable.ic_baseline_file_download_24 else R.drawable.ic_baseline_file_upload_24
        val lockCancel: Boolean = progress < 100
        val indeterminate: Boolean = progress == 0
        val finish = if (isDownload) "دانلود با موفقیت به پایان رسید" else "آپلود با موفقیت به پایان رسید"
        val description = if (progress >= 100) finish else "$progress kb"
        val title = if (isDownload) "در حال دانلود ..." else "در حال آپلود ..."

        notificationBuilder
            .setChannelId(channelId)
            .setSmallIcon(icon)
            .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
            .setContentTitle(setPersianDigits(title))
            .setContentText(setPersianDigits(description))
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
            .setOngoing(lockCancel)
            .setProgress(100, progress, indeterminate)

        notificationManager.notify(notificationID, notificationBuilder.build())
    }

    fun testGroup(context: Context, list: ArrayList<String>, notificationID: Int) {
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
            inboxStyle.setSummaryText(list.count().toString())
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

    @SuppressLint("RestrictedApi")
    fun testMessengingNotification(context: Context, nId: Int, list: MutableList<ChatData>) {

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }

        val messagingStyle = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val personStyle = Person.Builder()
                .setName("me")
                .setIcon(
                    IconCompat.createFromIcon(
                        Icon.createWithResource(
                            context,
                            R.drawable.ic_launcher_foreground
                        )
                    )
                )
                .build()
            NotificationCompat.MessagingStyle(personStyle).setConversationTitle("GroupChat")
        } else {
            NotificationCompat.MessagingStyle("me").setConversationTitle("GroupChat")
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (i in list) {
                val person = Person.Builder()
                    .setName(i.username)
                    .setIcon(
                        IconCompat.createFromIcon(
                            Icon.createWithResource(
                                context,
                                R.drawable.ic_android_black_24dp
                            )
                        )
                    )
                    .build()
                messagingStyle.addMessage(
                    NotificationCompat.MessagingStyle.Message(
                        i.message,
                        i.time ?: System.currentTimeMillis(),
                        person
                    )
                )
            }
        } else {
            for (i in list) {
                messagingStyle.addMessage(
                    NotificationCompat.MessagingStyle.Message(
                        i.message,
                        i.time ?: System.currentTimeMillis(),
                        i.username
                    )
                )
            }
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

}