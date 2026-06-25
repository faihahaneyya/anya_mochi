package com.example.anya_mochi.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.anya_mochi.BaseActivity

class ReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("title") ?: "Pengingat Kegiatan Desa"
        val message = intent.getStringExtra("message") ?: "Waktunya melakukan kegiatan desa!"
        val targetClassName = intent.getStringExtra("target_activity")

        // Tentukan halaman tujuan saat notifikasi diklik
        val targetIntent = if (!targetClassName.isNullOrEmpty()) {
            try {
                val clazz = Class.forName(targetClassName)
                Intent(context, clazz).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    // Kirim nav target agar BaseActivity langsung buka fragment yang tepat
                    putExtra("NAV_TARGET", "note")
                }
            } catch (e: ClassNotFoundException) {
                defaultIntent(context)
            }
        } else {
            defaultIntent(context)
        }

        NotificationHelper.showNotification(
            context = context,
            title = title,
            message = message,
            intent = targetIntent
        )
    }

    private fun defaultIntent(context: Context): Intent {
        return Intent(context, BaseActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("NAV_TARGET", "note")
        }
    }
}
