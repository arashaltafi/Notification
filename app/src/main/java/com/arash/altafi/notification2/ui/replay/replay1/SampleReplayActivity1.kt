package com.arash.altafi.notification2.ui.replay.replay1

import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import com.arash.altafi.notification2.databinding.ActivitySampleReplay1Binding
import com.arash.altafi.notification2.utils.Constants.KEY_REPLY
import com.arash.altafi.notification2.utils.NotificationUtils
import com.arash.altafi.notification2.utils.getPendingIntentFlags

class SampleReplayActivity1 : AppCompatActivity() {

    private lateinit var binding: ActivitySampleReplay1Binding
    private lateinit var notificationManagerCompat: NotificationManagerCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySampleReplay1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        notificationManagerCompat = NotificationManagerCompat.from(this)

        binding.btnSample1.setOnClickListener {
            displayNotification()
        }
    }

    private fun displayNotification() {
        //First Tab
        val tapIntent = Intent(this, TapActivity::class.java)

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this, 0, tapIntent,
            getPendingIntentFlags(true)
        )

        val remoteInput: RemoteInput = RemoteInput.Builder(KEY_REPLY).run {
            setLabel("Insert the text You want to display.")
                .build()
        }

        val replyAction: NotificationCompat.Action =
            NotificationCompat.Action.Builder(
                0,
                "Reply Here: ",
                pendingIntent
            ).addRemoteInput(remoteInput).build()

        //Second Tab
        val secondIntent = Intent(this, ActionButtonOne::class.java)

        val pendingIntentTwo: PendingIntent = PendingIntent.getActivity(
            this, 0, secondIntent,
            getPendingIntentFlags(true)
        )

        val secondAction: NotificationCompat.Action =
            NotificationCompat.Action.Builder(
                0,
                "Cloud Storage", pendingIntentTwo
            ).build()


        //Third Tab
        val thirdIntent = Intent(this, ActionButtonTwo::class.java)

        val pendingIntentThree: PendingIntent = PendingIntent.getActivity(
            this, 0, thirdIntent,
            getPendingIntentFlags(true)
        )

        val thirdAction: NotificationCompat.Action =
            NotificationCompat.Action.Builder(
                1,
                "Media", pendingIntentThree
            ).build()

        NotificationUtils.replayNotification(
            this,
            "WARNING!!!!!",
            "To go to the next Activity, click Here!",
            NotificationCompat.PRIORITY_HIGH,
            pendingIntent,
            replyAction,
            secondAction,
            thirdAction
        )
    }

}