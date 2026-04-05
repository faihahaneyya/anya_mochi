package com.example.anya_mochi.pertemuan_3

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
// Pastikan import binding merujuk ke nama file XML yang baru
import com.example.anya_mochi.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup View Binding sesuai nama file XML baru
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Logika tombol Get Started (sebelumnya Explore)
        binding.btnExplore.setOnClickListener {
            Toast.makeText(this, "Namaste! Let's start your journey.", Toast.LENGTH_SHORT).show()
        }
    }
}