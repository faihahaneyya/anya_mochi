package com.example.anya_mochi

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.anya_mochi.databinding.ActivityAuthBinding
import com.example.anya_mochi.pertemuan_3.WelcomeActivity

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val username = binding.etUser.text.toString().trim()
            val email = binding.etEmail.text.toString().trim() // Mengambil value email baru
            val password = binding.etPass.text.toString().trim()

            // Mengambil data dari SharedPreferences
            val sharedPref = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
            val savedUser = sharedPref.getString("saved_username", "")
            val savedPass = sharedPref.getString("saved_password", "")

            // Rule 1: username = password
            val isRule1 = (username == password && username.isNotEmpty())

            // Rule 2: username & password cocok dengan SharedPreference
            val isRule2 = (username == savedUser && password == savedPass && username.isNotEmpty())

            // Tambahkan pengecekan apakah email kosong atau tidak (opsional)
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

                val intent = Intent(this, WelcomeActivity::class.java)
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