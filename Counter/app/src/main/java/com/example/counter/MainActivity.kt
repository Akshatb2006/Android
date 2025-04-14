package com.example.counter

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.counter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var myViewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        binding.counter.text = myViewModel.getInitialCounter().toString()

        binding.incButton.setOnClickListener {
            binding.counter.text = myViewModel.incrementCounter().toString()
        }
        binding.decButton.setOnClickListener {
                binding.counter.text = myViewModel.decreementCounter().toString()
        }

    }
}