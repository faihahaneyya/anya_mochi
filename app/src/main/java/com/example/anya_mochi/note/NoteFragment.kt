package com.example.anya_mochi.note

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
import com.example.anya_mochi.databinding.FragmentNoteBinding
import com.example.anya_mochi.entity.NoteEntity
import kotlinx.coroutines.launch

class NoteFragment : Fragment() {
    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!
    private lateinit var db: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = AppDatabase.getInstance(requireContext())

        // Setup RecyclerView & Garis Pembatas (Modul Hal 17)
        binding.rvNotes.layoutManager = LinearLayoutManager(context)
        binding.rvNotes.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        // Aksi Tombol FAB Tambah Catatan
        binding.fabAddNote.setOnClickListener {
            val intent = Intent(requireContext(), NoteFormActivity::class.java)
            startActivity(intent)
        }
    }

    // Mengambil data ulang setiap kali fragment aktif/kembali dari form (Modul Hal 17)
    override fun onResume() {
        super.onResume()
        fetchNotes()
    }

    private fun fetchNotes() {
        viewLifecycleOwner.lifecycleScope.launch {
            val noteList = db.noteDao().getAll()
            binding.rvNotes.adapter = NoteAdapter(noteList, this@NoteFragment)
        }
    }

    // Fungsi Hapus Catatan yang dipanggil dari Adapter (Modul Hal 20)
    fun deleteNote(note: NoteEntity) {
        viewLifecycleOwner.lifecycleScope.launch {
            db.noteDao().delete(note)
            Toast.makeText(requireContext(), "Catatan berhasil dihapus", Toast.LENGTH_SHORT).show()
            fetchNotes() // Refresh tampilan setelah dihapus
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}