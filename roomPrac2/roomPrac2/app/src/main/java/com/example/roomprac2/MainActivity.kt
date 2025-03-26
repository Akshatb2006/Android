package com.example.roomprac2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.example.roomprac2.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: StudentDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityMainBinding.inflate(layoutInflater)
        database= Room.databaseBuilder(
            applicationContext,
            StudentDatabase::class.java,
            "student_database"
        ).build()
        setContentView(binding.root)




        binding.loginbutton.setOnClickListener {
            val username = binding.loginusername.text.toString()
            val password = binding.loginpassword.text.toString()

            // Validate inputs
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show()

            }else{
                var isValid=false
                GlobalScope.launch {
                    isValid = database.studentsDao().validateCredentials(username, password)

                    runOnUiThread {

                        if (isValid) {
                            Toast.makeText(this@MainActivity, "Login successful!", Toast.LENGTH_SHORT).show()
                            // Navigate to the next screen after successful login
                            startActivity(Intent(this@MainActivity, MainActivity2::class.java))
                        } else {
                            Toast.makeText(this@MainActivity, "Invalid username or password", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            }

            // Use lifecycleScope instead of GlobalScope

        }


    }
}