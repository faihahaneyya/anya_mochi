package com.example.anya_mochi.feed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.anya_mochi.R


class FeedTabFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FeedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_feed_tab, container, false)

        recyclerView = view.findViewById(R.id.rvFeed)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val kategori = arguments?.getString("kategori") ?: "Terbaru"

        val listData = ArrayList<FeedModel>()

        // Mengubah konten dummy menjadi topik seputar program Bina Desa
        when (kategori) {
            "Terbaru" -> {
                listData.add(FeedModel("Pembangunan Fasilitas", "Pembangunan posko digital Bina Desa telah rampung.", R.drawable.ic_bangun_banner))
                listData.add(FeedModel("Agenda Sosialisasi", "Jadwal sosialisasi aplikasi Mochi ke warga desa.", R.drawable.ic_sosial_banner))
            }
            "Populer" -> {
                listData.add(FeedModel("Program Edukasi Anak", "Bimbingan belajar matematika interaktif menggunakan kalkulator pintar.", R.drawable.ic_edukasi_banner))
                listData.add(FeedModel("Pelatihan Teknologi", "Warga desa antusias mengikuti pelatihan operasional komputer.", R.drawable.ic_pelatihan_banner))
            }
            "Rekomendasi" -> {
                listData.add(FeedModel("E-Government Desa", "Optimalisasi website fasilitas desa untuk layanan surat menyurat.", R.drawable.ic_egover_banner))
                listData.add(FeedModel("Pojok Baca Digital", "Rekomendasi buku digital baru untuk anak-anak Bina Desa.", R.drawable.ic_pojok_banner))
            }
        }

        adapter = FeedAdapter(listData)
        recyclerView.adapter = adapter

        return view
    }
}