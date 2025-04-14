package com.example.navgraphexample

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.navgraphexample.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {
    private lateinit var binding: FragmentWelcomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWelcomeBinding.inflate(inflater,container,false)

        binding.goToLogin.setOnClickListener {
            it.findNavController().navigate(R.id.action_welcomeFragment_to_loginFragment2)
        }
        binding.goToSignUp.setOnClickListener {
            it.findNavController().navigate(R.id.action_welcomeFragment_to_signUpFragment2)
        }

        return binding.root
    }
}