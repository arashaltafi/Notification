package com.arash.altafi.notification2.ui.replay.replay3

import android.app.*
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.arash.altafi.notification2.databinding.ActivitySampleReplay3Binding
import com.arash.altafi.notification2.utils.Constants.REPLY_KEY
import com.arash.altafi.notification2.utils.NotificationUtils
import com.arash.altafi.notification2.utils.getPendingIntentFlags

class SampleReplayActivity3 : AppCompatActivity() {

    private lateinit var binding: ActivitySampleReplay3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySampleReplay3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        handleIntent()
    }

    private fun init() {
        binding.btnSample3.setOnClickListener {
            val resultIntent = Intent(this, SampleReplayActivity3::class.java)

            val resultPendingIntent: PendingIntent = PendingIntent.getActivity(
                this, 0, resultIntent,
                getPendingIntentFlags(true)
            )

            val remoteInput = androidx.core.app.RemoteInput.Builder(REPLY_KEY)
                .setLabel("Enter your reply here")
                .build()

            val replayAction = NotificationCompat.Action.Builder(
                android.R.drawable.ic_dialog_info,
                "Reply",
                resultPendingIntent
            ).addRemoteInput(remoteInput).build()

            NotificationUtils.replayNotification(
                this,
                "Notification Title",
                "Notification Content Text",
                NotificationCompat.PRIORITY_HIGH,
                replyAction = replayAction
            )
        }
    }

    private fun handleIntent() {
        val intent = this.intent
        val remoteInput = RemoteInput.getResultsFromIntent(intent)
        remoteInput?.let {
            val inputString = remoteInput.getCharSequence(REPLY_KEY).toString()
            binding.tv.text = inputString
            NotificationUtils.showNotif(this)
        }
    }

}