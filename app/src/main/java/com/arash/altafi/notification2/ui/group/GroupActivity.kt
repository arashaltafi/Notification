package com.arash.altafi.notification2.ui.group

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arash.altafi.notification2.databinding.ActivityGroupBinding
import com.arash.altafi.notification2.utils.NotificationUtils

class GroupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGroupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        binding.apply {
            btnNotificationGroup1.setOnClickListener {
                NotificationUtils.test(this@GroupActivity, 1)
            }
            btnNotificationGroup2.setOnClickListener {
                NotificationUtils.test(this@GroupActivity, 2)
            }
        }
    }

}