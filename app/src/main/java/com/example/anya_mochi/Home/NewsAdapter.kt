package com.example.anya_mochi.Home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.anya_mochi.databinding.ItemNewsBinding

class NewsAdapter(private val listArtikel: List<Article>) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val artikel = listArtikel[position]
        holder.binding.tvNewsTitle.text = artikel.title
        holder.binding.tvNewsDesc.text = artikel.description ?: "Tidak ada deskripsi."

        Glide.with(holder.itemView.context)
            .load(artikel.urlToImage)
            .into(holder.binding.imgNews)
    }

    override fun getItemCount(): Int = listArtikel.size
}