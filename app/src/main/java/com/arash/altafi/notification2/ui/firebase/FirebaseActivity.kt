package com.arash.altafi.notification2.ui.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.arash.altafi.notification2.databinding.ActivityFirebaseBinding
import com.arash.altafi.notification2.models.NotificationData
import com.arash.altafi.notification2.models.PushNotification
import com.arash.altafi.notification2.remote.RetrofitInstance
import com.arash.altafi.notification2.service.FirebaseService
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FirebaseActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFirebaseBinding.inflate(layoutInflater)
    }
    private val topic = "/topics/myTopic2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Toast.makeText(this, "If you are in Iran, use VPN. Boycott!!!", Toast.LENGTH_LONG).show()
        init()
    }

    private fun init() = binding.apply {
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

        btnSend.setOnClickListener {
            val title = etTitle.text.toString()
            val message = etMessage.text.toString()
            val image = etImage.text.toString()
            val recipientToken = etToken.text.toString()
            if (title.isNotEmpty() && message.isNotEmpty() && recipientToken.isNotEmpty()) {
                PushNotification(
                    NotificationData(title, message, image),
                    topic
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
            if (title.isNotEmpty() && message.isNotEmpty() && recipientToken.isNotEmpty()) {
                PushNotification(
                    NotificationData(title, message, image),
                    recipientToken
                ).also {
                    sendNotification(it)
                }
            }
        }
    }


    private fun sendNotification(notification: PushNotification) =
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api.postNotification(notification)
                if (response.isSuccessful) {
                    Toast.makeText(this@FirebaseActivity, "Success", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@FirebaseActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@FirebaseActivity, "Exception: $e", Toast.LENGTH_SHORT).show()
                }
            }
        }

}