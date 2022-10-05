package com.arash.altafi.notification2.ui.replay.replay2

import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import com.arash.altafi.notification2.databinding.ActivitySampleReplay2Binding
import com.arash.altafi.notification2.utils.Constants.REPLY_KEY
import com.arash.altafi.notification2.utils.NotificationUtils
import com.arash.altafi.notification2.utils.getPendingIntentFlags

class SampleReplayActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivitySampleReplay2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySampleReplay2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        binding.btnSample2.setOnClickListener {

            val tapResultIntent = Intent(this, SecondActivity::class.java)

            val pendingIntent: PendingIntent = PendingIntent.getActivity(
                this, 0, tapResultIntent,
                getPendingIntentFlags(true)
            )

            //Reply Action
            val remoteInput: RemoteInput = RemoteInput.Builder(REPLY_KEY).run {
                setLabel("Insert your text here")
                build()
            }

            val replayAction = NotificationCompat.Action.Builder(
                0,
                "Reply",
                pendingIntent
            ).addRemoteInput(remoteInput).build()

            NotificationUtils.replayNotification(
                this,
                "Notification Title",
                "Replied",
                NotificationCompat.PRIORITY_HIGH,
                pendingIntent,
                replayAction
            )
        }
    }

}