package com.example.showvideo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.showvideo.databinding.ActivityLoginBinding
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallConfig
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService

class Login : AppCompatActivity() {
    private lateinit var usernameinput : EditText
    private lateinit var loginbutton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        usernameinput = findViewById(R.id.emailInput)
        loginbutton = findViewById(R.id.loginButton)

        usernameinput = findViewById(R.id.emailInput)
        loginbutton = findViewById(R.id.loginButton)

        loginbutton.setOnClickListener {
            val username = usernameinput.text.toString()
            val config = ZegoUIKitPrebuiltCallInvitationConfig()

            ZegoUIKitPrebuiltCallInvitationService.init(
                application,
                Constants.appId,
                Constants.appSign,
                username,
                username,
                config
            )
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("username", username)
            startActivity(intent)
            finish()

        }



        }
    }