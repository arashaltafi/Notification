package com.arash.altafi.notification2.ui.media

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arash.altafi.notification2.databinding.ActivityMediaBinding
import com.arash.altafi.notification2.ui.media.media1.Media1Activity
import com.arash.altafi.notification2.ui.media.media2.Media2Activity

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
            btnMedia1.setOnClickListener {
                startActivity(Intent(this@MediaActivity, Media1Activity::class.java))
            }
            btnMedia2.setOnClickListener {
                startActivity(Intent(this@MediaActivity, Media2Activity::class.java))
            }
        }
    }

}