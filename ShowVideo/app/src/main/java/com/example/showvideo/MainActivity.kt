package com.example.showvideo

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoSendCallInvitationButton
import com.zegocloud.uikit.service.defines.ZegoUIKitUser
import java.util.Collections

class MainActivity : AppCompatActivity() {

    lateinit var currentusernametextview : TextView
    lateinit var targetusernameinput : EditText
    lateinit var voice_call_btn : ZegoSendCallInvitationButton
    lateinit var video_call_btn : ZegoSendCallInvitationButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        currentusernametextview = findViewById(R.id.current_username_textview)
        targetusernameinput = findViewById(R.id.taget_username_input)
        voice_call_btn = findViewById(R.id.voice_call_btn)
        video_call_btn = findViewById(R.id.video_call_btn)

        val username = intent.getStringExtra("username")
        currentusernametextview.text = username

        targetusernameinput.addTextChangedListener {
            val targetusername = targetusernameinput.text.toString()
            setupvoicecall(targetusername)
            setupvideocall(targetusername)
        }

    }

    fun setupvoicecall(username : String){
        voice_call_btn.setIsVideoCall(false)
        voice_call_btn.resourceID = "zego_uikit_call"
        voice_call_btn.setInvitees(Collections.singletonList(ZegoUIKitUser(username,username)))
    }

    fun setupvideocall(username : String){
        video_call_btn.setIsVideoCall(true)
        video_call_btn.resourceID = "zego_uikit_call"
        video_call_btn.setInvitees(Collections.singletonList(ZegoUIKitUser(username,username)))
    }
}
