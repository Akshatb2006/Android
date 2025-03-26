package com.example.tabdemo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.Tab
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewpager2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabLayout = findViewById(R.id.mytablayout)
        viewpager2 = findViewById(R.id.myviewpager)

        viewpager2.adapter = MyAdapter(this)
        TabLayoutMediator(tabLayout, viewpager2) { tab, index ->
            when(index){
                0->tab.text = "Chats"
                1->tab.text = "Updates"
                2->tab.text = "History"
                3->tab.text = "Call"
            }
        }

    }
}
