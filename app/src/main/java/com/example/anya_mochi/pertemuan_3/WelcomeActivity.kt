package com.example.anya_mochi.pertemuan_3

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
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

        // Tombol 1: Ke Kalkulator (P2)
        binding.btnRumusBangunRuang.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("EXTRA_JUDUL", "KALKULATOR REXODUS")
            intent.putExtra("EXTRA_DESC", "Hitung rumus bangun ruang favoritmu.")
            startActivity(intent)
        }

        // Tombol 2: Ke Job Board (P4)
        binding.btnJobBoard.setOnClickListener {
            val intent = Intent(this, JobBoardActivity::class.java)
            intent.putExtra("EXTRA_JUDUL", "REXODUS CAREER")
            intent.putExtra("EXTRA_DESC", "Temukan karir impian di dunia gaming.")
            startActivity(intent)
        }

        // Tombol 3: Ke Portofolio (P4)
        binding.btnPortofolio.setOnClickListener {
            val intent = Intent(this, PortofolioActivity::class.java)
            intent.putExtra("EXTRA_JUDUL", "REXODUS GALLERY")
            intent.putExtra("EXTRA_DESC", "Lihat portofolio dan proyek terbaik kami.")
            startActivity(intent)
        }

        // Tombol 4: Logout (Alert + SnackBar)
        binding.btnLogout.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Konfirmasi Logout")
                .setMessage("Yakin ingin kembali ke halaman login?")
                .setPositiveButton("Iya") { _, _ ->
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
                .setNegativeButton("Tidak") { _, _ ->
                    // SnackBar sesuai permintaan soal
                    Snackbar.make(binding.root, "Logout dibatalkan", Snackbar.LENGTH_SHORT).show()
                }
                .show()
        }
    }
}