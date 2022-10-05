package com.arash.altafi.notification2.ui.media.media2.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationActionService : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.sendBroadcast(
            Intent("Songs action")
                .putExtra(
                    "action_name",
                    intent?.action)
        )
    }

}