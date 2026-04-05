package com.example.anya_mochi.pertemuan_3

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.anya_mochi.databinding.ActivitySuccessBinding

class SuccessActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Setup View Binding
        binding = ActivitySuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Logika tombol Explore
        binding.btnExplore.setOnClickListener {
            Toast.makeText(this, "Welcome to Anya Mochi App!", Toast.LENGTH_SHORT).show()
        }
    }
}