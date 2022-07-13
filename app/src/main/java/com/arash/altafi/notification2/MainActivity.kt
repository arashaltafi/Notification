package com.arash.altafi.notification2

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val topic = "/topics/myTopic2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnTestCrashlytics.setOnClickListener {
            throw RuntimeException("Test Crash")
        }

        FirebaseService.sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("test123321", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            val token = task.result
            FirebaseService.token = token
            etToken.setText(token)
            Log.i("test123321", "onNewToken: $token")
        })

        FirebaseMessaging.getInstance().subscribeToTopic(topic)

        btnSend.setOnClickListener {
            val title = etTitle.text.toString()
            val message = etMessage.text.toString()
            val image = etImage.text.toString()
            val recipientToken = etToken.text.toString()
            if(title.isNotEmpty() && message.isNotEmpty() && recipientToken.isNotEmpty()) {
                PushNotification(
                    NotificationData(title, message, image),
                    topic
                ).also {
                    sendNotification(it)
                }
            }
        }

        btnSendS21FE.setOnClickListener {
            val title = etTitle.text.toString()
            val message = etMessage.text.toString()
            val image = etImage.text.toString()
            val recipientToken = etToken.text.toString()
            if(title.isNotEmpty() && message.isNotEmpty() && recipientToken.isNotEmpty()) {
                PushNotification(
                    NotificationData(title, message, image),
                    Constants.PHONE_S21_FE_TOKEN
                ).also {
                    sendNotification(it)
                }
            }
        }

        btnSendEmulator29.setOnClickListener {
            val title = etTitle.text.toString()
            val message = etMessage.text.toString()
            val image = etImage.text.toString()
            val recipientToken = etToken.text.toString()
            if(title.isNotEmpty() && message.isNotEmpty() && recipientToken.isNotEmpty()) {
                PushNotification(
                    NotificationData(title, message, image),
                    Constants.EMULATOR_API_29_TOKEN
                ).also {
                    sendNotification(it)
                }
            }
        }

        btnSendCurrentDevice.setOnClickListener {
            val title = etTitle.text.toString()
            val message = etMessage.text.toString()
            val image = etImage.text.toString()
            val recipientToken = etToken.text.toString()
            if(title.isNotEmpty() && message.isNotEmpty() && recipientToken.isNotEmpty()) {
                PushNotification(
                    NotificationData(title, message, image),
                    recipientToken
                ).also {
                    sendNotification(it)
                }
            }
        }

    }

    private fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = RetrofitInstance.api.postNotification(notification)
            if(response.isSuccessful) {
//                Log.d("test123321", "Response: ${Gson().toJson(response)}")
            } else {
//                Log.e("test123321", response.errorBody().toString())
            }
        } catch(e: Exception) {
            Log.e("test123321", e.toString())
        }
    }
}