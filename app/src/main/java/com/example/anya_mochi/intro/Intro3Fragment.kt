package com.example.anya_mochi.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.anya_mochi.R
import com.google.android.material.button.MaterialButton

class Intro3Fragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_intro3, container, false)
        val btnStart = view.findViewById<MaterialButton>(R.id.btnStart)

        btnStart.setOnClickListener {
            (activity as? IntroActivity)?.pindahKeHalamanUtama()
        }
        return view
    }
}