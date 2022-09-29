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
            val list1 = arrayListOf("1 1", "1 2", "1 3", "1 4", "1 5", "1 6")
            val list2 = arrayListOf("2 1", "2 2", "2 3", "2 4", "2 5", "2 6")
            btnNotificationGroup1.setOnClickListener {
                list1.add("1 7")
                NotificationUtils.testGroup(this@GroupActivity, list1,1)
            }
            btnNotificationGroup2.setOnClickListener {
                list2.add("2 7")
                NotificationUtils.testGroup(this@GroupActivity, list2,2)
            }
        }
    }

}