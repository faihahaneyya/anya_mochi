package com.example.anya_mochi.note

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.anya_mochi.AppDatabase
import com.example.anya_mochi.BaseActivity
import com.example.anya_mochi.databinding.ActivityNoteFormBinding
import com.example.anya_mochi.entity.NoteEntity
import com.example.anya_mochi.utils.NotificationHelper
import com.example.anya_mochi.utils.PermissionHelper
import com.example.anya_mochi.utils.ReminderHelper
import kotlinx.coroutines.launch
import java.util.Calendar

class NoteFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteFormBinding
    private lateinit var db: AppDatabase

    // Launcher untuk request permission notifikasi
    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Toast.makeText(this, "Notifikasi diizinkan", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Notifikasi ditolak, beberapa fitur tidak aktif", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)

        binding.toolbar.title = "Tambah Kegiatan Desa"
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        // Request permission notifikasi (Android 13+)
        if (PermissionHelper.isNotificationPermissionRequired()) {
            if (!PermissionHelper.hasPermission(this, Manifest.permission.POST_NOTIFICATIONS)) {
                PermissionHelper.requestPermission(
                    notificationPermissionLauncher,
                    Manifest.permission.POST_NOTIFICATIONS
                )
            }
        }

        // Toggle tampilan input menit saat switch berubah
        binding.switchReminder.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.tilReminderMinutes.visibility = View.VISIBLE
                binding.tvReminderInfo.visibility = View.VISIBLE
            } else {
                binding.tilReminderMinutes.visibility = View.GONE
                binding.tvReminderInfo.visibility = View.GONE
            }
        }

        binding.btnSaveNote.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val content = binding.etContent.text.toString()

            if (title.isNotBlank() && content.isNotBlank()) {
                lifecycleScope.launch {
                    // 1. Simpan catatan ke database
                    db.noteDao().insert(
                        NoteEntity(
                            title = title,
                            content = content,
                            createdAt = System.currentTimeMillis()
                        )
                    )

                    // 2. Tampilkan notifikasi instan "Catatan Tersimpan"
                    val noteIntent = Intent(this@NoteFormActivity, BaseActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                        putExtra("NAV_TARGET", "note")
                    }
                    NotificationHelper.showNotification(
                        context = this@NoteFormActivity,
                        title = "Kegiatan Desa Tersimpan! 📋",
                        message = "\"$title\" berhasil dicatat. Pantau semua kegiatan desa di menu Catatan.",
                        intent = noteIntent
                    )

                    // 3. Jadwalkan reminder jika switch aktif
                    if (binding.switchReminder.isChecked) {
                        val minutesText = binding.etReminderMinutes.text.toString()
                        val minutes = minutesText.toIntOrNull()

                        if (minutes != null && minutes > 0) {
                            val calendar = Calendar.getInstance().apply {
                                add(Calendar.MINUTE, minutes)
                            }
                            ReminderHelper.setReminder(
                                context = this@NoteFormActivity,
                                hour = calendar.get(Calendar.HOUR_OF_DAY),
                                minute = calendar.get(Calendar.MINUTE),
                                title = "⏰ Pengingat Kegiatan Desa",
                                message = "Jangan lupa! Kegiatan \"$title\" menunggu Anda.",
                                targetActivity = BaseActivity::class.java
                            )
                            Toast.makeText(
                                this@NoteFormActivity,
                                "Pengingat dijadwalkan dalam $minutes menit",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                this@NoteFormActivity,
                                "Masukkan jumlah menit yang valid",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    finish()
                }
            } else {
                Toast.makeText(this, "Isi semua kolom terlebih dahulu!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}