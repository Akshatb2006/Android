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
import com.example.roomprac2.databinding.ActivitySigUpBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SigUp : AppCompatActivity() {

    private lateinit var binding: ActivitySigUpBinding
    private lateinit var database: StudentDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySigUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database= Room.databaseBuilder(
            applicationContext,
            StudentDatabase::class.java,
            "student_database"
        ).build()
        binding.signupbutton.setOnClickListener {
            val username = binding.signupUsername.text.toString()
            val password = binding.signuppassword.text.toString()

            GlobalScope.launch {
                database.studentsDao().insertStudent(Students(name = username, password = password))
//                Toast.makeText(this@SigUp, "Signup successful!", Toast.LENGTH_SHORT).show()
               Log.d("Signup", "Signup successful!")
            }
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}