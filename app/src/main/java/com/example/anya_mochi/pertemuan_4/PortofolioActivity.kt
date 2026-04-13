package com.example.anya_mochi.pertemuan_4

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.anya_mochi.databinding.ActivityPortofolioBinding

class PortofolioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPortofolioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPortofolioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ambil data Intent
        val judul = intent.getStringExtra("EXTRA_JUDUL") ?: "Portofolio"
        val deskripsi = intent.getStringExtra("EXTRA_DESKRIPSI") ?: "Daftar Karya"

        // Set ke view
        binding.tvJudulPortofolio.text = intent.getStringExtra("EXTRA_JUDUL")
        binding.tvDeskripsiPortofolio.text = intent.getStringExtra("EXTRA_DESC")

        binding.btnTambahPortofolio.setOnClickListener {
            Toast.makeText(this, "Fitur Tambah Portofolio menyusul!", Toast.LENGTH_SHORT).show()
        }
    }
}