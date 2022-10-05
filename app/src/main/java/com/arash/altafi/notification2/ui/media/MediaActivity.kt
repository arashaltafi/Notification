package com.arash.altafi.notification2.ui.media

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arash.altafi.notification2.databinding.ActivityMediaBinding
import com.arash.altafi.notification2.utils.NotificationUtils

class MediaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMediaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        binding.apply {
            btnNotificationMedia1.setOnClickListener {
                NotificationUtils.mediaNotification(
                    this@MediaActivity,
                    "تست عنوان",
                    "تست توضیحات"
                )
            }
        }
    }

}