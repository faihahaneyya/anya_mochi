package com.example.anya_mochi.Home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anya_mochi.AuthActivity
import com.example.anya_mochi.R
import com.example.anya_mochi.api.RetrofitClient
import com.example.anya_mochi.databinding.FragmentHomeBinding
import com.example.anya_mochi.pertemuan_2.MainActivity
import com.example.anya_mochi.pertemuan_4.JobBoardActivity
import com.example.anya_mochi.pertemuan_4.PortofolioActivity
import com.example.anya_mochi.WebViewActivity
import com.example.anya_mochi.settings.SettingsFragment
import com.example.anya_mochi.feed.FeedViewPager

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Logika tombol settings ke SettingsFragment
        binding.icSettings.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SettingsFragment())
                .addToBackStack(null)
                .commit()
        }

        // 2. Logika navigasi menu tugas kuliah kamu
        binding.btnRumusBangunRuang.setOnClickListener {
            startActivity(Intent(activity, MainActivity::class.java))
        }

        binding.btnJobBoard.setOnClickListener {
            startActivity(Intent(activity, JobBoardActivity::class.java))
        }

        binding.btnPortofolio.setOnClickListener {
            startActivity(Intent(activity, PortofolioActivity::class.java))
        }

        binding.btnWebBinaDesa.setOnClickListener {
            startActivity(Intent(activity, WebViewActivity::class.java))
        }

        // 3. Logika Setup TabLayout & ViewPager Feed
        val tabLayout = binding.tabLayoutFeed
        val viewPager = binding.viewPagerFeed

        val pagerAdapter = FeedViewPager(this)
        viewPager.adapter = pagerAdapter

        com.google.android.material.tabs.TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Terbaru"
                1 -> tab.text = "Populer"
                2 -> tab.text = "Rekomendasi"
            }
        }.attach()

        // 4. Setup RecyclerView Berita di bagian bawah Feed
        binding.rvNews.layoutManager = LinearLayoutManager(context)

        // Memanggil API Berita Online
        RetrofitClient.instance.getTopBerita().enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful && !response.body()?.articles.isNullOrEmpty()) {
                    val listBerita = response.body()?.articles!!
                    binding.rvNews.adapter = NewsAdapter(listBerita)
                } else {
                    tampilkanDataCadangan()
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                tampilkanDataCadangan()
            }
        })

        // 5. Logika tombol Sign Out di posisi paling bawah
        binding.btnLogout.setOnClickListener {
            val sharedPref = activity?.getSharedPreferences("user_pref", android.content.Context.MODE_PRIVATE)
            sharedPref?.edit()?.apply {
                putBoolean("isLogin", false)
                putBoolean("isFirstTime", true)
                apply()
            }
            startActivity(Intent(activity, AuthActivity::class.java))
            activity?.finish()
        }
    }

    private fun tampilkanDataCadangan() {
        val beritaDummy = listOf(
            Article(
                title = "Peresmian Pusat Digital dan Balai Pelatihan Internet Desa",
                description = "Guna menunjang program Bina Desa, kini masyarakat dapat memanfaatkan fasilitas komputer gratis dan wifi kecepatan tinggi untuk belajar.",
                urlToImage = "https://images.unsplash.com/photo-1542601906990-b4d3fb778b09?w=500&auto=format&fit=crop",
                url = null
            ),
            Article(
                title = "Optimalisasi Fasilitas Perpustakaan Umum & Pojok Baca Warga",
                description = "Penambahan ratusan koleksi buku baru dan sistem peminjaman digital kini mulai diterapkan untuk meningkatkan minat baca pemuda desa.",
                urlToImage = "https://images.unsplash.com/photo-1521587760476-6c12a4b040da?w=500&auto=format&fit=crop",
                url = null
            )
        )
        binding.rvNews.adapter = NewsAdapter(beritaDummy)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}