package com.arash.altafi.notification2.ui.replay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arash.altafi.notification2.databinding.ActivityReplayBinding
import com.arash.altafi.notification2.ui.replay.replay1.SampleReplayActivity1
import com.arash.altafi.notification2.ui.replay.replay2.SampleReplayActivity2
import com.arash.altafi.notification2.ui.replay.replay3.SampleReplayActivity3

class ReplayActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReplayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReplayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        binding.apply {
            btnNotificationReplay1.setOnClickListener {
                startActivity(Intent(this@ReplayActivity, SampleReplayActivity1::class.java))
            }
            btnNotificationReplay2.setOnClickListener {
                startActivity(Intent(this@ReplayActivity, SampleReplayActivity2::class.java))
            }
            btnNotificationReplay3.setOnClickListener {
                startActivity(Intent(this@ReplayActivity, SampleReplayActivity3::class.java))
            }
        }
    }

}