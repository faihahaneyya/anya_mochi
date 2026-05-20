package com.example.anya_mochi.feed

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class FeedViewPager(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        val fragment = FeedTabFragment()
        val args = Bundle()

        when (position) {
            0 -> args.putString("kategori", "Terbaru")
            1 -> args.putString("kategori", "Populer")
            else -> args.putString("kategori", "Rekomendasi")
        }

        fragment.arguments = args
        return fragment
    }
}