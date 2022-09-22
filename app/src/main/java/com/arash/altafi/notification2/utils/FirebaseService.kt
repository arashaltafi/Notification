package com.arash.altafi.notification2.utils

import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseService : FirebaseMessagingService() {

    companion object {
        var sharedPref: SharedPreferences? = null

        var token: String?
        get() {
            return sharedPref?.getString("token", "")
        }
        set(value) {
            sharedPref?.edit()?.putString("token", value)?.apply()
        }
    }

    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)
        token = newToken
        Log.i("test123321", "onNewToken: $newToken")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        NotificationUtils.sendNotification(this, message)
    }
}











