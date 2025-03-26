package com.example.music

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var startbtn:Button
    private lateinit var stopbtn:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startbtn=findViewById(R.id.myStartbtn)
        stopbtn=findViewById(R.id.myStopbtn)

        startbtn.setOnClickListener{
            startService(Intent(this@MainActivity,RingtoneService::class.java))
        }
        stopbtn.setOnClickListener{
            stopService(Intent(this@MainActivity,RingtoneService::class.java))
        }
    }
}