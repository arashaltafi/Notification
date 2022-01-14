package com.arash.altafi.notification.sample1

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.arash.altafi.notification.MainActivity
import com.arash.altafi.notification.R
import kotlinx.android.synthetic.main.activity_sample1.*
import java.util.*

class Sample1 : AppCompatActivity() {

    private var manager: NotificationManager ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample1)

        init()
        notif()
//        logcat()
    }

    private fun init() {

        btn_sample_1_one.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
            val builder: NotificationCompat.Builder =
                NotificationCompat.Builder(this, "myApp")
                    .setSmallIcon(R.drawable.ic_android_black_24dp)
                    .setContentTitle("تست 1")
                    .setContentText("َآرش الطافی")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
            manager?.notify(Random().nextInt(), builder.build())
        }

        btn_sample_1_two.setOnClickListener {
            val builder = NotificationCompat.Builder(this, "myApp")
                .setSmallIcon(R.drawable.ic_android_black_24dp)
                .setContentTitle("تست 2")
                .setContentText("استایل تصویر بزرگ")
                .setStyle(
                    NotificationCompat.BigPictureStyle()
                        .bigPicture(BitmapFactory.decodeResource(resources, R.drawable.pics))
                )
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
            manager?.notify(Random().nextInt(), builder.build())
        }

        btn_sample_1_three.setOnClickListener {
            val builder = NotificationCompat.Builder(this, "myApp")
                .setSmallIcon(R.drawable.ic_android_black_24dp)
                .setContentTitle("تست 3")
                .setContentText("استایل تصویر بزرگ")
                .setStyle(NotificationCompat.BigTextStyle().bigText(getString(R.string.lorem)))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
            manager?.notify(Random().nextInt(), builder.build())
        }

        btn_sample_1_four.setOnClickListener {
            val builder = NotificationCompat.Builder(this, "myApp")
                .setSmallIcon(R.drawable.ic_android_black_24dp)
                .setContentTitle("تست 4")
                .setContentText("استایل تصویر بزرگ")
                .setStyle(NotificationCompat.InboxStyle().addLine("متن اول است").addLine("متن دوم").addLine("متن سوم"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
            manager?.notify(Random().nextInt(), builder.build())
        }

    }

    private fun notif() {
        manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("myApp", "Channel", NotificationManager.IMPORTANCE_DEFAULT)
            manager?.createNotificationChannel(channel)
        }
    }

    private fun logcat() {

        // detect notification (General)
        NotificationManagerCompat.from(this).areNotificationsEnabled()
        Log.i("test_notification", "${NotificationManagerCompat.from(this).areNotificationsEnabled()}")

        // detect notification (Component)
        isNotificationChannelEnabled(this , "Channel")
        val log = isNotificationChannelEnabled(this , "Channel")
        Log.i("test_notification", "$log")
    }

    private fun isNotificationChannelEnabled(context: Context, channelId: String): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!TextUtils.isEmpty(channelId)) {
                val manager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                val channel = manager.getNotificationChannel(channelId)
                return channel.importance != NotificationManager.IMPORTANCE_NONE
                true
            }
            false
        } else {
            NotificationManagerCompat.from(context).areNotificationsEnabled()
        }
    }

}