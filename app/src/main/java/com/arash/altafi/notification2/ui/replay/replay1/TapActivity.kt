package com.arash.altafi.notification2.ui.replay.replay1

import android.app.RemoteInput
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.arash.altafi.notification2.databinding.ActivityTapBinding
import com.arash.altafi.notification2.utils.Constants.CHANNEL_ID
import com.arash.altafi.notification2.utils.Constants.KEY_REPLY
import com.arash.altafi.notification2.utils.Constants.NOTIFICATION_ID

class TapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTapBinding
    private lateinit var notificationManagerCompat: NotificationManagerCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        receiveUserInput()
    }

    private fun receiveUserInput() {
        notificationManagerCompat = NotificationManagerCompat.from(this)

        val intent = this.intent
        val remoteInput = RemoteInput.getResultsFromIntent(intent)
        if (remoteInput != null) {
            val inputString = remoteInput.getCharSequence(KEY_REPLY).toString()
            binding.tvOne.text = inputString

            val replyToNotification = NotificationCompat.Builder(this@TapActivity, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentText("Thank you! Your reply has been received! ")
                .build()

            notificationManagerCompat.notify(NOTIFICATION_ID, replyToNotification)
        }
    }

}