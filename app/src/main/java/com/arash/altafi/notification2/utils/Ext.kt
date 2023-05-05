package com.arash.altafi.notification2.utils

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import com.arash.altafi.notification2.R
import com.arash.altafi.notification2.utils.glide.GlideUtils
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


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

inline fun <reified NEW> Any.cast(): NEW? {
    return if (this.isCastable<NEW>())
        this as NEW
    else null
}

inline fun <reified NEW> Any.isCastable(): Boolean {
    return this is NEW
}

fun setPersianDigits(src: String?): String {
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

fun String.getBitmapFromURL(): Bitmap? {
    return try {
        val url = URL(this)
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
        connection.doInput = true
        connection.connect()
        val input: InputStream = connection.inputStream
        BitmapFactory.decodeStream(input)
    } catch (e: Exception) {
        null
    }
}

fun Context.getBitmap(
    url: Any,
    result: ((Bitmap) -> Unit),
    @DrawableRes placeholderRes: Int? = R.drawable.bit_placeholder_image,
    @DrawableRes errorRes: Int? = R.drawable.bit_error_image,
    requestOptions: RequestOptions? = null
) {
    GlideUtils(this).getBitmapRequestBuilder(requestOptions)
        .load(url)
        .apply {
            placeholderRes?.let { placeholder(it) }
            error(errorRes)
        }
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                result.invoke(resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) {
            }

        })

}