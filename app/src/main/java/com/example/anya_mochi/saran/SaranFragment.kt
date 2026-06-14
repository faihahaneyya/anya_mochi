package com.example.anya_mochi.saran

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anya_mochi.AppDatabase
import com.example.anya_mochi.databinding.FragmentSaranBinding
import com.example.anya_mochi.entity.SaranEntity
import kotlinx.coroutines.launch

class SaranFragment : Fragment() {
    private var _binding: FragmentSaranBinding? = null
    private val binding get() = _binding!!
    private lateinit var db: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSaranBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = AppDatabase.getInstance(requireContext())

        // Setup RecyclerView & Garis Pembatas
        binding.rvSaran.layoutManager = LinearLayoutManager(context)
        binding.rvSaran.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        // Aksi Tombol FAB Tambah Saran
        binding.fabAddSaran.setOnClickListener {
            val intent = Intent(requireContext(), SaranFormActivity::class.java)
            startActivity(intent)
        }
    }

    // Ambil data ulang saat layar kembali dibuka
    override fun onResume() {
        super.onResume()
        fetchSaran()
    }

    private fun fetchSaran() {
        viewLifecycleOwner.lifecycleScope.launch {
            val saranList = db.saranDao().getAllSaran()
            binding.rvSaran.adapter = SaranAdapter(saranList, this@SaranFragment)
        }
    }

    // Fungsi Hapus Saran yang dipanggil dari Adapter
    fun deleteSaran(saran: SaranEntity) {
        viewLifecycleOwner.lifecycleScope.launch {
            db.saranDao().deleteSaran(saran)
            Toast.makeText(requireContext(), "Saran berhasil dihapus", Toast.LENGTH_SHORT).show()
            fetchSaran() // Refresh tampilan
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}