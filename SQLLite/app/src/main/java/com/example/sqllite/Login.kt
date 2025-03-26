package com.example.sqllite

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sqllite.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var dBHelper: DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dBHelper = DBHelper(this)
        val loginbtn = binding.loginButton

        loginbtn.setOnClickListener {
            val username = binding.loginUsername.text.toString()
            val password = binding.loginpassword.text.toString()
            loginDatabase(username, password)
        }
        val signuptext = binding.logintext
        signuptext.setOnClickListener {
            startActivity(Intent(this@Login, SignUp::class.java))
            finish()
        }
}

        private fun loginDatabase(username:String,password:String) {
            val userExists = dBHelper.readUser(username, password)

            if (userExists) {
                Toast.makeText(applicationContext, "Login successful", Toast.LENGTH_SHORT).show()
                val intent = Int
                finish()
            } else {
                Toast.makeText(applicationContext, "Login failed", Toast.LENGTH_SHORT).show()
            }
        }
}