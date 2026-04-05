package com.example.anya_mochi.pertemuan_2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.anya_mochi.R
import kotlin.math.PI

class TabungActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabung)

        val etRadius = findViewById<EditText>(R.id.etRadius)
        val etTinggi = findViewById<EditText>(R.id.etTinggi)
        val btnHitung = findViewById<Button>(R.id.btnHitungVolume)
        val tvHasil = findViewById<TextView>(R.id.tvHasil)
        val btnBack = findViewById<TextView>(R.id.btnBack)

        btnBack.setOnClickListener { finish() }

        btnHitung.setOnClickListener {
            val r = etRadius.text.toString()
            val t = etTinggi.text.toString()

            if (r.isNotEmpty() && t.isNotEmpty()) {
                val volume = PI * Math.pow(r.toDouble(), 2.0) * t.toDouble()
                tvHasil.text = String.format("%.2f", volume)
            } else {
                if (r.isEmpty()) etRadius.error = "Harus diisi"
                if (t.isEmpty()) etTinggi.error = "Harus diisi"
            }
        }
    }
}