package com.example.anya_mochi

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.anya_mochi.databinding.ActivityAuthBinding
import com.example.anya_mochi.utils.NotificationHelper
import com.example.anya_mochi.utils.PermissionHelper

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

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
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Request permission notifikasi (Android 13+)
        if (PermissionHelper.isNotificationPermissionRequired()) {
            if (!PermissionHelper.hasPermission(this, Manifest.permission.POST_NOTIFICATIONS)) {
                PermissionHelper.requestPermission(
                    notificationPermissionLauncher,
                    Manifest.permission.POST_NOTIFICATIONS
                )
            }
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.etUser.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPass.text.toString().trim()

            // Mengambil data dari SharedPreferences
            val sharedPref = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
            val savedUser = sharedPref.getString("saved_username", "")
            val savedPass = sharedPref.getString("saved_password", "")

            // Rule 1: username = password
            val isRule1 = (username == password && username.isNotEmpty())

            // Rule 2: username & password cocok dengan SharedPreference
            val isRule2 = (username == savedUser && password == savedPass && username.isNotEmpty())

            // Pengecekan email kosong
            if (email.isEmpty()) {
                binding.tilEmail.error = "Email tidak boleh kosong!"
                return@setOnClickListener
            } else {
                binding.tilEmail.error = null
            }

            if (isRule1 || isRule2) {
                getSharedPreferences("user_pref", MODE_PRIVATE).edit()
                    .putBoolean("isLogin", true)
                    .apply()

                // Tampilkan notifikasi selamat datang setelah login berhasil
                val homeIntent = Intent(this, BaseActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                NotificationHelper.showNotification(
                    context = this,
                    title = "Login Berhasil! 👋",
                    message = "Selamat datang di Sistem Bina Desa, $username! Semoga harimu menyenangkan.",
                    intent = homeIntent
                )

                // Diarahkan ke SplashScreenActivity agar dicek ulang status isFirstTime-nya
                val intent = Intent(this, SplashScreenActivity::class.java)
                startActivity(intent)
                finish()

            } else {
                AlertDialog.Builder(this)
                    .setTitle("Login Gagal")
                    .setMessage("Username & Password tidak cocok!")
                    .setPositiveButton("OK", null)
                    .show()
            }
        }

        // AKSI KLIK UNTUK MATERIALBUTTON BARU (btnCancel)
        binding.btnCancel.setOnClickListener {
            finish() // Menutup Activity dan kembali ke halaman sebelumnya
        }

        // Navigasi ke Halaman Registrasi
        binding.tvDaftarSekarang.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}