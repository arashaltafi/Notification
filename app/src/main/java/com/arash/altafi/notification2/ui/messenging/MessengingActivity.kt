package com.arash.altafi.notification2.ui.messenging

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arash.altafi.notification2.databinding.ActivityMessengingBinding
import com.arash.altafi.notification2.models.ChatData
import com.arash.altafi.notification2.utils.NotificationUtils

class MessengingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMessengingBinding
    private var list: MutableList<ChatData> = mutableListOf()
    private var username: String? = null
    private var message: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessengingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        binding.apply {
            btnNotificationMessenging1.setOnClickListener {
                username = edtUsername.text.toString()
                message = edtMessage.text.toString()
                list.add(ChatData(username, message!!, System.currentTimeMillis()))
                NotificationUtils.messengingNotification(this@MessengingActivity, 1, list)
            }
            btnNotificationMessenging2.setOnClickListener {
                username = edtUsername.text.toString()
                message = edtMessage.text.toString()
                list.add(ChatData(username, message!!, System.currentTimeMillis()))
                NotificationUtils.messengingNotification(this@MessengingActivity, 2, list)
            }
        }
    }
}