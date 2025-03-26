package com.example.sqllite

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sqllite.databinding.ActivitySignUpBinding

class SignUp : AppCompatActivity() {
    private lateinit var DBHelper: DBHelper
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        DBHelper = DBHelper(this)

        val signupbtn = binding.signupButton

        signupbtn.setOnClickListener {
            val username = binding.signupemail.text.toString()
            val password = binding.signuppassword.text.toString()
            signupDatabase(username,password)
        }
        val logintext = binding.signuptext
        logintext.setOnClickListener {
            startActivity(Intent(this@SignUp,Login::class.java))
            finish()
        }

    }

    private fun signupDatabase(username:String,password:String){
        val insertedRowId = DBHelper.insertUser(username, password)
        if (insertedRowId != -1L) {
            Toast.makeText(applicationContext, "User registered successfully", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@SignUp,Login::class.java))
            finish()
        }
        else{
            Toast.makeText(applicationContext, "Registration failed", Toast.LENGTH_SHORT).show()
        }
    }
}