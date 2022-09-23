package com.arash.altafi.notification2.ui.replay.replay1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arash.altafi.notification2.databinding.ActivityActionButtonTwoBinding

class ActionButtonTwo : AppCompatActivity() {

    private lateinit var binding: ActivityActionButtonTwoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActionButtonTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}