package com.example.anya_mochi

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.anya_mochi.databinding.ActivityVerificationBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class VerificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVerificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ambil data yang dikirim dari RegisterActivity
        val phone = intent.getStringExtra("EXTRA_PHONE") ?: ""
        val username = intent.getStringExtra("EXTRA_USER") ?: ""
        val password = intent.getStringExtra("EXTRA_PASS") ?: ""

        binding.btnVerify.setOnClickListener {
            val inputOtp = binding.etOtp.text.toString().trim()

            // Soal 2: Jika OTP sama dengan nomor HP
            if (inputOtp == phone && inputOtp.isNotEmpty()) {
                // Lanjut Soal 3: Simpan ke SharedPreferences
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

    // Soal 3: Simpan data registrasi ke SharedPreferences
    private fun saveData(user: String, pass: String) {
        val sharedPref = getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("saved_username", user)
        editor.putString("saved_password", pass)
        editor.apply()

        Toast.makeText(this, "Registrasi Berhasil!", Toast.LENGTH_SHORT).show()

        // Pindah ke LoginActivity (AuthActivity) setelah sukses simpan
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finishAffinity() // Menutup semua activity agar tidak bisa Back ke OTP
    }
}
