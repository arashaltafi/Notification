package com.arash.altafi.notification.sample2

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat
import com.arash.altafi.notification.R
import kotlinx.android.synthetic.main.activity_sample2.*

class Sample2 : AppCompatActivity() {

    var notifManager: NotificationManager? = null
    var offerChannelId = "Offers"
    var mpIntent: PendingIntent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample2)

        notifManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val pIntent = Intent(this, TestActivity::class.java)
        mpIntent = PendingIntent.getActivity(this, 0, pIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        init()
    }

    private fun init() {

        simple_notif.setOnClickListener(View.OnClickListener {
            createNotifChannel()
            simpleNotification()
        })

        pending_notif.setOnClickListener(View.OnClickListener {
            createNotifChannel()
            pendingNotification()
        })

        big_text_notif.setOnClickListener(View.OnClickListener {
            createNotifChannel()
            bigTextNotification()
        })

        big_pic_notif.setOnClickListener(View.OnClickListener {
            createNotifChannel()
            bigPicNotification()
        })

        inbox_notif.setOnClickListener(View.OnClickListener {
            createNotifChannel()
            inboxNotification()
        })

        delete_channel.setOnClickListener(View.OnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notifManager!!.deleteNotificationChannel(offerChannelId)
            }
        })

    }

    private fun createNotifChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val offerChannelName = "Shop offers"
            val offerChannelDescription = "Best offers for customers"
            val offerChannelImportance = NotificationManager.IMPORTANCE_DEFAULT
            val notifChannel =
                NotificationChannel(offerChannelId, offerChannelName, offerChannelImportance)
            notifChannel.description = offerChannelDescription
            //notifChannel.enableVibration(true);
            notifChannel.enableLights(true)
            notifChannel.lightColor = Color.GREEN
            //notifChannel.setShowBadge(false);
            notifManager!!.createNotificationChannel(notifChannel)
        }
    }

    private fun simpleNotification() {
        val sNotifBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, offerChannelId)
                .setSmallIcon(R.drawable.notif_icon)
                .setContentTitle("بروزرسانی")
                .setContentText("یک نسخه جدید از برنامه آماده دریافت است")
                .setVibrate(longArrayOf(100, 500, 500, 500, 500, 500))
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.android))
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE)
                .setNumber(3)
                .setTimeoutAfter(400000)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        notifManager!!.notify(1, sNotifBuilder.build())
    }

    private fun pendingNotification() {
        val pNotifBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, offerChannelId)
                .setSmallIcon(R.drawable.notif_icon)
                .setContentTitle("تخفیف پنجاه درصدی!")
                .setContentText("پنجاه درصد تخفیف به مناسبت طلوع خورشید")
                .setLargeIcon(
                    BitmapFactory.decodeResource(
                        resources,
                        R.drawable.android
                    )
                ) //.setContentIntent(mpIntent)
                .addAction(R.drawable.notif_icon, "Buy", mpIntent)
                .addAction(R.drawable.notif_icon, "Product details", mpIntent)
                .setAutoCancel(true)
        notifManager!!.notify(2, pNotifBuilder.build())
    }

    private fun bigTextNotification() {
        val bStyle = NotificationCompat.BigTextStyle()
            .bigText("لورم ایپسوم متنی است که ساختگی برای طراحی و چاپ آن مورد است. صنعت چاپ زمانی لازم بود شرایطی شما باید فکر ثبت نام و طراحی، لازمه خروج می باشد.")
            .setBigContentTitle("نوتیفیکیشن طولانی")
            .setSummaryText("خلاصه متن طولانی")
        val bNotifBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, offerChannelId)
                .setSmallIcon(R.drawable.notif_icon)
                .setContentTitle("تخفیف پنجاه درصدی!")
                .setContentText("پنجاه درصد تخفیف به مناسبت طلوع خورشید")
                .setStyle(bStyle)
        notifManager!!.notify(3, bNotifBuilder.build())
    }

    private fun bigPicNotification() {
        val mImage = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.bigpic)
        val bpStyle = NotificationCompat.BigPictureStyle()
            .bigPicture(mImage)
            .bigLargeIcon(null)
        //.setBigContentTitle("test");
        val bNotifBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, offerChannelId)
                .setSmallIcon(R.drawable.notif_icon)
                .setContentTitle("تخفیف پنجاه درصدی!")
                .setContentText("پنجاه درصد تخفیف به مناسبت طلوع خورشید")
                .setLargeIcon(mImage)
                .setStyle(bpStyle)
        notifManager!!.notify(4, bNotifBuilder.build())
    }

    private fun inboxNotification() {
        val iStyle = NotificationCompat.InboxStyle()
            .addLine("New order received")
            .addLine("Your payment information")
            .addLine("Amazon offer!")
            .setSummaryText("+3 more...")
        val iNotifBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, offerChannelId)
                .setSmallIcon(R.drawable.notif_icon)
                .setContentTitle("Inbox")
                .setStyle(iStyle)
        notifManager!!.notify(5, iNotifBuilder.build())
    }

}