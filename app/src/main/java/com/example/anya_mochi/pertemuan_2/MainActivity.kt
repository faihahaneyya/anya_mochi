package com.example.anya_mochi.pertemuan_2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.anya_mochi.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TANGKAP DATA DARI PERTEMUAN 4 (Jika ada)
        val judul = intent.getStringExtra("EXTRA_JUDUL") ?: "Menu Kalkulator"
        val btnBack = findViewById<TextView>(R.id.btnBack)
        btnBack.setOnClickListener { finish() }

        // Cari TextView di activity_main.xml (pastikan ID-nya ada)
        findViewById<TextView>(R.id.tvJudulP2)?.text = judul

        findViewById<Button>(R.id.btnGoPersegi).setOnClickListener {
            startActivity(Intent(this, PersegiPanjangActivity::class.java))
        }

        findViewById<Button>(R.id.btnGoTabung).setOnClickListener {
            startActivity(Intent(this, TabungActivity::class.java))
        }
    }
}