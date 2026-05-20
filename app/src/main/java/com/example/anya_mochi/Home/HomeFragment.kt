package com.example.anya_mochi.Home

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView // WAJIB ADA: Untuk mendeteksi icon settings
import androidx.fragment.app.Fragment
import com.example.anya_mochi.AuthActivity
import com.example.anya_mochi.R
import com.example.anya_mochi.WebViewActivity
import com.example.anya_mochi.databinding.FragmentHomeBinding
import com.example.anya_mochi.pertemuan_2.MainActivity
import com.example.anya_mochi.pertemuan_4.JobBoardActivity
import com.example.anya_mochi.pertemuan_4.PortofolioActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRumusBangunRuang.setOnClickListener {
            startActivity(Intent(requireContext(), MainActivity::class.java))
        }

        // KEMBALI NORMAL: Tombol Career Path membuka halaman JobBoardActivity bawaanmu semula
        binding.btnJobBoard.setOnClickListener {
            startActivity(Intent(requireContext(), JobBoardActivity::class.java))
        }

        // SOLUSI MANUAL ANTI-ERROR: Menggunakan findViewById langsung ke layout view
        val icSettings = view.findViewById<ImageView>(R.id.ic_settings)
        icSettings.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, com.example.anya_mochi.settings.SettingsFragment())
                .addToBackStack(null) // Biar kalau ditekan tombol back di HP, kembali ke Home
                .commit()
        }

        binding.btnPortofolio.setOnClickListener {
            startActivity(Intent(requireContext(), PortofolioActivity::class.java))
        }

        binding.btnWebBinaDesa.setOnClickListener {
            startActivity(Intent(requireContext(), WebViewActivity::class.java))
        }

        binding.btnLogout.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Logout")
                .setMessage("Yakin ingin logout?")
                .setPositiveButton("Ya") { _, _ ->
                    val sharedPref = requireContext().getSharedPreferences("user_pref", MODE_PRIVATE)
                    sharedPref.edit().clear().apply()
                    startActivity(Intent(requireContext(), AuthActivity::class.java))
                    requireActivity().finish()
                }
                .setNegativeButton("Tidak", null)
                .show()
        }

        // ========================================================
        // KODE TAMBAHAN UNTUK TABLAYOUT & VIEWPAGER FEED BARU
        // ========================================================

        // Ambil ID komponen TabLayout dan ViewPager2 dari layout
        val tabLayout = view.findViewById<com.google.android.material.tabs.TabLayout>(R.id.tabLayoutFeed)
        val viewPager = view.findViewById<androidx.viewpager2.widget.ViewPager2>(R.id.viewPagerFeed)

        // Pasang Adapter ViewPager versi Kotlin (Menggunakan nama FeedViewPager sesuai request kamu)
        val pagerAdapter = com.example.anya_mochi.feed.FeedViewPager(this)
        viewPager.adapter = pagerAdapter

        // Hubungkan TabLayout dengan ViewPager2 menggunakan TabLayoutMediator
        com.google.android.material.tabs.TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Terbaru"
                1 -> tab.text = "Populer"
                2 -> tab.text = "Rekomendasi"
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}