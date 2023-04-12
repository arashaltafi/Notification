package com.arash.altafi.notification2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arash.altafi.notification2.R

class CloseAppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_close_app)

        finishAffinity() // Close all activities in the task, and exit the app
    }
}