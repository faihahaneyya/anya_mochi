package com.example.anya_mochi.saran

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anya_mochi.databinding.ItemSaranBinding
import com.example.anya_mochi.entity.SaranEntity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SaranAdapter(
    private val sarans: List<SaranEntity>,
    private val saranFragment: SaranFragment
) : RecyclerView.Adapter<SaranAdapter.SaranViewHolder>() {

    inner class SaranViewHolder(val binding: ItemSaranBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaranViewHolder {
        val binding = ItemSaranBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SaranViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SaranViewHolder, position: Int) {
        val saran = sarans[position]
        holder.binding.tvJudulSaran.text = saran.judulSaran
        holder.binding.tvRincianSaran.text = saran.rincianSaran

        holder.binding.btnDeleteSaran.setOnClickListener {
            MaterialAlertDialogBuilder(holder.itemView.context)
                .setTitle("Hapus Saran")
                .setMessage("Apakah Anda yakin ingin menghapus data saran ini?")
                .setPositiveButton("Ya") { dialog, _ ->
                    saranFragment.deleteSaran(saran)
                    dialog.dismiss()
                }
                .setNegativeButton("Batal") { dialog, _ -> dialog.dismiss() }
                .show()
        }
    }

    override fun getItemCount() = sarans.size
}