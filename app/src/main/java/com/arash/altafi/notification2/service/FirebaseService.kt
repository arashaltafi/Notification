package com.arash.altafi.notification2.service

import android.content.SharedPreferences
import android.util.Log
import com.arash.altafi.notification2.models.MyNotificationModel
import com.arash.altafi.notification2.utils.JsonUtils
import com.arash.altafi.notification2.utils.NotificationUtils
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson

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

    private var gson = Gson()
    private var jsonUtils = JsonUtils(gson)

    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)
        token = newToken
        Log.i("test123321", "onNewToken: $newToken")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Log.i("test123321", "onMessageReceived: ${message.data["body"]}")

        val list = jsonUtils.getObject<MyNotificationModel>(message.data["body"].toString())
        Log.i("test123321", "list: $list")

        NotificationUtils.sendNotification(this, list, message)
    }
}











