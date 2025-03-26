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
import com.example.addcal.databinding.FragmentMultiplicationBinding
import com.example.addcal.viewmodel.CalculateViewModel

class Multiplication : Fragment(R.layout.fragment_multiplication) {
    private lateinit var binding: FragmentMultiplicationBinding
    private lateinit var calculateViewModel: CalculateViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentMultiplicationBinding.inflate(inflater,container,false)

        calculateViewModel = ViewModelProvider(this).get(CalculateViewModel::class.java)

        binding.mulbutton.setOnClickListener {
            val num1 = binding.nummone.text.toString().toFloatOrNull() ?: 0f
            val num2 = binding.nummtwo.text.toString().toFloatOrNull() ?: 0f

            val result = calculateViewModel.calculateMul(num1, num2)
            binding.mulfinal.text = result.result.toString()
        }
        return binding.root


    }
}