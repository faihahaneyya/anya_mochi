package com.example.anya_mochi

import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.anya_mochi.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Setup Toolbar dan Aktifkan Tombol Panah Kembali (Back Arrow)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Sistem Fasilitas Desa"
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // KUNCI: Menampilkan panah back di toolbar

        // 2. Setup WebView
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.domStorageEnabled = true
        binding.webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE

        binding.webView.webViewClient = WebViewClient()
        binding.webView.loadUrl("https://faiha-2sic.alwaysdata.net/")

        // 3. Logika Tombol Back Sistem (Tombol Back HP / Gesture)
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.webView.canGoBack()) {
                    binding.webView.goBack()
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }

    // 4. Logika ketika Tombol Panah Kembali di Toolbar diklik
    override fun onSupportNavigateUp(): Boolean {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack() // Mundur halaman web jika ada riwayat
        } else {
            finish() // Tutup halaman activity jika sudah di halaman utama web
        }
        return true
    }
}