package com.arash.altafi.notification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arash.altafi.notification.sample1.Sample1
import com.arash.altafi.notification.sample2.Sample2
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        btn_sample_one.setOnClickListener {
            startActivity(Intent(this , Sample1::class.java))
        }
        btn_sample_two.setOnClickListener {
            startActivity(Intent(this , Sample2::class.java))
        }
    }

}