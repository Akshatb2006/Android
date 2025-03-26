package com.example.sharedprefs

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sharedprefs.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("Notes", Context.MODE_PRIVATE)

        binding.mysubmittextbutton.setOnClickListener{
            binding.myedittext.text.toString()
            val note = binding.myedittext.text.toString()
            val data = sharedPreferences.edit()
            data.putString("key",note)
            data.apply()
            Toast.makeText(applicationContext,"Data Saved",Toast.LENGTH_SHORT).show()
            binding.myedittext.text?.clear()
        }
        binding.myviewtextbutton.setOnClickListener{
            val storedData = sharedPreferences.getString("key","There's No data")
            binding.mytextview.text = "$storedData"
        }
    }

}
