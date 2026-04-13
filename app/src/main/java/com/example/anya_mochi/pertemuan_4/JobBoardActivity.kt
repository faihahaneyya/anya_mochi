package com.example.anya_mochi.pertemuan_4

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.anya_mochi.databinding.ActivityJobBoardBinding

class JobBoardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJobBoardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tampilkan Judul dan Deskripsi dari Intent
        binding.tvJudulJob.text = intent.getStringExtra("EXTRA_JUDUL")
        binding.tvDeskripsiJob.text = intent.getStringExtra("EXTRA_DESC")
    }
}