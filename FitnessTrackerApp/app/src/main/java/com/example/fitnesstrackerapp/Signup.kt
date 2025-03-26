package com.example.fitnesstrackerapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fitnesstrackerapp.databinding.ActivitySignupBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Signup : AppCompatActivity() {
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

        binding.signupButton.setOnClickListener{
            val email = binding.loginUsername.text.toString()
            val password = binding.signuppassword.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                signupUser(email, password)
            } else {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show()
            }
        }
        binding.signuptext.setOnClickListener{
            val intent = Intent(this@Signup,Login::class.java)
            startActivity(intent)
        }
    }
    private fun signupUser(email: String, password: String) {
        databaseReference.child("users").equalTo(email).addListenerForSingleValueEvent(object:
            ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                if (!datasnapshot.exists()){
                    val id = databaseReference.push().key
                    val userData = UserData(id,email,password)
                    databaseReference.child(id!!).setValue(userData)
                    Toast.makeText(this@Signup,"Signed Up",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@Signup,Login::class.java))
                    finish()
                }
                else{
                    Toast.makeText(this@Signup,"User Already Exist",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Signup,"Database error",Toast.LENGTH_SHORT).show()
            }
        })
    }
}