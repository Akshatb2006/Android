package com.example.addcal.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.addcal.R
import com.example.addcal.databinding.ActivityMainBinding
import com.example.addcal.viewmodel.CalculateViewModel
import com.example.addcal.viewmodel.MyAdapter
import com.google.android.material.tabs.TabLayout
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
                0->tab.text = "Addition"
                1->tab.text = "Subtraction"
                2->tab.text = "Multiplication"
                3->tab.text = "Division"
            }
        }.attach()

    }
}
