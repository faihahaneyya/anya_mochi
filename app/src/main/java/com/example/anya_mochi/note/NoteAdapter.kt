package com.example.anya_mochi.note

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anya_mochi.databinding.ItemNoteBinding
import com.example.anya_mochi.entity.NoteEntity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class NoteAdapter(
    private val notes: List<NoteEntity>,
    private val noteFragment: NoteFragment
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    // Perbaikan struktur inner class agar binding.root terbaca sebagai View (bukan File)
    inner class NoteViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.binding.tvTitle.text = note.title
        holder.binding.tvContent.text = note.content

        holder.binding.btnDelete.setOnClickListener {
            MaterialAlertDialogBuilder(holder.itemView.context)
                .setTitle("Hapus Catatan")
                .setMessage("Apakah kamu yakin ingin menghapus catatan ini?")
                .setPositiveButton("Ya") { dialog, _ ->
                    noteFragment.deleteNote(note)
                    dialog.dismiss()
                }
                .setNegativeButton("Batal") { dialog, _ -> dialog.dismiss() }
                .show()
        }
    }

    override fun getItemCount() = notes.size
}