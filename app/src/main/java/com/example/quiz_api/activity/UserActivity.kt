package com.example.quiz_api.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.quiz_api.R
import com.example.quiz_api.adapter.UserLogPagerAdapter
import com.example.quiz_api.databinding.ActivityUserBinding
import com.google.android.material.tabs.TabLayout

class UserActivity : AppCompatActivity(){

    private lateinit var binding: ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tabLogSelection.addTab(binding.tabLogSelection.newTab().setText("LOG IN"))
        binding.tabLogSelection.addTab(binding.tabLogSelection.newTab().setText("SIGN UP"))

        binding.viewpagerLog.adapter = UserLogPagerAdapter(supportFragmentManager, lifecycle)

        binding.tabLogSelection.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.viewpagerLog.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // do nothing
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // do nothing
            }
        })

        binding.viewpagerLog.registerOnPageChangeCallback(object : OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLogSelection.selectTab(binding.tabLogSelection.getTabAt(position))
            }
        })
    }
}