package com.example.sqltest

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sqltest.databinding.ActivitySignupBinding

class Signup : AppCompatActivity() {
    private lateinit var DBhelper:DBhelper
    private lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        DBhelper = DBhelper(this)

        val btnSignUp = binding.btnSignUp

        btnSignUp.setOnClickListener{
            val userName = binding.signupEmail.text.toString()
            val password = binding.signupPassword.text.toString()
            if (userName.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                signupDatabase(userName, password)
            }
        }
        val textLogin = binding.textLogin
        textLogin.setOnClickListener{
            startActivity(Intent(this@Signup, Login::class.java))
            finish()
        }

    }
    private fun signupDatabase(userName:String,password:String){
        val insertUser = DBhelper.insertUser(userName,password)
        if (insertUser != -1L){
            Toast.makeText(applicationContext, "User registered successfully", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@Signup, Login::class.java))
            finish()
        }
        else{
            Toast.makeText(applicationContext, "User registration failed", Toast.LENGTH_SHORT).show()
        }
    }
}