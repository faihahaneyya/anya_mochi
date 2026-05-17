package com.example.anya_mochi.settings

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.anya_mochi.R

class SettingAdapter(
    context: Context,
    private val settingsList: List<SettingItem>
) : ArrayAdapter<SettingItem>(context, R.layout.item_setting, settingsList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Inflate layout menggunakan inflater biasa tanpa binding
        val rowView = LayoutInflater.from(context).inflate(R.layout.item_setting, parent, false)

        // Hubungkan komponen menggunakan findViewById manual
        val tvIcon = rowView.findViewById<TextView>(R.id.tvSettingIcon)
        val tvTitle = rowView.findViewById<TextView>(R.id.tvSettingTitle)
        val tvSubtitle = rowView.findViewById<TextView>(R.id.tvSettingSubtitle)

        // Ambil data item sesuai posisi baris
        val item = settingsList[position]

        // Set teks data ke komponen view
        tvIcon.text = item.icon
        tvTitle.text = item.title
        tvSubtitle.text = item.subtitle

        return rowView
    }
}