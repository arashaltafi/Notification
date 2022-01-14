package com.arash.altafi.notification.sample2

import android.app.NotificationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arash.altafi.notification.R

class TestActivity : AppCompatActivity() {

    var cartNotifManager: NotificationManager ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        init()
    }

    private fun init() {
        cartNotifManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        cartNotifManager!!.cancel(2)
    }

}