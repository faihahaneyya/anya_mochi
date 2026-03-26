package com.example.anya_mochi

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

// Nama class HARUS sama dengan nama file
class PersegiPanjangActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_persegi_panjang)

        val etPanjang = findViewById<EditText>(R.id.etPanjang)
        val etLebar = findViewById<EditText>(R.id.etLebar)
        val btnHitung = findViewById<Button>(R.id.btnHitungLuas)
        val tvHasil = findViewById<TextView>(R.id.tvHasil)
        val btnBack = findViewById<TextView>(R.id.btnBack)

        btnBack.setOnClickListener { finish() }

        btnHitung.setOnClickListener {
            val pText = etPanjang.text.toString()
            val lText = etLebar.text.toString()

            if (pText.isNotEmpty() && lText.isNotEmpty()) {
                val p = pText.toDouble()
                val l = lText.toDouble()
                val hasil = p * l
                tvHasil.text = String.format("%.2f", hasil)
            } else {
                if (pText.isEmpty()) etPanjang.error = "Harus diisi"
                if (lText.isEmpty()) etLebar.error = "Harus diisi"
            }
        }
    }
}