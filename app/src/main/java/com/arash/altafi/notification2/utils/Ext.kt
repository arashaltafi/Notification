package com.arash.altafi.notification2.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

fun getPendingIntentFlags(isMutable: Boolean = false): Int {
    return when {
        isMutable && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S ->
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_CANCEL_CURRENT

        !isMutable && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ->
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT

        else -> PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_CANCEL_CURRENT
    }
}

fun Context.initializeNotification(
    title: String,
    content: String,
    priority: Int,
    pendingIntent: PendingIntent? = null,
    replyAction: NotificationCompat.Action? = null,
    secondAction: NotificationCompat.Action? = null,
    thirdAction: NotificationCompat.Action? = null
): Notification {
    return NotificationCompat.Builder(this, Constants.CHANNEL_ID)
        .setContentTitle(title)
        .setContentText(content)
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setAutoCancel(true)
        .setPriority(priority)
        .setContentIntent(pendingIntent) // notification intent
        .addAction(secondAction) // first action intent
        .addAction(thirdAction) // second action intent
        .addAction(replyAction)
        .build()
}

fun createNotificationChannel(notificationManagerCompat: NotificationManagerCompat) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            Constants.CHANNEL_ID,
            Constants.CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManagerCompat.createNotificationChannel(channel)
    }
}

inline fun <reified NEW> Any.cast(): NEW? {
    return if (this.isCastable<NEW>())
        this as NEW
    else null
}

inline fun <reified NEW> Any.isCastable(): Boolean {
    return this is NEW
}

fun setPersianDigits(src: String?): String? {
    val result = StringBuilder("")
    var unicode = 0
    if (src != null) {
        for (i in src.indices) {
            unicode = src[i].code
            if (unicode in 48..57) {
                result.append((unicode + 1728).toChar())

                // Log.e("unicode", unicode + "");
                // Log.e("persian character", (unicode + 1728) + " " + (char) (unicode + 1728));
            } else {
                result.append(src[i])
            }
        }
    }
    return result.toString()
}