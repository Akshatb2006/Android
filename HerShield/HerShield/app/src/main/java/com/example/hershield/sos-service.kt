package com.example.hershield

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import android.net.Uri
import android.widget.Toast
import android.telephony.SmsManager
import com.example.hershield.MainActivity

class SOSService : Service() {
    private val NOTIFICATION_ID:Int = 1001
    private val CHANNEL_ID = "sos_channel"

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val handler = Handler(Looper.getMainLooper())
    private var locationUpdateRunnable: Runnable? = null
    private var flashAlertRunnable: Runnable? = null

    private val EMERGENCY_CONTACT_NUMBER = "8240169929" // Same as MainActivity
    private val EMERGENCY_CONTACT_NAME = "Emergency Contact"

    override fun onCreate() {
        super.onCreate()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("SOS Emergency Active")
            .setContentText("Sending location updates to your emergency contact")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)
            .build()

//        startForeground(NOTIFICATION_ID, notification)

        // Start SOS features
        startLocationUpdates()
        startFlashAlert()

        return START_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "SOS Alert Channel"
            val descriptionText = "Channel for SOS emergency alerts"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun startLocationUpdates() {
        locationUpdateRunnable = object : Runnable {
            override fun run() {
                sendLocationMessage(EMERGENCY_CONTACT_NUMBER)
                handler.postDelayed(this, 30000) // 30 seconds interval
            }
        }
        handler.post(locationUpdateRunnable!!)
    }

    private fun startFlashAlert() {
        flashAlertRunnable = object : Runnable {
            override fun run() {
                sendFlashAlert(EMERGENCY_CONTACT_NUMBER)
                handler.postDelayed(this, 60000) // 1 minute interval
            }
        }
        handler.post(flashAlertRunnable!!)
    }

    private fun sendLocationMessage(number: String) {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                val latitude = location.latitude
                val longitude = location.longitude
                val mapsLink = "https://maps.google.com/?q=$latitude,$longitude"
                val message = "EMERGENCY SOS! My current location: $mapsLink\nTimestamp: ${System.currentTimeMillis()}"

                sendSms(number, message)
            }
        }
    }

    private fun sendFlashAlert(number: String) {
        val message = "FLASH ALERT: If you have the companion app, your phone flashlight will blink to indicate an emergency."
        sendSms(number, message)
    }

    private fun sendSms(number: String, message: String) {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.SEND_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        try {
            val smsManager = SmsManager.getDefault()
            val parts = smsManager.divideMessage(message)
            smsManager.sendMultipartTextMessage(number, null, parts, null, null)
        } catch (e: Exception) {
            // Log error - cannot show Toast from a service reliably
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        locationUpdateRunnable?.let { handler.removeCallbacks(it) }
        flashAlertRunnable?.let { handler.removeCallbacks(it) }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}