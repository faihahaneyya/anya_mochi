package com.example.anya_mochi.pertemuan_3

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.anya_mochi.AuthActivity
import com.example.anya_mochi.WebViewActivity
import com.example.anya_mochi.databinding.ActivityWelcomeBinding
import com.example.anya_mochi.pertemuan_2.MainActivity
import com.example.anya_mochi.pertemuan_4.JobBoardActivity
import com.example.anya_mochi.pertemuan_4.PortofolioActivity
import com.google.android.material.snackbar.Snackbar

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tombol 1: Ke Kalkulator
        binding.btnRumusBangunRuang.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Tombol 2: Ke Job Board
        binding.btnJobBoard.setOnClickListener {
            val intent = Intent(this, JobBoardActivity::class.java)
            startActivity(intent)
        }

        // Tombol 3: Ke Portofolio
        binding.btnPortofolio.setOnClickListener {
            val intent = Intent(this, PortofolioActivity::class.java)
            startActivity(intent)
        }

        // Tombol Baru: Ke Bina Desa (Web View)
        binding.btnWebBinaDesa.setOnClickListener {
            startActivity(Intent(this, WebViewActivity::class.java))
        }

        // Tombol 4: Logout
        binding.btnLogout.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Konfirmasi Logout")
                .setMessage("Yakin ingin kembali ke halaman login?")
                .setPositiveButton("Iya") { _, _ ->
                    // Bersihkan status login
                    getSharedPreferences("user_pref", MODE_PRIVATE).edit().clear().apply()
                    startActivity(Intent(this, AuthActivity::class.java))
                    finish()
                }
                .setNegativeButton("Tidak") { _, _ ->
                    Snackbar.make(binding.root, "Logout dibatalkan", Snackbar.LENGTH_SHORT).show()
                }
                .show()
        }
    }
}