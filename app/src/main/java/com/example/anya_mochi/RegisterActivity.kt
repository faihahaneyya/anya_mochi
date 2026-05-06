package com.example.anya_mochi

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.anya_mochi.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDaftar.setOnClickListener {
            val nama = binding.etNama.text.toString()
            val phone = binding.etPhone.text.toString()
            val user = binding.etUsername.text.toString()
            val pass = binding.etPassword.text.toString()

            if (nama.isNotEmpty() && phone.isNotEmpty() && user.isNotEmpty() && pass.isNotEmpty()) {
                val intent = Intent(this, VerificationActivity::class.java)
                // Mengirim data ke halaman verifikasi
                intent.putExtra("EXTRA_PHONE", phone)
                intent.putExtra("EXTRA_USER", user)
                intent.putExtra("EXTRA_PASS", pass)
                startActivity(intent)
            }
        }
    }
}