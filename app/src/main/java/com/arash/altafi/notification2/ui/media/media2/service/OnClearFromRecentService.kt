package com.arash.altafi.notification2.ui.media.media2.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

class OnClearFromRecentService: Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        stopSelf()
    }

}