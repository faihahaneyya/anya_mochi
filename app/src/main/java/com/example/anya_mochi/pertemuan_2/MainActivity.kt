package com.example.anya_mochi.pertemuan_2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.anya_mochi.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Tombol ke Persegi Panjang
        findViewById<Button>(R.id.btnGoPersegi).setOnClickListener {
            val intentPersegi = Intent(this, PersegiPanjangActivity::class.java)
            startActivity(intentPersegi)
        }

        // Tombol ke Tabung
        findViewById<Button>(R.id.btnGoTabung).setOnClickListener {
            val intentTabung = Intent(this, TabungActivity::class.java)
            startActivity(intentTabung)
        }
    }
}