package com.example.anya_mochi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.anya_mochi.About.AboutFragment
import com.example.anya_mochi.Home.HomeFragment
import com.example.anya_mochi.Profile.ProfileFragment
import com.example.anya_mochi.note.NoteFragment
import com.example.anya_mochi.saran.SaranFragment
import com.example.anya_mochi.databinding.ActivityBaseBinding

class BaseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tentukan fragment default berdasarkan intent NAV_TARGET (dari notifikasi)
        if (savedInstanceState == null) {
            val navTarget = intent.getStringExtra("NAV_TARGET")
            when (navTarget) {
                "note" -> {
                    replaceFragment(NoteFragment())
                    binding.bottomNavView.selectedItemId = R.id.nav_note
                }
                "saran" -> {
                    replaceFragment(SaranFragment())
                    binding.bottomNavView.selectedItemId = R.id.nav_saran
                }
                else -> {
                    replaceFragment(HomeFragment())
                }
            }
        }

        // Logika ketika menu navigasi diklik
        binding.bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> replaceFragment(HomeFragment())
                R.id.nav_note -> replaceFragment(NoteFragment())
                R.id.nav_saran -> replaceFragment(SaranFragment())
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