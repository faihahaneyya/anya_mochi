package com.example.anya_mochi.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.anya_mochi.R

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Hubungkan dengan layout fragment_settings.xml
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        val lvSettings = view.findViewById<ListView>(R.id.lvSettings)
        val btnBackSettings = view.findViewById<ImageView>(R.id.btnBackSettings)

        // LOGIKA ARROW BACK: Menghapus fragment ini dari stack dan otomatis kembali ke halaman Home
        btnBackSettings.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // Data dummy untuk ListView menu settings kamu
        val listMenu = listOf(
            SettingItem("🔒", "Privacy Policy", "Kebijakan privasi & keamanan data pengguna"),
            SettingItem("ℹ️", "About Application", "Informasi versi & pengembang aplikasi"),
            SettingItem("🛠️", "Account Settings", "Ubah detail profil dan setelan dasar"),
            SettingItem("📄", "Terms of Service", "Syarat & ketentuan penggunaan platform"),
            SettingItem("📞", "Help & Support", "Hubungi tim bantuan Sistem Desa")
        )

        // Pasang adapter ke ListView
        val adapter = SettingAdapter(requireContext(), listMenu)
        lvSettings.adapter = adapter

        // Logika ketika salah satu menu di list ditekan
        lvSettings.setOnItemClickListener { _, _, position, _ ->
            val selectedMenu = listMenu[position]
            Toast.makeText(requireContext(), "Membuka: ${selectedMenu.title}", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}