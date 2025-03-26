package com.example.homeworkapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.homeworkapp.databinding.ActivityLoginBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("Users")
        binding.loginButton.setOnClickListener {
            val username = binding.loginUsername.text.toString()
            val userPassword = binding.loginpassword.text.toString()
            if (username.isNotEmpty() && userPassword.isNotEmpty()) {
                loginUser(username, userPassword)
            } else {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        binding.logintext.setOnClickListener {
            startActivity(Intent(this, signup::class.java))
        }
    }

    private fun loginUser(username: String, userPassword: String) {
        databaseReference.orderByChild("username").equalTo(username)
            .addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(datasnapshot: DataSnapshot) {
                    if (datasnapshot.exists()) {
                        for (userSnapshot in datasnapshot.children) {
                            val userData = userSnapshot.getValue(UserData::class.java)
                            if (userData != null && userData.userpassword == userPassword)
                                Toast.makeText(
                                    this@login,
                                    "Login Successfull",
                                    Toast.LENGTH_SHORT
                                ).show()
                            startActivity(Intent(this@login, MainActivity::class.java))
                            finish()
                        }
                    }

                }
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@login, "Database error", Toast.LENGTH_SHORT).show()
                }
            })
    }
}

