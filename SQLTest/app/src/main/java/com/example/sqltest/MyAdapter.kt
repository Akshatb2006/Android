package com.example.sqltest

import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    private val nTabs:Int = 3;
    override fun getItemCount(): Int {
        return nTabs
    }
    override fun createFragment(position: Int): Fragment {
        when(position){
            0->return HomeFragment()
            1->return SearchFragment()
            2->return ChatFragment()
            else-> return HomeFragment()
        }
    }
}
