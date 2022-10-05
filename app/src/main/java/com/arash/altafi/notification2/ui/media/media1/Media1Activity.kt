package com.arash.altafi.notification2.ui.media.media1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arash.altafi.notification2.databinding.ActivityMedia1Binding
import com.arash.altafi.notification2.utils.NotificationUtils

class Media1Activity : AppCompatActivity() {

    private lateinit var binding: ActivityMedia1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMedia1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        binding.apply {
            btnNotificationMedia1.setOnClickListener {
                NotificationUtils.mediaNotification(
                    this@Media1Activity,
                    "تست عنوان",
                    "تست توضیحات"
                )
            }
        }
    }

}