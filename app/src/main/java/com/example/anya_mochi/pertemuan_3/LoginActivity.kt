package com.example.anya_mochi.pertemuan_3

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.anya_mochi.RegisterActivity
import com.example.anya_mochi.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val pass = binding.etPassword.text.toString()
            val intent = Intent(this, RegisterActivity::class.java)

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                val intent = Intent(this, WelcomeActivity::class.java)
                intent.putExtra("USER_NAME", email)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Email/Password salah", Toast.LENGTH_SHORT).show()

            }
        }
    }
}