package com.example.anya_mochi

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.anya_mochi.databinding.ActivityAuthBinding
import com.example.anya_mochi.pertemuan_2.MainActivity
import com.example.anya_mochi.pertemuan_3.WelcomeActivity

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val username = binding.etUser.text.toString().trim()
            val password = binding.etPass.text.toString().trim()

            // Soal 3: Ambil data dari SharedPreferences
            val sharedPref = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
            val savedUser = sharedPref.getString("saved_username", "")
            val savedPass = sharedPref.getString("saved_password", "")

            // Rule 1: username = password
            val isRule1 = (username == password && username.isNotEmpty())

            // Rule 2: username & password cocok dengan SharedPreference
            val isRule2 = (username == savedUser && password == savedPass && username.isNotEmpty())

            if (isRule1 || isRule2) {
                getSharedPreferences("user_pref", MODE_PRIVATE).edit()
                    .putBoolean("isLogin", true)
                    .apply()

                val intent = Intent(this, WelcomeActivity::class.java)
                startActivity(intent)
                finish() // Menutup halaman login setelah berhasil masuk

            } else {
                AlertDialog.Builder(this)
                    .setTitle("Login Gagal")
                    .setMessage("Username & Password tidak cocok!")
                    .setPositiveButton("OK", null)
                    .show()
            }
        }

        // Navigasi ke Halaman Registrasi (Penting untuk Soal 1)
        binding.tvDaftarSekarang.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}