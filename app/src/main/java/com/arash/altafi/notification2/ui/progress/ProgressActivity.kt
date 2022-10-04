package com.arash.altafi.notification2.ui.progress

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.arash.altafi.notification2.databinding.ActivityProgressBinding
import com.arash.altafi.notification2.utils.Constants.CHANNEL_ID
import com.arash.altafi.notification2.utils.NotificationUtils

class ProgressActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProgressBinding
    private var progressPercent = 0
    private var notificationUploadID = 1
    private var notificationDownloadID = 10000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProgressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        binding.apply {
            btnNotificationProgressUpload.setOnClickListener {
                notificationUploadID++
                timerUpload(notificationUploadID)
            }
            btnNotificationProgressDownload.setOnClickListener {
                notificationDownloadID++
                timerDownload(notificationDownloadID)
            }
        }
    }

    private fun timerUpload(notificationID: Int) {
        val liveTime = 10000L
        object : CountDownTimer(liveTime, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                progressPercent = ((liveTime - millisUntilFinished) * 1200 / liveTime / 10f).toInt()
                NotificationUtils.progressNotification(
                    notificationID,
                    this@ProgressActivity,
                    CHANNEL_ID,
                    progress = progressPercent,
                    false
                )
            }
            override fun onFinish() {
                progressPercent = 100
            }
        }.start()
    }

    private fun timerDownload(notificationID: Int) {
        val liveTime = 10000L
        object : CountDownTimer(liveTime, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                progressPercent = ((liveTime - millisUntilFinished) * 1200 / liveTime / 10f).toInt()
                NotificationUtils.progressNotification(
                    notificationID,
                    this@ProgressActivity,
                    CHANNEL_ID,
                    progress = progressPercent,
                    true
                )
            }
            override fun onFinish() {
                progressPercent = 100
            }
        }.start()
    }
}