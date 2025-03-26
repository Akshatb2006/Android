package com.example.hershield

import android.Manifest
import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PowerManager
import android.provider.ContactsContract
import android.provider.Settings
import android.telephony.SmsManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import android.content.BroadcastReceiver
import android.app.Service
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.IBinder
import kotlin.math.sqrt

class MainActivity<LinearLayout : View?> : AppCompatActivity() {

    private val REQUEST_CALL_PERMISSION = 1
    private val REQUEST_LOCATION_PERMISSION = 2
    private val REQUEST_SMS_PERMISSION = 3
    private val IGNORE_BATTERY_OPTIMIZATION_REQUEST = 1002
    private val PICK_CONTACT = 4

    private var currentNumber: String = ""
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    // SOS feature variables
    private var isSOSActive = false
    private val handler = Handler(Looper.getMainLooper())
    private var locationUpdateRunnable: Runnable? = null
    private var flashAlertRunnable: Runnable? = null

    // Contacts variables
    private lateinit var addContactButton: Button
    private lateinit var listView: ListView
    private lateinit var db: DbHelper
    private lateinit var contactsList: List<ContactModel>
    private lateinit var customAdapter: CustomAdapter

    // Main view and shake detection view
    private lateinit var mainView: View
    private lateinit var shakeView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize main view (default view)
        mainView = findViewById(R.id.main_view)
        shakeView = findViewById(R.id.shake_view)

        // Initially hide the shake view
        shakeView.visibility = View.GONE
        mainView.visibility = View.VISIBLE

        // Initialize location client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Request runtime permissions
        checkAndRequestPermissions()

        // Check for battery optimization settings
        checkBatteryOptimization()

        // Initialize UI components from main view
        val policeButton = findViewById<ImageView>(R.id.police_button)
        val womenSafetyButton = findViewById<ImageView>(R.id.women_safety_button)
        val sosButton = findViewById<Button>(R.id.sos)
        val mapButton = findViewById<ImageView>(R.id.map)
        val shakeButton = findViewById<Button>(R.id.shake_button)
        val trustedContactsButton = findViewById<LinearLayout>(R.id.shake)

        // Initialize UI components from shake view
        addContactButton = findViewById(R.id.add_contact_button)
        listView = findViewById(R.id.contacts_list_view)

        // Initialize contacts database
        setupContactsList()

        // Start shake detection service
        startShakeService()

        // Set click listeners for main view buttons
        mapButton.setOnClickListener {
            val url = "https://aquamarine-croissant-fc4c7b.netlify.app/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        policeButton.setOnClickListener {
            currentNumber = "100"
            makeEmergencyCall(currentNumber)
            sendLocationMessage(currentNumber)
        }

        womenSafetyButton.setOnClickListener {
            currentNumber = "1091"
            makeEmergencyCall(currentNumber)
            sendLocationMessage(currentNumber)
        }

        sosButton.setOnClickListener {
            if (!isSOSActive) {
                activateSOS()
            } else {
                deactivateSOS()
            }
        }

        // Button to switch to shake view (both the layout and the button)
        shakeButton.setOnClickListener {
            mainView.visibility = View.GONE
            shakeView.visibility = View.VISIBLE
        }

        trustedContactsButton?.setOnClickListener {
            mainView.visibility = View.GONE
            shakeView.visibility = View.VISIBLE
        }

        // Add contact button in shake view
        addContactButton.setOnClickListener {
            if (db.count() < 5) {
                val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
                startActivityForResult(intent, PICK_CONTACT)
            } else {
                Toast.makeText(this, "Can't add more than 5 contacts", Toast.LENGTH_SHORT).show()
            }
        }

        // Back button in shake view
        val backButton = findViewById<Button>(R.id.back_button)
        backButton.setOnClickListener {
            mainView.visibility = View.VISIBLE
            shakeView.visibility = View.GONE
        }

