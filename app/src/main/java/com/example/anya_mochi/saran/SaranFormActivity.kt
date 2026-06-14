package com.example.anya_mochi.saran

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.anya_mochi.AppDatabase
import com.example.anya_mochi.databinding.ActivitySaranFormBinding
import com.example.anya_mochi.entity.SaranEntity // Baris import ini yang tadi membuat error merah
import kotlinx.coroutines.launch

class SaranFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySaranFormBinding
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaranFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)

        // Konfigurasi Toolbar sesuai standardisasi Modul Halaman 7
        binding.toolbar.title = "Form Tambah Saran"
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.btnSaveSaran.setOnClickListener {
            val judul = binding.etJudulSaran.text.toString()
            val rincian = binding.etRincianSaran.text.toString()

            // Validasi input kosong
            if (judul.isNotBlank() && rincian.isNotBlank()) {
                lifecycleScope.launch {
                    db.saranDao().insertSaran(
                        SaranEntity(
                            judulSaran = judul,
                            rincianSaran = rincian,
                            tanggalSaran = System.currentTimeMillis() // Menggunakan format Long sesuai modul hal 5
                        )
                    )
                    finish() // Tutup activity setelah berhasil menyimpan
                }
            } else {
                Toast.makeText(this, "Semua inputan wajib diisi!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}