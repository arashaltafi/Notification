package com.arash.altafi.notification2.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.arash.altafi.notification2.databinding.ActivityMainBinding
import com.arash.altafi.notification2.models.NotificationData
import com.arash.altafi.notification2.models.PushNotification
import com.arash.altafi.notification2.pushy.RegisterForPushNotificationsAsync
import com.arash.altafi.notification2.remote.RetrofitInstance
import com.arash.altafi.notification2.ui.group.GroupActivity
import com.arash.altafi.notification2.ui.replay.ReplayActivity
import com.arash.altafi.notification2.utils.Constants
import com.arash.altafi.notification2.service.FirebaseService
import com.arash.altafi.notification2.ui.messenging.MessengingActivity
import com.arash.altafi.notification2.ui.progress.ProgressActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.pushy.sdk.Pushy

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val topic = "/topics/myTopic2"
    private var deviceToken: String? = null
    private lateinit var registerForPushNotificationsAsync: RegisterForPushNotificationsAsync

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        if (extras?.getBoolean("NotClick") == true) {
            Toast.makeText(this, "test", Toast.LENGTH_SHORT).show()
        }

        pushy()

        btnTestCrashlytics.setOnClickListener {
            throw RuntimeException("Test Crash")
        }

        btnPushyNotification.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://arashaltafi.ir/pushy/")))
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

        btnTestGroup.setOnClickListener {
            startActivity(Intent(this, GroupActivity::class.java))
        }

        btnTestReplay.setOnClickListener {
            startActivity(Intent(this, ReplayActivity::class.java))
        }

        btnTestMessenging.setOnClickListener {
            startActivity(Intent(this, MessengingActivity::class.java))
        }

        btnTestProgress.setOnClickListener {
            startActivity(Intent(this, ProgressActivity::class.java))
        }

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

        btnSendS21FE.setOnClickListener {
            val title = etTitle.text.toString()
            val message = etMessage.text.toString()
            val image = etImage.text.toString()
            val recipientToken = etToken.text.toString()
            if (title.isNotEmpty() && message.isNotEmpty() && recipientToken.isNotEmpty()) {
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
            if (title.isNotEmpty() && message.isNotEmpty() && recipientToken.isNotEmpty()) {
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

    private fun pushy() {
        //get data
        val id = intent.getIntExtra("id", 0)
        val message = intent.getStringExtra("message")
        val success = intent.getBooleanExtra("success", false)
        Log.i("test123321", "pushy: id = $id")
        Log.i("test123321", "pushy: message = $message")
        Log.i("test123321", "pushy: success = $success")

        //register
        if (!Pushy.isRegistered(this)) {
            RegisterForPushNotificationsAsync(this).execute()
        } else {
            // Start Pushy notification service if not already running
            Pushy.listen(this)

            //create topic
//            Pushy.subscribe("new_topic", this)

            // Enable FCM Fallback Delivery
            // Pushy.toggleFCM(true, this)

            // Get device token from SharedPreferences
            registerForPushNotificationsAsync = RegisterForPushNotificationsAsync(this)
            deviceToken = registerForPushNotificationsAsync.getDeviceToken()
            Log.i("test123321", "pushy token: $deviceToken")
        }
    }

    private fun sendNotification(notification: PushNotification) =
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api.postNotification(notification)
                if (response.isSuccessful) {
                    Log.d("test123321", "Response Successful: ${Gson().toJson(response)}")
                } else {
                    Log.e("test123321", "Response Error: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("test123321", "Exception: $e")
            }
        }
}