        // Check if app was started with SOS activation intent
        if (intent.getBooleanExtra("ACTIVATE_SOS", false)) {
            activateSOS()
        }
    }

    private fun checkAndRequestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permissions = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.SEND_SMS,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_CONTACTS
            )

            ActivityCompat.requestPermissions(this, permissions, 100)

            // Check for background location on Android Q and above
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION), 101)
            }
        }
    }

    private fun checkBatteryOptimization() {
        val pm = getSystemService(POWER_SERVICE) as PowerManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                askIgnoreOptimization()
            }
        }
    }

    private fun askIgnoreOptimization() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            @SuppressLint("BatteryLife")
            val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
            intent.data = Uri.parse("package:$packageName")
            startActivityForResult(intent, IGNORE_BATTERY_OPTIMIZATION_REQUEST)
        }
    }

    private fun setupContactsList() {
        db = DbHelper(this)
        contactsList = db.getAllContacts()
        customAdapter = CustomAdapter(this, contactsList)
        listView.adapter = customAdapter
    }

    private fun startShakeService() {
        val sensorService = SensorService()
        val intent = Intent(this, sensorService.javaClass)
        if (!isMyServiceRunning(sensorService.javaClass)) {
            startService(intent)
        }
    }

    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                Log.i("Service status", "Running")
                return true
            }
        }
        Log.i("Service status", "Not running")
        return false
    }

    // Function to handle SOS activation
    private fun activateSOS() {
        isSOSActive = true

        // Change SOS button text to indicate it's active
        val sosButton = findViewById<Button>(R.id.sos)
        sosButton.text = "CANCEL SOS"
        sosButton.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_red_light))

        // Use emergency contacts from database or default to first contact
        currentNumber = if (contactsList.isNotEmpty()) {
            contactsList[0].phoneNo
        } else {
            "100" // Default to police emergency number if no contacts added
        }

        // Send initial alert notification
        sendSOS(currentNumber)

        // Start location updates every 30 seconds
        startLocationUpdates()

        // Start flash alert signal
        startFlashAlert()

        Toast.makeText(this, "SOS Activated! Alerting emergency contacts", Toast.LENGTH_LONG).show()
    }

    private fun deactivateSOS() {
        isSOSActive = false

        // Reset SOS button
        val sosButton = findViewById<Button>(R.id.sos)
        sosButton.text = "SOS Emergency"
        sosButton.setBackgroundColor(resources.getColor(R.color.yellow_sos, theme))

        // Stop location updates
        locationUpdateRunnable?.let { handler.removeCallbacks(it) }

        // Stop flash alert
        flashAlertRunnable?.let { handler.removeCallbacks(it) }

        // Send SMS to notify emergency contact that SOS is cancelled
        val message = "SOS Alert CANCELLED. I am safe now."
        for (contact in contactsList) {
            sendSms(contact.phoneNo, message)
        }

        Toast.makeText(this, "SOS Deactivated", Toast.LENGTH_SHORT).show()
    }

    private fun sendSOS(number: String) {
        val message = "EMERGENCY SOS ALERT! I need immediate help!"
        sendSms(number, message)
        makeEmergencyCall(number)
    }

    private fun startLocationUpdates() {
        locationUpdateRunnable = object : Runnable {
            override fun run() {
                if (isSOSActive) {
                    // Send location to all emergency contacts
                    for (contact in contactsList) {
                        sendLocationMessage(contact.phoneNo)
                    }
                    handler.postDelayed(this, 30000) // 30 seconds interval
                }
            }
        }
        handler.post(locationUpdateRunnable!!)
    }

    private fun startFlashAlert() {
        flashAlertRunnable = object : Runnable {
            override fun run() {
                if (isSOSActive) {
                    // Send flash alert to all emergency contacts
                    for (contact in contactsList) {
                        sendFlashAlert(contact.phoneNo)
                    }
                    handler.postDelayed(this, 60000) // 1 minute interval
                }
            }
        }
        handler.post(flashAlertRunnable!!)
    }

    private fun sendFlashAlert(number: String) {
        val message = "FLASH ALERT: If you have the companion app, your phone flashlight will blink to indicate an emergency."
        sendSms(number, message)
    }

    private fun makeEmergencyCall(number: String) {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CALL_PHONE),
                REQUEST_CALL_PERMISSION
            )
        } else {
            startCall(number)
        }
    }

    private fun startCall(number: String) {
        val dial = "tel:$number"
        val intent = Intent(Intent.ACTION_CALL, Uri.parse(dial))

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "Failed to make call", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendLocationMessage(number: String) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION)
        } else {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    val mapsLink = "https://maps.google.com/?q=$latitude,$longitude"

                    val enhancedMessage = if (isSOSActive) {
                        "EMERGENCY SOS! My current location: $mapsLink\nTimestamp: ${System.currentTimeMillis()}"
                    } else {
                        "Emergency! My current location: $mapsLink"
                    }

                    sendSms(number, enhancedMessage)
                } else {
                    Toast.makeText(this, "Failed to get location", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun sendSms(number: String, message: String) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.SEND_SMS),
                REQUEST_SMS_PERMISSION
            )
        } else {
            try {
                val smsManager = SmsManager.getDefault()
                val parts = smsManager.divideMessage(message)
                smsManager.sendMultipartTextMessage(number, null, parts, null, null)
                Toast.makeText(this, "Emergency message sent", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this, "Failed to send message: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("Range")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            PICK_CONTACT -> {
                if (resultCode == RESULT_OK && data != null) {
                    val contactData = data.data
                    val cursor = managedQuery(contactData, null, null, null, null)

                    if (cursor.moveToFirst()) {
                        val id = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID))
                        val hasPhone = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))

                        if (hasPhone == "1") {
                            val phones = contentResolver.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                                null,
                                null
                            )

                            if (phones != null && phones.moveToFirst()) {
                                val phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

                                db.addContact(ContactModel(0, name, phoneNumber))
                                contactsList = db.getAllContacts()
                                customAdapter.refresh(contactsList)

                                phones.close()
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_CALL_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startCall(currentNumber)
                } else {
                    Toast.makeText(this, "Permission denied to make calls", Toast.LENGTH_SHORT).show()
                }
            }
            REQUEST_LOCATION_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendLocationMessage(currentNumber)
                } else {
                    Toast.makeText(this, "Permission denied to access location", Toast.LENGTH_SHORT).show()
                }
            }
            REQUEST_SMS_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendSms(currentNumber, "Emergency! Need help!")
                } else {
                    Toast.makeText(this, "Permission denied to send SMS", Toast.LENGTH_SHORT).show()
                }
            }
            100 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, "Permissions Denied!\n Can't use the App!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroy() {
        // Restart shake service if it gets killed
        val broadcastIntent = Intent()
        broadcastIntent.action = "restartservice"
        broadcastIntent.setClass(this, ReactivateService::class.java)
        this.sendBroadcast(broadcastIntent)

        // Clean up handlers to prevent memory leaks
        if (isSOSActive) {
            deactivateSOS()
        }

        super.onDestroy()
    }
}

// Shake detection service class
class SensorService : Service() {
    private lateinit var mSensorManager: SensorManager
    private lateinit var mAccelerometer: Sensor
    private var mAcceleration = 0f
    private var mAccelerationCurrent = 0f
    private var mAccelerationLast = 0f

    override fun onCreate() {
        super.onCreate()
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!!
        mSensorManager.registerListener(
            sensorListener, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    private val sensorListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            mAccelerationLast = mAccelerationCurrent
            mAccelerationCurrent = sqrt(x * x + y * y + z * z)
            val delta = mAccelerationCurrent - mAccelerationLast
            mAcceleration = mAcceleration * 0.9f + delta

            // If acceleration is above threshold, trigger SOS
            if (mAcceleration > 12) {
                // Start MainActivity with SOS intent
                val intent = Intent(applicationContext, MainActivity::class.java)
                intent.putExtra("ACTIVATE_SOS", true)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mSensorManager.unregisterListener(sensorListener)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}

// Service to restart the shake detection if killed
class ReactivateService : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "restartservice") {
            context.startService(Intent(context, SensorService::class.java))
        }
    }
}