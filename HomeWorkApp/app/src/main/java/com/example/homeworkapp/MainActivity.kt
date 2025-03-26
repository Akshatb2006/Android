package com.example.homeworkapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        tabLayout = findViewById(R.id.mytablayout)
        viewPager2 = findViewById(R.id.myviewpager)

        viewPager2.adapter = MyAdapter(this)
        TabLayoutMediator(tabLayout, viewPager2) { tab, index ->
            when(index){
                0->tab.text = "HomeFragment"
                1->tab.text = "DashBoardFragment"
                2->tab.text = "Notifications Fragment"
            }
        }
    }
}