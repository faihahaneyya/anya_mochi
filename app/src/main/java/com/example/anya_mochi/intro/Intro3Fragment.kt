package com.example.anya_mochi.intro

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.anya_mochi.BaseActivity
import com.example.anya_mochi.R
import com.example.anya_mochi.utils.NotificationHelper
import com.google.android.material.button.MaterialButton

class Intro3Fragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_intro3, container, false)
        val btnStart = view.findViewById<MaterialButton>(R.id.btnStart)

        btnStart.setOnClickListener {
            // Tampilkan notifikasi sambutan saat pertama kali masuk aplikasi
            val homeIntent = Intent(requireContext(), BaseActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            NotificationHelper.showNotification(
                context = requireContext(),
                title = "Halo, Warga Desa! 🌾",
                message = "Aplikasi Bina Desa siap membantu Anda. Sampaikan aspirasi, pantau kegiatan, dan nikmati layanan desa dengan mudah!",
                intent = homeIntent
            )

            (activity as? IntroActivity)?.pindahKeHalamanUtama()
        }
        return view
    }
}