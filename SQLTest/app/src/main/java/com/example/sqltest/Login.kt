package com.example.sqltest

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sqltest.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var dBhelper: DBhelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dBhelper = DBhelper(this)
        val btnLogin = binding.btnLogin

        btnLogin.setOnClickListener{
            val userName = binding.Email.text.toString()
            val password = binding.Password.text.toString()
            if (userName.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                loginDatabase(userName, password)
            }
        }
        val textSignUp = binding.textSignUp
        textSignUp.setOnClickListener{
            startActivity(Intent(this@Login, Signup::class.java))
            finish()
        }

    }
    private fun loginDatabase(userName:String,password:String){
        val userExists = dBhelper.readUser(userName,password)
        if (userExists){
            Toast.makeText(applicationContext, "Login successful", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@Login, MainActivity::class.java))
            finish()
        }
        else{
            Toast.makeText(applicationContext, "Invalid username or password", Toast.LENGTH_SHORT).show()
        }
    }
}