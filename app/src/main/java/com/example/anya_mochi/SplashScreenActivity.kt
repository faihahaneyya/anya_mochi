package com.example.anya_mochi

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.anya_mochi.pertemuan_3.LoginActivity
import com.example.anya_mochi.pertemuan_3.WelcomeActivity
import com.example.anya_mochi.intro.IntroActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        lifecycleScope.launch {
            delay(2000)
            val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)
            val isLogin = sharedPref.getBoolean("isLogin", false)

            // TAMBAHAN: Cek apakah user sudah pernah melihat intro onboarding
            val isFirstTime = sharedPref.getBoolean("isFirstTime", true)

            if (isFirstTime) {
                // Jika pertama kali (atau setelah Sign Out), lempar ke halaman Intro dengan titik/dots
                startActivity(Intent(this@SplashScreenActivity, IntroActivity::class.java))
            } else if (isLogin) {
                // Sekarang langsung ke WelcomeActivity / BaseActivity
                startActivity(Intent(this@SplashScreenActivity, BaseActivity::class.java))
            } else {
                startActivity(Intent(this@SplashScreenActivity, AuthActivity::class.java))
            }
            finish()
        }
    }
}