package com.example.addcal.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.addcal.R
import com.example.addcal.databinding.ActivityMainBinding
import com.example.addcal.databinding.FragmentAdditionBinding
import com.example.addcal.viewmodel.CalculateViewModel

class Addition : Fragment(R.layout.fragment_addition) {
    private lateinit var binding: FragmentAdditionBinding
    private lateinit var calculateViewModel: CalculateViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentAdditionBinding.inflate(inflater,container,false)

        calculateViewModel = ViewModelProvider(this).get(CalculateViewModel::class.java)

        binding.addbutton.setOnClickListener {
            val num1 = binding.numone.text.toString().toFloatOrNull() ?: 0f
            val num2 = binding.numtwo.text.toString().toFloatOrNull() ?: 0f

            val result = calculateViewModel.calculateSum(num1, num2)
            binding.finalValue.text = result.result.toString()
        }
        return binding.root


    }
}