package com.example.addcal.viewmodel

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.addcal.view.Addition
import com.example.addcal.view.Division
import com.example.addcal.view.MainActivity
import com.example.addcal.view.Multiplication
import com.example.addcal.view.Subtraction

class MyAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    private final val nTabs:Int = 4;

    override fun getItemCount(): Int {
        return nTabs
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> return Addition()
            1 -> return Subtraction()
            2 -> return Multiplication()
            3 -> return Division()
            else -> return Addition()
        }
    }
}