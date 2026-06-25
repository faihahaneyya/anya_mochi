package com.example.anya_mochi

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.anya_mochi.databinding.ActivityVerificationBinding
import com.example.anya_mochi.utils.NotificationHelper
import com.example.anya_mochi.utils.PermissionHelper
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class VerificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVerificationBinding

    // Launcher untuk request permission notifikasi
    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Toast.makeText(this, "Notifikasi diizinkan", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Notifikasi ditolak", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ambil data yang dikirim dari RegisterActivity
        val phone = intent.getStringExtra("EXTRA_PHONE") ?: ""
        val username = intent.getStringExtra("EXTRA_USER") ?: ""
        val password = intent.getStringExtra("EXTRA_PASS") ?: ""

        // Request permission notifikasi (Android 13+)
        if (PermissionHelper.isNotificationPermissionRequired()) {
            if (!PermissionHelper.hasPermission(this, Manifest.permission.POST_NOTIFICATIONS)) {
                PermissionHelper.requestPermission(
                    notificationPermissionLauncher,
                    Manifest.permission.POST_NOTIFICATIONS
                )
            }
        }

        binding.btnVerify.setOnClickListener {
            val inputOtp = binding.etOtp.text.toString().trim()

            // Jika OTP sama dengan nomor HP
            if (inputOtp == phone && inputOtp.isNotEmpty()) {
                // Simpan ke SharedPreferences
                saveData(username, password)
            } else {
                // Tampilkan error dengan MaterialAlertDialog jika salah/kosong
                MaterialAlertDialogBuilder(this)
                    .setTitle("Verifikasi Gagal")
                    .setMessage("Kode OTP salah atau kosong. Gunakan No. Handphone Anda!")
                    .setPositiveButton("Coba Lagi", null)
                    .show()
            }
        }
    }

    // Simpan data registrasi ke SharedPreferences
    private fun saveData(user: String, pass: String) {
        val sharedPref = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        sharedPref.edit()
            .putString("saved_username", user)
            .putString("saved_password", pass)
            .apply()

        // Tampilkan notifikasi "Registrasi Berhasil"
        val loginIntent = Intent(this, AuthActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        NotificationHelper.showNotification(
            context = this,
            title = "Registrasi Berhasil! 🎉",
            message = "Selamat datang di Sistem Bina Desa, $user! Silakan login untuk mengeksplorasi fasilitas desa.",
            intent = loginIntent
        )

        Toast.makeText(this, "Registrasi Berhasil!", Toast.LENGTH_SHORT).show()

        // Pindah ke AuthActivity (Login) setelah sukses simpan
        startActivity(loginIntent)
        finishAffinity() // Menutup semua activity agar tidak bisa Back ke OTP
    }
}
