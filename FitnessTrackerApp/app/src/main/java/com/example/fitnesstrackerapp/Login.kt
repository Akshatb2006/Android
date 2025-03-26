package com.example.fitnesstrackerapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fitnesstrackerapp.databinding.ActivityLoginBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Login : AppCompatActivity() {
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
        binding.loginbutton.setOnClickListener{
            val email = binding.loginUsername.text.toString()
            val password = binding.loginpassword.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()){
                loginUser(email,password)
            }
            else{
                Toast.makeText(this@Login,"All fields are mandatory",Toast.LENGTH_SHORT).show()
            }
            }
        binding.logintext.setOnClickListener{
            val intent = Intent(this,Signup::class.java)
            startActivity(intent)
        }
    }
    private fun loginUser(email:String, password:String){
        databaseReference.orderByChild("email").equalTo(email)
            .addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(datasnapshot: DataSnapshot) {
                    if (datasnapshot.exists()) {
                        for (userSnapshot in datasnapshot.children) {
                            val userData = userSnapshot.getValue(UserData::class.java)
                            if (userData != null && userData.password == password)
                                Toast.makeText(
                                    this@Login,
                                    "Login Successfull",
                                    Toast.LENGTH_SHORT
                                ).show()
                            startActivity(Intent(this@Login, MainActivity::class.java))
                        }
                    }

                }
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@Login, "Database error", Toast.LENGTH_SHORT).show()
                }
            })
    }
}

