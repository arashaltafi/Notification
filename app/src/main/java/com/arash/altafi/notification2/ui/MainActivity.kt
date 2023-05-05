package com.arash.altafi.notification2.ui

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.arash.altafi.notification2.databinding.ActivityMainBinding
import com.arash.altafi.notification2.ui.group.GroupActivity
import com.arash.altafi.notification2.ui.replay.ReplayActivity
import com.arash.altafi.notification2.ui.firebase.FirebaseActivity
import com.arash.altafi.notification2.ui.media.MediaActivity
import com.arash.altafi.notification2.ui.messenging.MessengingActivity
import com.arash.altafi.notification2.ui.progress.ProgressActivity
import com.arash.altafi.notification2.utils.NotificationUtils
import com.arash.altafi.notification2.utils.PermissionUtils
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val topic = "/topics/myTopic2"

    private val registerNotificationResult = PermissionUtils.register(this,
        object : PermissionUtils.PermissionListener {
            override fun observe(permissions: Map<String, Boolean>) {
            }
        })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val extras = intent.extras
        if (extras?.getBoolean("NotClick") == true) {
            Toast.makeText(this, "test", Toast.LENGTH_SHORT).show()
        }

        requestPermissionNotification()
        init()
    }

    private fun init() = binding.apply {
        FirebaseMessaging.getInstance().subscribeToTopic(topic)

        btnTestSimple.setOnClickListener {
            NotificationUtils.testNotification(
                this@MainActivity,
                5,
                "test title",
                "test description",
                "https://arashaltafi.ir/arash.jpg"
            )
        }

        btnTestGroup.setOnClickListener {
            startActivity(Intent(this@MainActivity, GroupActivity::class.java))
        }

        btnTestReplay.setOnClickListener {
            startActivity(Intent(this@MainActivity, ReplayActivity::class.java))
        }

        btnTestCustom.setOnClickListener {
            NotificationUtils.sendCustomNotification(this@MainActivity, 101)
        }

        btnTestMessenging.setOnClickListener {
            startActivity(Intent(this@MainActivity, MessengingActivity::class.java))
        }

        btnTestProgress.setOnClickListener {
            startActivity(Intent(this@MainActivity, ProgressActivity::class.java))
        }

        btnTestMedia.setOnClickListener {
            startActivity(Intent(this@MainActivity, MediaActivity::class.java))
        }

        btnTestFirebase.setOnClickListener {
            startActivity(Intent(this@MainActivity, FirebaseActivity::class.java))
        }
    }

    private fun requestPermissionNotification() {
        if (!PermissionUtils.isGranted(this, Manifest.permission.POST_NOTIFICATIONS)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                PermissionUtils.requestPermission(
                    this, registerNotificationResult,
                    Manifest.permission.POST_NOTIFICATIONS
                )
            }
        }
    }
}