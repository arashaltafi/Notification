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
        private var sharedPref: SharedPreferences? = null

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

        message.data["type"]?.let {
            when (it.toInt()) {
                1 -> {  // simple notification
                    Log.i("test123321", "notification: ${message.data["body"].toString()}")
                    NotificationUtils.sendNotification(this, message)
                }
                2 -> {  // group notification
                    val notificationList =
                        jsonUtils.getObject<MyNotificationModel>(message.data["body"].toString())
                    Log.i("test123321", "notificationGroup: $notificationList")
                    NotificationUtils.sendNotificationGroup(this, notificationList, message)
                }
                3 -> {  // messenger notification
                    val notificationMessageList =
                        jsonUtils.getObjectList<NotificationMessageModel>(message.data["body"].toString())
                    Log.i("test123321", "notificationMessageList: $notificationMessageList")
                    val list: MutableList<ChatData> = mutableListOf()
                    notificationMessageList.forEach { items ->
                        list.add(
                            ChatData(
                                message = items.text,
                                userAvatar = items.userAvatar,
                                username = items.userName
                            )
                        )
                    }
                    NotificationUtils.sendMessageNotification(this, list, message)
                }
            }
        } ?: kotlin.run {
            NotificationUtils.sendNotificationPostMan(this, message)
        }
    }
}