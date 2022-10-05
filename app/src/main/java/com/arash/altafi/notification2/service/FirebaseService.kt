package com.arash.altafi.notification2.service

import android.content.SharedPreferences
import android.util.Log
import com.arash.altafi.notification2.models.*
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

        when (message.data["type"].toString().toInt()) {
            1 -> {
                Log.i("test123321", "notification: ${message.data["body"].toString()}")
                NotificationUtils.sendNotification(this, message)
            }
            2 -> {
                val notificationList = jsonUtils.getObject<MyNotificationModel>(message.data["body"].toString())
                Log.i("test123321", "notificationGroup: $notificationList")
                NotificationUtils.sendNotificationGroup(this, notificationList, message)
            }
            3 -> {
                val notificationMessageList = jsonUtils.getObject<NotificationMessageModel>(message.data["body"].toString())
                Log.i("test123321", "notificationMessageList: $notificationMessageList")
                val list: MutableList<ChatData> = mutableListOf()
                notificationMessageList.message.forEach {
                    list.add(ChatData(message = it))
                }
                notificationMessageList.username.forEach {
                    list.add(ChatData(username = it))
                }
                notificationMessageList.time.forEach {
                    list.add(ChatData(time = it.toLong()))
                }
                NotificationUtils.sendMessageNotification(this, list, message)
            }
        }
    }
}