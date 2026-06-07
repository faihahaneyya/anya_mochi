package com.example.anya_mochi.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.anya_mochi.R
import com.example.anya_mochi.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    // 1. Inisialisasi variabel View Binding khas Fragment
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 2. Meng-inflate layout menggunakan class Binding otomatis
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 3. LOGIKA ARROW BACK: Langsung panggil ID dari xml via binding
        binding.btnBackSettings.setOnClickListener {
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

        // 4. PASANG ADAPTER KE LISTVIEW VIA BINDING
        val adapter = SettingAdapter(requireContext(), listMenu)
        binding.lvSettings.adapter = adapter

        // 5. LOGIKA KLIK ITEM LISTVIEW VIA BINDING
        binding.lvSettings.setOnItemClickListener { _, _, position, _ ->
            val selectedMenu = listMenu[position]
            Toast.makeText(requireContext(), "Membuka: ${selectedMenu.title}", Toast.LENGTH_SHORT).show()
        }
    }

    // 6. Wajib bersihkan binding saat view dihancurkan agar tidak terjadi kebocoran memori
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}