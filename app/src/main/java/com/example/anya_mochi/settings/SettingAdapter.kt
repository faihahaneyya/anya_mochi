package com.example.anya_mochi.settings

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.anya_mochi.R
import com.example.anya_mochi.databinding.ItemSettingBinding

class SettingAdapter(
    context: Context,
    private val settingsList: List<SettingItem>
) : ArrayAdapter<SettingItem>(context, R.layout.item_setting, settingsList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Melakukan inflate binding secara langsung untuk menghindari error inisialisasi variabel kosong
        val binding = ItemSettingBinding.inflate(LayoutInflater.from(context), parent, false)

        val item = settingsList[position]

        binding.tvSettingIcon.text = item.icon
        binding.tvSettingTitle.text = item.title
        binding.tvSettingSubtitle.text = item.subtitle

        return binding.root
    }
}