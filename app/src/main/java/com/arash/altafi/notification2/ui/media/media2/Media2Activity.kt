package com.arash.altafi.notification2.ui.media.media2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arash.altafi.notification2.databinding.ActivityMedia2Binding

class Media2Activity : AppCompatActivity() {

    private lateinit var binding: ActivityMedia2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMedia2Binding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}