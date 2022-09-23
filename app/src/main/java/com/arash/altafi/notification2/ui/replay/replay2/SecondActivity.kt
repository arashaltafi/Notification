package com.arash.altafi.notification2.ui.replay.replay2

import android.app.Notification
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import com.arash.altafi.notification2.databinding.ActivitySecondBinding
import com.arash.altafi.notification2.utils.Constants.CHANNEL_ID
import com.arash.altafi.notification2.utils.Constants.NOTIFICATION_ID
import com.arash.altafi.notification2.utils.Constants.REPLY_KEY
import com.arash.altafi.notification2.utils.initializeNotification

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding
    private lateinit var notificationManagerCompat: NotificationManagerCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        receiveInput()
    }

    private fun receiveInput() {
        val remoteInput = RemoteInput.getResultsFromIntent(intent)
        if (remoteInput != null) {
            binding.tvResult.text = remoteInput.getCharSequence(REPLY_KEY).toString()

            notificationManagerCompat = NotificationManagerCompat.from(this)

            val notification = initializeNotification(
                "Notification Title",
                "Replied",
                NotificationCompat.PRIORITY_HIGH
            )

            notificationManagerCompat.notify(NOTIFICATION_ID, notification)
        }
    }

}