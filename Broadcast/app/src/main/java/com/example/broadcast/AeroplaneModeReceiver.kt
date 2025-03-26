package com.example.broadcast
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AeroplaneModeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val isOn = intent?.getBooleanExtra("state", false)
        if (isOn == true) {
            Toast.makeText(context, "Aeroplane Mode Enabled", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Aeroplane Mode Disabled", Toast.LENGTH_SHORT).show()
        }
    }
}