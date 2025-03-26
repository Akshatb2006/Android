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
import com.example.addcal.databinding.FragmentSubtractionBinding
import com.example.addcal.viewmodel.CalculateViewModel


class Subtraction : Fragment(R.layout.fragment_subtraction) {
    private lateinit var binding: FragmentSubtractionBinding
    private lateinit var calculateViewModel: CalculateViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentSubtractionBinding.inflate(inflater,container,false)

        calculateViewModel = ViewModelProvider(this).get(CalculateViewModel::class.java)

        binding.subbutton.setOnClickListener {
            val num1 = binding.numsone.text.toString().toFloatOrNull() ?: 0f
            val num2 = binding.numstwo.text.toString().toFloatOrNull() ?: 0f

            val result = calculateViewModel.calculateSub(num1, num2)
            binding.subfinal.text = result.result.toString()
        }
        return binding.root


    }
}