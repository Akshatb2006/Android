package com.example.fitnesstrackerapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fitnesstrackerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bottomNavigationView = binding.navbaritem
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.workout -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentcontainer,workoutFragment())
                    true
                }
                R.id.progress -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentcontainer, ProgressFragment())
                    true
                }
                R.id.goal -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentcontainer, goalFragment())
                    true
                }
                else -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentcontainer, workoutFragment())
                    true
                }
            }
                }

    }
}