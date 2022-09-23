package com.arash.altafi.notification2.ui.replay.replay1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arash.altafi.notification2.databinding.ActivityActionButtonOneBinding

class ActionButtonOne : AppCompatActivity() {

    private lateinit var binding: ActivityActionButtonOneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActionButtonOneBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}