package com.example.tabdemo

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    private final val nTabs:Int = 4;

    override fun getItemCount(): Int {
        return nTabs
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return Chats()
            1 -> return Updates()
            2 -> return Call()
            3 -> return History()
            else -> return Chats()
        }
    }
}