package com.example.anya_mochi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.anya_mochi.About.AboutFragment
import com.example.anya_mochi.Home.HomeFragment
import com.example.anya_mochi.Profile.ProfileFragment
import com.example.anya_mochi.databinding.ActivityBaseBinding

class BaseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tampilan default saat pertama dibuka adalah HOME
        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }

        // Logika ketika menu navigasi diklik
        binding.bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> replaceFragment(HomeFragment())
                R.id.nav_about -> replaceFragment(AboutFragment())
                R.id.nav_profile -> replaceFragment(ProfileFragment())
            }
            true // Memberitahu sistem bahwa item telah dipilih
        }
    }

    // Fungsi untuk mengganti fragment di wadah container
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}