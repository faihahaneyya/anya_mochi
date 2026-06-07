package com.example.anya_mochi.intro

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.anya_mochi.BaseActivity
import com.example.anya_mochi.R
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator

class IntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        val viewPager = findViewById<ViewPager2>(R.id.viewPagerIntro)
        val dotsIndicator = findViewById<WormDotsIndicator>(R.id.dotsIndicator)

        val adapter = IntroAdapter(this)
        viewPager.adapter = adapter

        // Menghubungkan titik indikator dengan ViewPager2 slider
        dotsIndicator.attachTo(viewPager)
    }

    fun pindahKeHalamanUtama() {
        val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)
        sharedPref.edit().putBoolean("isFirstTime", false).apply()

        val intent = Intent(this, BaseActivity::class.java)
        startActivity(intent)
        finish()
    }
}