package com.example.anya_mochi

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.anya_mochi.databinding.ActivityAuthBinding
import com.example.anya_mochi.pertemuan_2.MainActivity
class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val username = binding.etUser.text.toString().trim()
            val password = binding.etPass.text.toString().trim()

            if (username == password && username.isNotEmpty()) {
                getSharedPreferences("user_pref", MODE_PRIVATE).edit()
                    .putBoolean("isLogin", true)
                    .apply()

                // Panggil WebViewActivity buatan sendiri agar tidak ada browser tester
                val intent = Intent(this, WebViewActivity::class.java)
                startActivity(intent)

            } else {
                AlertDialog.Builder(this)
                    .setTitle("Login Gagal")
                    .setMessage("Username & Password tidak cocok!")
                    .setPositiveButton("OK", null)
                    .show()
            }
        }
    }
}