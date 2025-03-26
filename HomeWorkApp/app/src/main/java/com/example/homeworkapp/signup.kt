package com.example.homeworkapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.homeworkapp.databinding.ActivitySignupBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class signup : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("Users")

        binding.signupButton.setOnClickListener {
            val username = binding.signupemail.text.toString()
            val userPassword = binding.signuppassword.text.toString()
            if (username.isNotEmpty() && userPassword.isNotEmpty()) {
                signupUser(username, userPassword)
            } else {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show()
            }
        }
        binding.mysignup.setOnClickListener{
            startActivity(Intent(this,login::class.java))
        }
    }
    private fun signupUser(username: String, userPassword: String) {
        databaseReference.child("username").equalTo(username).addListenerForSingleValueEvent(object:
            ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                if (!datasnapshot.exists()){
                    val id = databaseReference.push().key
                    val userData = UserData(id,userPassword,username)
                    databaseReference.child(id!!).setValue(userData)
                    Toast.makeText(this@signup,"Signed Up",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@signup,login::class.java))
                    finish()
                }
                else{
                    Toast.makeText(this@signup,"User Already Exist",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@signup,"Database error",Toast.LENGTH_SHORT).show()
            }
        })
    }
}