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
import com.example.addcal.databinding.FragmentDivisionBinding
import com.example.addcal.viewmodel.CalculateViewModel

class Division : Fragment(R.layout.fragment_division) {
    private lateinit var binding: FragmentDivisionBinding
    private lateinit var calculateViewModel: CalculateViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentDivisionBinding.inflate(inflater,container,false)

        calculateViewModel = ViewModelProvider(this).get(CalculateViewModel::class.java)

        binding.divbutton.setOnClickListener {
            val num1 = binding.numdiv.text.toString().toFloatOrNull() ?: 0f
            val num2 = binding.numdivtwo.text.toString().toFloatOrNull() ?: 0f

            val result = calculateViewModel.calculateDiv(num1, num2)
            binding.divfinal.text = result.result.toString()
        }
        return binding.root


    }
}