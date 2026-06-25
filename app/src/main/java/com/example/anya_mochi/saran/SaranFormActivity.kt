package com.example.anya_mochi.saran

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.anya_mochi.AppDatabase
import com.example.anya_mochi.BaseActivity
import com.example.anya_mochi.databinding.ActivitySaranFormBinding
import com.example.anya_mochi.entity.SaranEntity
import com.example.anya_mochi.utils.NotificationHelper
import com.example.anya_mochi.utils.PermissionHelper
import kotlinx.coroutines.launch

class SaranFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySaranFormBinding
    private lateinit var db: AppDatabase

    // Launcher untuk request permission notifikasi
    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Toast.makeText(this, "Notifikasi diizinkan", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Notifikasi ditolak, beberapa fitur tidak aktif", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaranFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)

        // Konfigurasi Toolbar
        binding.toolbar.title = "Form Tambah Saran"
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        // Request permission notifikasi (Android 13+)
        if (PermissionHelper.isNotificationPermissionRequired()) {
            if (!PermissionHelper.hasPermission(this, Manifest.permission.POST_NOTIFICATIONS)) {
                PermissionHelper.requestPermission(
                    notificationPermissionLauncher,
                    Manifest.permission.POST_NOTIFICATIONS
                )
            }
        }

        binding.btnSaveSaran.setOnClickListener {
            val judul = binding.etJudulSaran.text.toString()
            val rincian = binding.etRincianSaran.text.toString()

            // Validasi input kosong
            if (judul.isNotBlank() && rincian.isNotBlank()) {
                lifecycleScope.launch {
                    // 1. Simpan usulan ke database
                    db.saranDao().insertSaran(
                        SaranEntity(
                            judulSaran = judul,
                            rincianSaran = rincian,
                            tanggalSaran = System.currentTimeMillis()
                        )
                    )

                    // 2. Tampilkan notifikasi instan "Usulan Tersimpan"
                    val saranIntent = Intent(this@SaranFormActivity, BaseActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                        putExtra("NAV_TARGET", "saran")
                    }
                    NotificationHelper.showNotification(
                        context = this@SaranFormActivity,
                        title = "Usulan Warga Tersimpan! 📝",
                        message = "\"$judul\" berhasil disampaikan. Aspirasi Anda sangat berharga untuk kemajuan desa.",
                        intent = saranIntent
                    )

                    finish() // Tutup activity setelah berhasil menyimpan
                }
            } else {
                Toast.makeText(this, "Semua inputan wajib diisi!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}